package scrape.nekretnine_rs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import realties.Apartment;
import realties.Realty;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NekretnineRsAdParser {
    Pattern pricePattern = Pattern.compile(".+, (.*?) .*");
    Pattern surfaceAreaPattern = Pattern.compile("(^[0-9]+)");
    Pattern publishDatePattern = Pattern.compile("(^[0-9\\.]+)");
    Pattern advertiserTypePattern = Pattern.compile("([a-zA-Z]+)$");
    Pattern realtyTypePattern = Pattern.compile(".*?\\Q|\\E.*?\\Q|\\E ([a-zA-Z]+)");

    public List<Realty> parse(Document doc) {
        List<Realty> ads = new LinkedList<>();
        for (Element rawAd: doc.select("div.resultList.fixed")) {
            try {
                BigDecimal price = parsePrice(rawAd);
                String thumbnailUrl = parseThumbnailUrl(rawAd);
                LocalDate publishDate = parsePublishDate(rawAd);
                String advertiserType = parseAdvertiserType(rawAd);
                String adTitle = parseAdTitle(rawAd);
                String adUrl = parseAdUrl(rawAd);
                String realtyType = parseRealtyType(rawAd);
                BigDecimal area = parseSurfaceArea(rawAd);

                Document adDoc = getAdDocument(adUrl);
                Element adData = adDoc.selectFirst("div.oglasData");
                String adDesc = parseAdDescription(adData);
                BigDecimal roomCount = parseRoomCount(adData);
                Optional<Boolean> registered = parseRegistered(adData);

                ads.add(new Apartment(adTitle, adDesc, realtyType, "Prodaja", price, adUrl, thumbnailUrl, publishDate, advertiserType, area, registered, roomCount));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ads;
    }

    private Document getAdDocument(String url) {
        try {
            return Jsoup.connect(url)
                    .header("Host", "www.nekretnine.rs")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BigDecimal parsePrice(Element rawAd) {
        Element elem = rawAd.selectFirst("div.resultListPrice");
        if (elem == null)
            return null;
        Matcher matcher = pricePattern.matcher(elem.ownText());
        if (matcher.find()) {
            String price = matcher.group(1).replaceAll("[^0-9]", "");
            return new BigDecimal(price);
        } else
            return null;
    }

    private BigDecimal parseSurfaceArea(Element rawAd) {
        Element elem = rawAd.selectFirst("div.resultListPrice");
        if (elem == null)
            return null;
        Matcher matcher = surfaceAreaPattern.matcher(elem.ownText());
        if (matcher.find()) {
            String area = matcher.group(1);
            return new BigDecimal(area);
        } else
            return null;
    }

    private LocalDate parsePublishDate(Element rawAd) {
        Element elem = rawAd.selectFirst("div.timeCat");
        if (elem == null)
            return null;
        Matcher matcher = publishDatePattern.matcher(elem.ownText());
        if (matcher.find()) {
            String rawDate = matcher.group(1);
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            try {
                return LocalDate.parse(rawDate, dateFormatter);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else
            return null;
    }

    private String parseAdvertiserType(Element rawAd) {
        Element elem = rawAd.selectFirst("div.timeCat");
        if (elem == null)
            return null;
        Matcher matcher = advertiserTypePattern.matcher(elem.ownText());
        if (matcher.find()) {
            return matcher.group(1);
        } else
            return null;
    }

    private String parseAdTitle(Element rawAd) {
        Element elem = rawAd.selectFirst("div.resultInfo h2 a");
        if (elem == null)
            return null;
        return elem.ownText();
    }

    private String parseRealtyType(Element rawAd) {
        Element elem = rawAd.selectFirst("div.timeCat");
        if (elem == null)
            return null;
        Matcher matcher = realtyTypePattern.matcher(elem.ownText());
        if (matcher.find()) {
            return matcher.group(1);
        } else
            return null;
    }

    private String parseThumbnailUrl(Element rawAd) {
        Element elem = rawAd.selectFirst("a.resultImg > img.imgs");
        if (elem == null)
            return null;
        return "https://www.nekretnine.rs" + elem.attr("src");
    }

    private String parseAdUrl(Element rawAd) {
        Element elem = rawAd.selectFirst("div.resultInfo h2 a");
        if (elem == null)
            return null;
        return elem.absUrl("href");
    }

    private String parseAdDescription(Element rawAd) {
        Element elem = rawAd.selectFirst("div.sRightGrid > h4:containsOwn(Opis) ~ p");
        if (elem == null)
            return null;
        return elem.ownText();
    }

    private BigDecimal parseRoomCount(Element rawAd) {
        Element elem = rawAd.selectFirst("div.sLeftGrid > ul > li > div:containsOwn(Sobe) + div");
        if (elem == null)
            return null;
        return new BigDecimal(elem.ownText());
    }

    private Optional<Boolean> parseRegistered(Element rawAd) {
        Element elem = rawAd.selectFirst("div.sLeftGrid > ul > li > div:containsOwn(Uknjiženo) + div");
        if (elem == null)
            return Optional.empty();
        return Optional.of("Da".equals(elem.ownText()) ? true : false);
    }
}
