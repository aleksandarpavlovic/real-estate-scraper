package com.paki.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.paki.dto.realties.RealtyDTO;
import com.paki.dto.transformers.RealtiesDTOTransformer;
import com.paki.realties.Realty;
import com.paki.scrape.services.RealtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/realties")
public class RealtyController {

    private static final int DEFAULT_PAGE_SIZE = 7;

    private static final String SEARCH = "searchid";
    private static final String PAGE = "page";
    private static final String SORT = "sort";
    private static final String SORT_PRICE_ASC = "priceasc";
    private static final String SORT_PRICE_DESC = "pricedesc";
        private static final String SORT_DATE_ASC = "dateasc";
    private static final String SORT_DATE_DESC = "datedesc";

    private final RealtyService realtyService;
    private final RealtiesDTOTransformer dtoTransformer;

    @Autowired
    public RealtyController(RealtyService realtyService, RealtiesDTOTransformer dtoTransformer) {
        this.realtyService = realtyService;
        this.dtoTransformer = dtoTransformer;
    }

    @GetMapping
    List<? extends RealtyDTO> getRealties(
                                          @RequestParam(name = SEARCH, defaultValue = "0", required = false) long searchId,
                                          @RequestParam(name = SORT, defaultValue = SORT_PRICE_ASC, required = false) String sort,
                                          @RequestParam(name = PAGE, defaultValue = "1", required = false) int page,
                                          UriComponentsBuilder uriBuilder,
                                          HttpServletResponse response) {
        Pageable pageRequest = PageRequest.of(page - 1, DEFAULT_PAGE_SIZE, getSort(sort));
        Page<? extends Realty> realtiesPage = realtyService.findPaginatedAndSorted(searchId, pageRequest);
        buildCustomLinkHeader(uriBuilder, response, searchId, page, realtiesPage.getTotalPages(), sort);
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

    private void buildXmlLinkHeader(UriComponentsBuilder uriBuilder, HttpServletResponse response, Long searchId, int page, int totalPages, String sort) {
        uriBuilder.path("/realties/searchid/" + searchId);

        List<String> linkHeaderElements = new ArrayList<>();
        if (hasNextPage(page, totalPages)) {
            String uriNextPage = uriBuilder
                    .replaceQueryParam(PAGE, page + 1)
                    .replaceQueryParam(SORT, sort)
                    .toUriString();
            linkHeaderElements.add("<" + uriNextPage + ">; rel=\"next\"");

            String uriLastPage = uriBuilder
                    .replaceQueryParam(PAGE, totalPages)
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
                    .replaceQueryParam(PAGE, 1)
                    .replaceQueryParam(SORT, sort)
                    .toUriString();
            linkHeaderElements.add("<" + uriFirstPage + ">; rel=\"first\"");
        }

        response.setHeader("Link", String.join(",", linkHeaderElements));
    }

    private void buildCustomLinkHeader(UriComponentsBuilder uriBuilder, HttpServletResponse response, Long searchId, int page, int totalPages, String sort) {
        uriBuilder.path("/realties");

        uriBuilder.replaceQueryParam(SEARCH, searchId);

        Map<String, String> links = new HashMap<>();
        if (hasNextPage(page, totalPages)) {
            String uriNextPage = uriBuilder
                    .replaceQueryParam(PAGE, page + 1)
                    .replaceQueryParam(SORT, sort)
                    .toUriString();
            links.put("next", uriNextPage);

            String uriLastPage = uriBuilder
                    .replaceQueryParam(PAGE, totalPages)
                    .replaceQueryParam(SORT, sort)
                    .toUriString();
            links.put("last", uriLastPage);
        }

        if (hasPreviousPage(page)) {
            String uriPrevPage = uriBuilder
                    .replaceQueryParam(PAGE, page - 1)
                    .replaceQueryParam(SORT, sort)
                    .toUriString();
            links.put("prev", uriPrevPage);

            String uriFirstPage = uriBuilder
                    .replaceQueryParam(PAGE, 1)
                    .replaceQueryParam(SORT, sort)
                    .toUriString();
            links.put("first", uriFirstPage);
        }

        links.put("current", Integer.toString(page));
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        response.setHeader("Link", gson.toJson(links));
    }

    private boolean hasNextPage(int page, int totalPages) {
        return totalPages > page;
    }

    private boolean hasPreviousPage(int page) {
        return page - 1 > 0;
    }
}
