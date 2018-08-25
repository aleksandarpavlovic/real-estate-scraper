package com.paki.scrape.scraper.halooglasi;

import com.paki.realties.*;
import com.paki.realties.enums.*;
import com.paki.scrape.scraper.AdParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class HaloOglasiAdParser extends AdParser {
    Pattern areaUnitPattern = Pattern.compile("[0-9.,]+ (.*)");
    Pattern surfaceAreaPattern = Pattern.compile("([0-9.,]+) .*");

    public HaloOglasiAdParser(Source source) {
        super(source);
    }

    @Override
    public Set<Realty> parseApartments(Document doc) {
        Set<Realty> ads = new HashSet<>();
        for (Element rawAd: doc.select("div[class~=product-item product-list-item .*real-estates my-ad-placeholder]")) {
            try {
                Apartment apartment = Apartment.builder()
                        .source(AdSource.HALO_OGLASI)
                        .externalId(formCompleteId(parseId(rawAd)))
                        .title(parseAdTitle(rawAd))
                        .description(parseAdDescription(rawAd))
                        .location(parseLocation(rawAd))
                        .publishDate(parsePublishDate(rawAd))
                        .url(parseAdUrl(rawAd))
                        .imageUrl(parseThumbnailUrl(rawAd))
                        .advertiserType(getAdvertiserType(parseAdvertiserType(rawAd)))
                        .price(parsePrice(rawAd))
                        .surfaceArea(parseSurfaceArea(rawAd))
                        .areaMeasurementUnit(getAreaMeasurementUnit(parseAreaMeasurementUnit(rawAd)))
                        .roomCount(getRoomCount(parseRoomCount(rawAd)))
                        .registered(RegistrationType.NA)
                        .build();

                ads.add(apartment);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return ads;
    }

    @Override
    public Set<Realty> parseHouses(Document doc) {
        Set<Realty> ads = new HashSet<>();
        for (Element rawAd: doc.select("div[class~=product-item product-list-item .*real-estates my-ad-placeholder]")) {
            try {
                House house= House.builder()
                        .source(AdSource.HALO_OGLASI)
                        .externalId(formCompleteId(parseId(rawAd)))
                        .title(parseAdTitle(rawAd))
                        .description(parseAdDescription(rawAd))
                        .location(parseLocation(rawAd))
                        .publishDate(parsePublishDate(rawAd))
                        .url(parseAdUrl(rawAd))
                        .imageUrl(parseThumbnailUrl(rawAd))
                        .advertiserType(getAdvertiserType(parseAdvertiserType(rawAd)))
                        .price(parsePrice(rawAd))
                        .surfaceArea(parseSurfaceArea(rawAd))
                        .areaMeasurementUnit(getAreaMeasurementUnit(parseAreaMeasurementUnit(rawAd)))
                        .roomCount(getRoomCount(parseRoomCount(rawAd)))
                        .registered(RegistrationType.NA)
                        .build();

                ads.add(house);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return ads;
    }

    @Override
    public Set<Realty> parseLand(Document doc) {
        Set<Realty> ads = new HashSet<>();
        for (Element rawAd: doc.select("div[class~=product-item product-list-item .*real-estates my-ad-placeholder]")) {
            try {
                Land land= Land.builder()
                        .source(AdSource.HALO_OGLASI)
                        .externalId(formCompleteId(parseId(rawAd)))
                        .title(parseAdTitle(rawAd))
                        .description(parseAdDescription(rawAd))
                        .location(parseLocation(rawAd))
                        .publishDate(parsePublishDate(rawAd))
                        .url(parseAdUrl(rawAd))
                        .imageUrl(parseThumbnailUrl(rawAd))
                        .advertiserType(getAdvertiserType(parseAdvertiserType(rawAd)))
                        .price(parsePrice(rawAd))
                        .surfaceArea(parseSurfaceArea(rawAd))
                        .areaMeasurementUnit(getAreaMeasurementUnit(parseAreaMeasurementUnit(rawAd)))
                        .registered(RegistrationType.NA)
                        .build();

                ads.add(land);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        return ads;
    }

    private String parseId(Element rawAd) {
        return rawAd.attr("data-id");
    }

    private String formCompleteId(String id) {
        return AdSource.HALO_OGLASI + id;
    }

    private BigDecimal parsePrice(Element rawAd) {
        Element elem = rawAd.selectFirst("div.central-feature > span");
        String price = elem.attr("data-value");
        price = price.replaceAll("[^0-9]", "");
        return new BigDecimal(price);
    }

    private String parseThumbnailUrl(Element rawAd) {
        Element elem = rawAd.selectFirst("figure.pi-img-wrapper > a > img");
        if (elem == null)
            return null;

        return elem.attr("src");

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

    private AdvertiserType getAdvertiserType(String advertiserType) {
        if ("vlasnik".equals(advertiserType))
            return AdvertiserType.OWNER;
        if ("agencija".equals(advertiserType))
            return AdvertiserType.AGENCY;
        if ("investitor".equals(advertiserType))
            return AdvertiserType.INVESTOR;
        return AdvertiserType.OWNER;
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
        Element elem = rawAd.selectFirst("ul.subtitle-places");
        if (elem == null)
            return null;

        return elem.children().stream().map(sublocation -> sublocation.ownText()).collect(Collectors.joining(", "));
    }

    private BigDecimal parseSurfaceArea(Element rawAd) {
        Element elem = rawAd.selectFirst("div.value-wrapper:contains(Kvadratura),div.value-wrapper:contains(Površina)");
        if (elem == null)
            return null;

        Matcher matcher = surfaceAreaPattern.matcher(elem.ownText());
        if (matcher.find()) {
            String area = matcher.group(1);
            area = area.replace(',', '.');
            while(true) {
                int firstDotIndex = area.indexOf('.');
                if (firstDotIndex < 0)
                    break;
                if (area.length() - firstDotIndex > 3)
                   area = area.substring(0, firstDotIndex) + area.substring(firstDotIndex + 1);
                else
                    break;
            }
            return new BigDecimal(area);
        }
        else
            return null;
    }

    private AreaMeasurementUnit getAreaMeasurementUnit(String areaUnit) {
        if (Arrays.asList("ar", "ara", "ari").contains(areaUnit))
            return AreaMeasurementUnit.ARE;
        if ("m".equals(areaUnit))
            return AreaMeasurementUnit.SQUARE_METER;
        if  ("ha".equals(areaUnit))
            return AreaMeasurementUnit.HECTARE;
        return AreaMeasurementUnit.SQUARE_METER;
    }

    private String parseAreaMeasurementUnit(Element rawAd) {
        Element elem = rawAd.selectFirst("div.value-wrapper:contains(Kvadratura),div.value-wrapper:contains(Površina)");
        if (elem == null)
            return null;

        Matcher matcher = areaUnitPattern.matcher(elem.ownText());
        if (matcher.find())
            return matcher.group(1);
        else
            return null;
    }

    private String parseRoomCount(Element rawAd) {
        Element elem = rawAd.selectFirst("div.value-wrapper:contains(Broj soba)");
        if (elem == null)
            return null;

        return elem.ownText();
    }

    private RoomCount getRoomCount(String roomCount) {
        if ("0.5".equals(roomCount))
            return RoomCount.RC_0_5;
        if ("1.0".equals(roomCount))
            return RoomCount.RC_1_0;
        if ("1.5".equals(roomCount))
            return RoomCount.RC_1_5;
        if ("2.0".equals(roomCount))
            return RoomCount.RC_2_0;
        if ("2.5".equals(roomCount))
            return RoomCount.RC_2_5;
        if ("3.0".equals(roomCount))
            return RoomCount.RC_3_0;
        if ("3.5".equals(roomCount))
            return RoomCount.RC_3_5;
        if ("4.0".equals(roomCount))
            return RoomCount.RC_4_0;
        if ("4.5".equals(roomCount))
            return RoomCount.RC_4_5;
        if ("5.0".equals(roomCount))
            return RoomCount.RC_5_0;
        if ("5+".equals(roomCount))
            return RoomCount.RC_5_p;
        else
            return RoomCount.RC_0;
    }

    private String parseAdDescription(Element rawAd) {
        Element elem = rawAd.selectFirst("p.text-description-list.ad-description.short-desc");
        if (elem == null)
            return null;

        return elem.ownText();
    }
}
