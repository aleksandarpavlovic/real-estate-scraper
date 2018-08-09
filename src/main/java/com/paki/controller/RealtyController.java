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

    private final RealtyService realtyService;
    private final RealtiesDTOTransformer dtoTransformer;

    @Autowired
    public RealtyController(RealtyService realtyService, RealtiesDTOTransformer dtoTransformer) {
        this.realtyService = realtyService;
        this.dtoTransformer = dtoTransformer;
    }

    @GetMapping(value = "/search/{searchId}")
    List<? extends RealtyDTO> getRealties(@PathVariable("searchId") Long searchId,
                                          @RequestParam(name = "sort", defaultValue = "price-asc", required = false) String sort,
                                          @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                          UriComponentsBuilder uriBuilder,
                                          HttpServletResponse response) {
        Pageable pageRequest = PageRequest.of(page, DEFAULT_PAGE_SIZE, getSort(sort));
        Page<? extends Realty> realtiesPage = realtyService.findPaginatedAndSorted(searchId, pageRequest);
        buildLinkHeader(uriBuilder, response, searchId, page, realtiesPage.getTotalPages(), sort);
        return dtoTransformer.transformRealtiesToDTO(realtiesPage.getContent());
    }

    private Sort getSort(String sortDTO) {
        if ("price-asc".equals(sortDTO))
            return Sort.by("price").ascending();
        if ("price-desc".equals(sortDTO))
            return Sort.by("price").descending();
        if ("date-asc".equals(sortDTO))
            return Sort.by("publishDate").ascending();
        if ("date-desc".equals(sortDTO))
            return Sort.by("publishDate").descending();
        return Sort.unsorted();
    }

    private void buildLinkHeader(UriComponentsBuilder uriBuilder, HttpServletResponse response, Long searchId, int page, int totalPages, String sort) {
        uriBuilder.path("/realties/search/" + searchId);

        List<String> linkHeaderElements = new ArrayList<>();
        if (hasNextPage(page, totalPages)) {
            String uriNextPage = uriBuilder
                    .replaceQueryParam("page", page + 1)
                    .replaceQueryParam("sort", sort)
                    .toUriString();
            linkHeaderElements.add("<" + uriNextPage + ">; rel=\"next\"");

            String uriLastPage = uriBuilder
                    .replaceQueryParam("page", totalPages - 1)
                    .replaceQueryParam("sort", sort)
                    .toUriString();
            linkHeaderElements.add("<" + uriLastPage + ">; rel=\"last\"");
        }

        if (hasPreviousPage(page)) {
            String uriPrevPage = uriBuilder
                    .replaceQueryParam("page", page - 1)
                    .replaceQueryParam("sort", sort)
                    .toUriString();
            linkHeaderElements.add("<" + uriPrevPage + ">; rel=\"prev\"");

            String uriFirstPage = uriBuilder
                    .replaceQueryParam("page", 0)
                    .replaceQueryParam("sort", sort)
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
