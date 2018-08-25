package com.paki.scrape.scraper.nekretniners;

import com.paki.realties.*;
import com.paki.realties.enums.*;
import com.paki.scrape.scraper.AdParser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NekretnineRsAdParser extends AdParser {
    Pattern pricePattern = Pattern.compile(".+, (.*?) .*");
    Pattern surfaceAreaPattern = Pattern.compile("(^[0-9]+)");
    Pattern publishDatePattern = Pattern.compile("(^[0-9\\.]+)");
    Pattern advertiserTypePattern = Pattern.compile("([a-zA-Z]+)$");
    Pattern idPattern = Pattern.compile(".*www\\.nekretnine\\.rs/.+?/.+?/([0-9]+)");

    public NekretnineRsAdParser(Source source) {
        super(source);
    }

    @Override
    public Set<Realty> parseApartments(Document doc) {
        Set<Realty> ads = new HashSet<>();
        for (Element rawAd: doc.select("div.resultList.fixed")) {
            try {
                String adUrl = parseAdUrl(rawAd);
                Apartment apartment = Apartment.builder()
                        .source(AdSource.NEKRETNINE_RS)
                        .externalId(formCompleteId(parseId(rawAd)))
                        .title(parseAdTitle(rawAd))
                        .description(parseAdDescription(rawAd))
                        .location(parseLocation(rawAd))
                        .publishDate(parsePublishDate(rawAd))
                        .url(adUrl)
                        .imageUrl(parseThumbnailUrl(rawAd))
                        .advertiserType(getAdvertiserType(parseAdvertiserType(rawAd)))
                        .price(parsePrice(rawAd))
                        .surfaceArea(parseSurfaceArea(rawAd))
                        .areaMeasurementUnit(AreaMeasurementUnit.SQUARE_METER)
                        .build();

                ads.add(apartment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ads;
    }

    @Override
    public Set<Realty> parseHouses(Document doc) {
        Set<Realty> ads = new HashSet<>();
        for (Element rawAd: doc.select("div.resultList.fixed")) {
            try {
                String adUrl = parseAdUrl(rawAd);
                House house = House.builder()
                        .source(AdSource.NEKRETNINE_RS)
                        .externalId(formCompleteId(parseId(rawAd)))
                        .title(parseAdTitle(rawAd))
                        .description(parseAdDescription(rawAd))
                        .location(parseLocation(rawAd))
                        .publishDate(parsePublishDate(rawAd))
                        .url(adUrl)
                        .imageUrl(parseThumbnailUrl(rawAd))
                        .advertiserType(getAdvertiserType(parseAdvertiserType(rawAd)))
                        .price(parsePrice(rawAd))
                        .surfaceArea(parseSurfaceArea(rawAd))
                        .areaMeasurementUnit(AreaMeasurementUnit.SQUARE_METER)
                        .build();

                ads.add(house);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ads;
    }

    @Override
    public Set<Realty> parseLand(Document doc) {
        Set<Realty> ads = new HashSet<>();
        for (Element rawAd: doc.select("div.resultList.fixed")) {
            try {
                String adUrl = parseAdUrl(rawAd);
                Land land = Land.builder()
                        .source(AdSource.NEKRETNINE_RS)
                        .externalId(formCompleteId(parseId(rawAd)))
                        .title(parseAdTitle(rawAd))
                        .description(parseAdDescription(rawAd))
                        .location(parseLocation(rawAd))
                        .publishDate(parsePublishDate(rawAd))
                        .url(adUrl)
                        .imageUrl(parseThumbnailUrl(rawAd))
                        .advertiserType(getAdvertiserType(parseAdvertiserType(rawAd)))
                        .price(parsePrice(rawAd))
                        .surfaceArea(parseSurfaceArea(rawAd))
                        .areaMeasurementUnit(AreaMeasurementUnit.SQUARE_METER)
                        .build();

                ads.add(land);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ads;
    }

    @Override
    public Apartment updateApartment(Apartment apartment, Document adDoc) {
        Element adData = adDoc.selectFirst("div.oglasData");
        apartment.setDescription(parseAdDescription(adData));
        apartment.setRoomCount(getRoomCount(parseRoomCount(adData)));
        apartment.setRegistered(parseRegistered(adData));
        return apartment;
    }

    @Override
    public House updateHouse(House house, Document adDoc) {
        Element adData = adDoc.selectFirst("div.oglasData");
        house.setDescription(parseAdDescription(adData));
        house.setRoomCount(getRoomCount(parseRoomCount(adData)));
        house.setRegistered(parseRegistered(adData));
        return house;
    }

    @Override
    public Land updateLand(Land land, Document adDoc) {
        Element adData = adDoc.selectFirst("div.oglasData");
        land.setDescription(parseAdDescription(adData));
        land.setRegistered(parseRegistered(adData));
        return land;
    }

    private String parseId(Element rawAd) {
        String adUrl = parseAdUrl(rawAd);
        if (adUrl == null)
            return "";
        Matcher matcher = idPattern.matcher(adUrl);
        if (matcher.find())
            return matcher.group(1);
        else
            return null;
    }

    private String formCompleteId(String id) {
        return AdSource.NEKRETNINE_RS + id;
    }

    private BigDecimal parsePrice(Element rawAd) {
        Element elem = rawAd.selectFirst("div.resultListPrice");
        if (elem == null)
            return null;
        Matcher matcher = pricePattern.matcher(elem.ownText());
        if (matcher.find()) {
            String price = matcher.group(1).replaceAll("[^0-9]", "");
            try {
                return new BigDecimal(price);
            } catch(Exception e) {
                return BigDecimal.ZERO;
            }
        } else
            return BigDecimal.ZERO;
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

    private AdvertiserType getAdvertiserType(String advertiserType) {
        if ("Vlasnik".equals(advertiserType))
            return AdvertiserType.OWNER;
        if ("Zastupnik".equals(advertiserType))
            return AdvertiserType.AGENCY;
        if ("Investitor".equals(advertiserType))
            return AdvertiserType.INVESTOR;
        return AdvertiserType.OWNER;
    }

    private String parseAdTitle(Element rawAd) {
        Element elem = rawAd.selectFirst("div.resultInfo h2 a");
        if (elem == null)
            return null;
        return elem.ownText();
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

    private String parseLocation(Element rawAd) {
        Element elem = rawAd.selectFirst("div.resultInfo > div.resultOtherWrap > div.resultData");
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

    private RoomCount getRoomCount(BigDecimal roomCount) {
        if (roomCount == null)
            return null;
        else if (roomCount.compareTo(BigDecimal.ZERO) == 0)
            return RoomCount.RC_0;
        else if (roomCount.compareTo(BigDecimal.valueOf(1)) == 0)
            return RoomCount.RC_1_0;
        else if (roomCount.compareTo(BigDecimal.valueOf(2)) == 0)
            return RoomCount.RC_2_0;
        else if (roomCount.compareTo(BigDecimal.valueOf(3)) == 0)
            return RoomCount.RC_3_0;
        else if (roomCount.compareTo(BigDecimal.valueOf(4)) == 0)
            return RoomCount.RC_4_0;
        else if (roomCount.compareTo(BigDecimal.valueOf(5)) == 0)
            return RoomCount.RC_5_0;
        else if (roomCount.compareTo(BigDecimal.valueOf(5)) > 0)
            return RoomCount.RC_5_p;
        else
            return null;
    }

    private RegistrationType parseRegistered(Element rawAd) {
        Element elem = rawAd.selectFirst("div.sLeftGrid > ul > li > div:containsOwn(Uknjiženo) + div");
        if (elem == null)
            return RegistrationType.NA;
        return "Da".equals(elem.ownText()) ? RegistrationType.REGISTERED : RegistrationType.NOT_REGISTERED;
    }
}
