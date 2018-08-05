package com.paki.scrape.scraper.halooglasi;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HaloOglasiRequest {
    @SerializedName("BaseTaxonomy")
    private String baseTaxonomy;
    @SerializedName("CategoryId")
    private String categoryId;
    @SerializedName("FieldORQueries")
    private List<FieldORQuery> fieldORQueries;
    @SerializedName("FieldQueries")
    private List<Object> fieldQueries;
    @SerializedName("GeoPolygonQuery")
    private Object geoPolygonQuery;
    @SerializedName("GetAllGeolocations")
    private boolean getAllGeolocations = false;
    @SerializedName("HasValueQueries")
    private List<String> hasValueQueries;
    @SerializedName("IsGrid")
    private boolean isGrid = false;
    @SerializedName("ItemsPerPage")
    private int itemsPerPage = 20;
    @SerializedName("PageNumber")
    private int pageNumber = 1;
    @SerializedName("MultiFieldORQueries")
    private List<FieldORQuery> multiFieldORQueries;
    @SerializedName("QuasiTaxonomy")
    private String quasiTaxonomy = "";
    @SerializedName("RangeQueries")
    private List<RangeQuery> rangeQueries;
    @SerializedName("RenderSEOWidget")
    private boolean renderSEOWidget = true;
    @SerializedName("SearchTypeIds")
    private List<Integer> searchTypeIds;
    @SerializedName("SortFields")
    private List<SortField> sortFields;
    @SerializedName("fetchBanners")
    private boolean fetchBanners = false;

    public HaloOglasiRequest(HaloOglasiRequest other) {
        this.baseTaxonomy = other.baseTaxonomy;
        this.categoryId = other.categoryId;
        this.fieldORQueries = other.fieldORQueries;
        this.fieldQueries = other.fieldQueries;
        this.geoPolygonQuery = other.geoPolygonQuery;
        this.getAllGeolocations = other.getAllGeolocations;
        this.hasValueQueries = other.hasValueQueries;
        this.isGrid = other.isGrid;
        this.itemsPerPage = other.itemsPerPage;
        this.pageNumber = other.pageNumber;
        this.multiFieldORQueries = other.multiFieldORQueries;
        this.quasiTaxonomy = other.quasiTaxonomy;
        this.rangeQueries = other.rangeQueries;
        this.renderSEOWidget = other.renderSEOWidget;
        this.searchTypeIds = other.searchTypeIds;
        this.sortFields = other.sortFields;
        this.fetchBanners = other.fetchBanners;
    }

    public HaloOglasiRequest nextPageRequest() {
        HaloOglasiRequest request = new HaloOglasiRequest(this);
        request.pageNumber++;
        return request;
    }

    public void updateFieldOrQueries(FieldORQuery newQuery) {
        if (fieldORQueries == null)
            fieldORQueries = new ArrayList<>();
        updateList(fieldORQueries, newQuery);
    }

    public void updateMultiFieldOrQueries(FieldORQuery newQuery) {
        if (multiFieldORQueries == null)
            multiFieldORQueries = new ArrayList<>();
        updateList(multiFieldORQueries, newQuery);
    }

    private void updateList(List<FieldORQuery> queries, FieldORQuery newQuery) {
         for(FieldORQuery query: queries) {
             if (query.getFieldName().equals(newQuery.getFieldName())) {
                 query.getFieldValues().addAll(newQuery.getFieldValues());
                 return;
             }
         }
         queries.add(newQuery);
    }

    public void updateRangeQueries(RangeQuery newQuery) {
        if (rangeQueries == null)
            rangeQueries = new ArrayList<>();
        rangeQueries.add(newQuery);
    }

    @Getter
    @Setter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor
    public static class FieldORQuery {
        @SerializedName("FieldName")
        private String fieldName;
        @SerializedName("FieldValues")
        private List<String> fieldValues;

        public static FieldORQuery from(String fieldName, String... fieldValues) {
            FieldORQuery query = new FieldORQuery();
            query.setFieldName(fieldName);
            query.setFieldValues(new ArrayList<>());
            if (fieldValues.length > 0) {
                for(String fieldValue: fieldValues) {
                    query.getFieldValues().add(fieldValue);
                }
            }
            return query;
        }
    }

    @Getter
    @Setter
    @Builder
    public static class RangeQuery {
        @SerializedName("FieldName")
        private String fieldName;
        @SerializedName("From")
        private Integer from;
        @SerializedName("To")
        private Integer to;
        @SerializedName("UnitId")
        private Integer unitId;
        @SerializedName("IncludeEmpty")
        private boolean includeEmpty = false;
        @SerializedName("_max")
        Integer max;
        @SerializedName("_min")
        Integer min;
    }

    public static class SortField {
        @SerializedName("Ascending")
        private boolean ascending = false;
        @SerializedName("FieldName")
        private String fieldName = "ValidFromForDisplay";
    }
}
