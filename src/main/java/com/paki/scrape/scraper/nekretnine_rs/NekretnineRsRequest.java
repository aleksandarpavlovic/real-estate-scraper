package com.paki.scrape.scraper.nekretnine_rs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class NekretnineRsRequest {
    private String baseUrl;
    private Map<String, Path> paths = new HashMap<>();
    private List<DivergentPaths> divergentPaths = new ArrayList<>();
    private List<String> pathOrder;
    private int pageNumber = 1;
    private Optional<Boolean> registered = Optional.empty();
    private Optional<List<String>> advertiser = Optional.empty();

    public NekretnineRsRequest(NekretnineRsRequest other) {
        this.baseUrl = other.baseUrl;
        this.paths = deepCopyPaths(other.paths);
        this.divergentPaths = other.divergentPaths;
        this.pathOrder = other.pathOrder;
        this.pageNumber = other.pageNumber;
        this.registered = other.registered;
        this.advertiser = other.advertiser;
    }

    private Map<String, Path> deepCopyPaths(Map<String, Path> paths) {
        Map<String, Path> pathsCopy = new HashMap<>();
        for (Map.Entry<String, Path> entry: paths.entrySet()) {
            pathsCopy.put(entry.getKey(), entry.getValue().copy());
        }
        return pathsCopy;
    }


    public void updatePath(Path path) {
        Path oldPath = paths.get(path.getResource());
        if (oldPath == null)
            paths.put(path.getResource(), path);
        else
            oldPath.setValue(oldPath.getValue() + "_" + path.getValue());
    }

    public NekretnineRsRequest nextPageRequest() {
        NekretnineRsRequest request = new NekretnineRsRequest(this);
        request.pageNumber++;
        return request;
    }

    public List<String> getUrls() {
        // split divergent paths into separate requests
        List<NekretnineRsRequest> requests = Arrays.asList(new NekretnineRsRequest(this));
        for(DivergentPaths divPaths: divergentPaths) {
            List<NekretnineRsRequest> newRequests = new ArrayList<>();
            for(NekretnineRsRequest request: requests) {
                for (Path path: divPaths.getPaths()) {
                    NekretnineRsRequest newRequest = new NekretnineRsRequest(request);
                    newRequest.updatePath(path);
                    newRequests.add(newRequest);
                }
            }
            requests = newRequests;
        }
        return requests.stream().map(NekretnineRsRequest::getUrl).collect(Collectors.toList());
    }

    private String getUrl() {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(baseUrl);
        for (String pathResource: pathOrder) {
            Path path = paths.get(pathResource);
            if (path != null)
                urlBuilder.append(path);
        }
        urlBuilder.append("/poredjaj-po/datumu_nanize")
                .append("/lista/po_stranici/50")
                .append("/stranica/").append(pageNumber);

        return urlBuilder.toString();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Path {
        private String resource;
        private String value;

        public Path(Path other) {
            this.resource = other.resource;
            this.value = other.value;
        }

        public Path copy() {
            return new Path(this);
        }

        @Override
        public String toString() {
            return "/" + resource + "/" + value;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class DivergentPaths {
        private List<Path> paths;

        public DivergentPaths(List<Path> paths) {
            this.paths = paths;
        }
    }
}
