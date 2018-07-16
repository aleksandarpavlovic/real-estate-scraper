package scrape.halooglasi;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import scrape.Advertisement;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class HaloOglasiAdParser {
    public List<Advertisement> parse(Document doc) {
        List<Advertisement> ads = new LinkedList<>();
        for (Element rawAd: doc.select("div[class~=product-item product-list-item .*real-estates my-ad-placeholder]")) {
            try {
                BigDecimal price = parsePrice(rawAd);
                String adThumbnail = downloadAdThumbnail(rawAd, ".");
                LocalDate publishDate = parsePublishDate(rawAd);
                String advertiserType = parseAdvertiserType(rawAd);
                String adDesc = parseAdDescription(rawAd);
                String adTitle = parseAdTitle(rawAd);
                String adUrl = parseAdUrl(rawAd);
                String realtyType = parseRealtyType(rawAd);
                BigDecimal roomCount = parseRoomCount(rawAd);
                BigDecimal area = parseSurfaceArea(rawAd);

                ads.add(new Advertisement(adTitle, adDesc, realtyType, "Prodaja", price, adUrl, adThumbnail, publishDate, advertiserType, area, roomCount));
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return ads;
    }

    private BigDecimal parsePrice(Element rawAd) {
        Element elem = rawAd.selectFirst("div.central-feature > span");
        String price = elem.attr("data-value");
        price = price.replaceAll("[^0-9]", "");
        return new BigDecimal(price);
    }

    private String downloadAdThumbnail(Element rawAd, String directoryPath) {
        Element elem = rawAd.selectFirst("figure.pi-img-wrapper > a > img");
        if (elem == null)
            return null;

        String imageUrl = elem.attr("src");
        if (imageUrl == null)
            return null;

        try {
            return getImage(imageUrl, directoryPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Image url: " + imageUrl);
            return null;
        }
    }

    private String getImage(String src, String directoryPath) throws IOException {
        //Exctract the name of the image from the src attribute
        int indexname = src.lastIndexOf("/");

        if (indexname == src.length()) {
            src = src.substring(1, indexname);
        }

        indexname = src.lastIndexOf("/");
        String name = src.substring(indexname, src.length());
        String filePath = directoryPath + name;
        //Open a URL Stream
        URL url = new URL(src);
        InputStream in = url.openStream();

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(filePath))) {
            for (int b; (b = in.read()) != -1; ) {
                out.write(b);
            }
        }

        return filePath;
    }

    private LocalDate parsePublishDate(Element rawAd) {
        Element elem = rawAd.selectFirst("span.publish-date");
        if (elem == null)
            return null;
        String rawDate = elem.ownText();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            return LocalDate.parse(rawDate, dateFormatter);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String parseAdvertiserType(Element rawAd) {
        Element elem = rawAd.selectFirst("span.basic-info > span");
        if (elem == null)
            return null;

        return elem.attr("data-field-value");
    }

    private String parseAdUrl(Element rawAd) {
        Element elem = rawAd.selectFirst("h3.ad-title > a");
        if (elem == null)
            return null;

        return "https://www.halooglasi.com" + elem.attr("href");
    }

    private String parseAdTitle(Element rawAd) {
        Element elem = rawAd.selectFirst("h3.ad-title > a");
        if (elem == null)
            return null;

        return elem.ownText();
    }

    private String parseLocation(Element rawAd) {
        // TODO
        return null;
    }

    private String parseRealtyType(Element rawAd) {
        Element elem = rawAd.selectFirst("div.value-wrapper:contains(Tip nekretnine)");
        if (elem == null)
            return null;

        return elem.ownText();
    }

    private BigDecimal parseSurfaceArea(Element rawAd) {
        Element elem = rawAd.selectFirst("div.value-wrapper:contains(Kvadratura)");
        if (elem == null)
            return null;

        String area = elem.ownText().replaceAll("[m ]", "").replace(",",".");
        return new BigDecimal(area);
    }

    private BigDecimal parseRoomCount(Element rawAd) {
        Element elem = rawAd.selectFirst("div.value-wrapper:contains(Broj soba)");
        if (elem == null)
            return null;

        return new BigDecimal(elem.ownText());
    }

    private String parseAdDescription(Element rawAd) {
        Element elem = rawAd.selectFirst("p.text-description-list.ad-description.short-desc");
        if (elem == null)
            return null;

        return elem.ownText();
    }
}
