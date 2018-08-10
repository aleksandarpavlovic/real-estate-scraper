package com.paki.controller;

import com.paki.dto.realties.RealtyDTO;
import com.paki.dto.transformers.RealtiesDTOTransformer;
import com.paki.realties.Realty;
import com.paki.scrape.services.RealtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/realties")
public class RealtyController {

    private static final int DEFAULT_PAGE_SIZE = 20;

    private static final String PAGE = "page";
    private static final String SORT = "sort";
    private static final String SORT_PRICE_ASC = "price-asc";
    private static final String SORT_PRICE_DESC = "price-desc";
    private static final String SORT_DATE_ASC = "date-asc";
    private static final String SORT_DATE_DESC = "date-desc";

    private final RealtyService realtyService;
    private final RealtiesDTOTransformer dtoTransformer;

    @Autowired
    public RealtyController(RealtyService realtyService, RealtiesDTOTransformer dtoTransformer) {
        this.realtyService = realtyService;
        this.dtoTransformer = dtoTransformer;
    }

    @GetMapping(value = "/search/{searchId}")
    List<? extends RealtyDTO> getRealties(@PathVariable("searchId") Long searchId,
                                          @RequestParam(name = SORT, defaultValue = SORT_PRICE_ASC, required = false) String sort,
                                          @RequestParam(name = PAGE, defaultValue = "0", required = false) int page,
                                          UriComponentsBuilder uriBuilder,
                                          HttpServletResponse response) {
        Pageable pageRequest = PageRequest.of(page, DEFAULT_PAGE_SIZE, getSort(sort));
        Page<? extends Realty> realtiesPage = realtyService.findPaginatedAndSorted(searchId, pageRequest);
        buildLinkHeader(uriBuilder, response, searchId, page, realtiesPage.getTotalPages(), sort);
        return dtoTransformer.transformRealtiesToDTO(realtiesPage.getContent());
    }

    private Sort getSort(String sortDTO) {
        if (SORT_PRICE_ASC.equals(sortDTO))
            return Sort.by("price").ascending();
        if (SORT_PRICE_DESC.equals(sortDTO))
            return Sort.by("price").descending();
        if (SORT_DATE_ASC.equals(sortDTO))
            return Sort.by("publishDate").ascending();
        if (SORT_DATE_DESC.equals(sortDTO))
            return Sort.by("publishDate").descending();
        return Sort.unsorted();
    }

    private void buildLinkHeader(UriComponentsBuilder uriBuilder, HttpServletResponse response, Long searchId, int page, int totalPages, String sort) {
        uriBuilder.path("/realties/search/" + searchId);

        List<String> linkHeaderElements = new ArrayList<>();
        if (hasNextPage(page, totalPages)) {
            String uriNextPage = uriBuilder
                    .replaceQueryParam(PAGE, page + 1)
                    .replaceQueryParam(SORT, sort)
                    .toUriString();
            linkHeaderElements.add("<" + uriNextPage + ">; rel=\"next\"");

            String uriLastPage = uriBuilder
                    .replaceQueryParam(PAGE, totalPages - 1)
                    .replaceQueryParam(SORT, sort)
                    .toUriString();
            linkHeaderElements.add("<" + uriLastPage + ">; rel=\"last\"");
        }

        if (hasPreviousPage(page)) {
            String uriPrevPage = uriBuilder
                    .replaceQueryParam(PAGE, page - 1)
                    .replaceQueryParam(SORT, sort)
                    .toUriString();
            linkHeaderElements.add("<" + uriPrevPage + ">; rel=\"prev\"");

            String uriFirstPage = uriBuilder
                    .replaceQueryParam(PAGE, 0)
                    .replaceQueryParam(SORT, sort)
                    .toUriString();
            linkHeaderElements.add("<" + uriFirstPage + ">; rel=\"prev\"");
        }

        response.setHeader("Link", String.join(",", linkHeaderElements));
    }

    private boolean hasNextPage(int page, int totalPages) {
        return totalPages - 1 > page;
    }

    private boolean hasPreviousPage(int page) {
        return page > 0;
    }
}
