package com.paki.dto.transformers;

import com.paki.dto.ValueDTO;
import com.paki.dto.realties.*;
import com.paki.realties.*;
import com.paki.realties.enums.*;
import com.paki.scrape.criteria.definitions.CriteriaDefinitions;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class RealtiesDTOTransformer {

    private static Map<Object, String> realty2dtoMappings = new HashMap<>();
    private static Map<String, Object> dto2RealtyMappings = new HashMap<>();

    private static DecimalFormat priceFormat = new DecimalFormat(",###");

    private static void addToRealtyMappings(Object k, String v) {
        realty2dtoMappings.put(k, v);
        dto2RealtyMappings.put(v, k);
    }

    static {
        addToRealtyMappings(AdSource.HALO_OGLASI, "Halo Oglasi");
        addToRealtyMappings(AdSource.NEKRETNINE_RS, "Nekretnine.rs");
        addToRealtyMappings(AdType.SELL, "Prodaja");
        addToRealtyMappings(AdType.RENT, "Izdavanje");
        addToRealtyMappings(AdvertiserType.AGENCY, "Agencija");
        addToRealtyMappings(AdvertiserType.OWNER, "Vlasnik");
        addToRealtyMappings(AdvertiserType.INVESTOR, "Investitor");
        addToRealtyMappings(RealtyType.APARTMENT, "Stan");
        addToRealtyMappings(RealtyType.HOUSE, "Kuća");
        addToRealtyMappings(RealtyType.LAND, "Zemljište");
        addToRealtyMappings(BuildType.NEW_BUILD, "Novogradnja");
        addToRealtyMappings(BuildType.OLD_BUILD, "Starogradnja");
        addToRealtyMappings(BuildType.UNDER_CONSTRUCTION, "U izgradnji");
        addToRealtyMappings(HeatingType.CENTRAL, "Centralno");
        addToRealtyMappings(HeatingType.OWN_CENTRAL, "Etažno");
        addToRealtyMappings(HeatingType.GAS, "Gas");
        addToRealtyMappings(HeatingType.ELECTRIC, "Struja");
        addToRealtyMappings(RegistrationType.REGISTERED, "Uknjiženo");
        addToRealtyMappings(RegistrationType.NOT_REGISTERED, "Neuknjiženo");
        addToRealtyMappings(ApartmentType.DUPLEX, "Dupleks");
        addToRealtyMappings(ApartmentType.LOFT, "Potkrovlje");
        addToRealtyMappings(ApartmentType.PENTHOUSE, "Penthaus");
        addToRealtyMappings(ApartmentType.WITH_SALON, "Salonac");
        addToRealtyMappings(ApartmentType.WITH_YARD, "Sa dvorištem");
        addToRealtyMappings(Facilities.TERRASSE, "Terasa");
        addToRealtyMappings(Facilities.BALCONY, "Balkon");
        addToRealtyMappings(Facilities.FRENCH_BALCONY, "Francuski balkon");
        addToRealtyMappings(Facilities.LOGGIA, "Lođa");
        addToRealtyMappings(Facilities.GARAGE, "Garaža");
        addToRealtyMappings(Facilities.PARKING, "Parking");
        addToRealtyMappings(RoomCount.RC_0, "0");
        addToRealtyMappings(RoomCount.RC_0_5, "0.5");
        addToRealtyMappings(RoomCount.RC_1_0, "1.0");
        addToRealtyMappings(RoomCount.RC_1_5, "1.5");
        addToRealtyMappings(RoomCount.RC_2_0, "2.0");
        addToRealtyMappings(RoomCount.RC_2_5, "2.5");
        addToRealtyMappings(RoomCount.RC_3_0, "3.0");
        addToRealtyMappings(RoomCount.RC_3_5, "3.5");
        addToRealtyMappings(RoomCount.RC_4_0, "4.0");
        addToRealtyMappings(RoomCount.RC_4_5, "4.5");
        addToRealtyMappings(RoomCount.RC_5_0, "5.0");
        addToRealtyMappings(RoomCount.RC_5_p, "5+");
        addToRealtyMappings(CriteriaDefinitions.SUBTERRAIN, "Suteren");
        addToRealtyMappings(CriteriaDefinitions.LOW_GROUND_FLOOR, "Nisko prizemlje");
        addToRealtyMappings(CriteriaDefinitions.HIGH_GROUND_FLOOR, "Visoko prizemlje");
        addToRealtyMappings(CriteriaDefinitions.GROUND_FLOOR, "Prizemlje");
        addToRealtyMappings(AreaMeasurementUnit.SQUARE_METER, "m2");
        addToRealtyMappings(AreaMeasurementUnit.ARE, "ar");
        addToRealtyMappings(AreaMeasurementUnit.HECTARE, "hektar");
    }

    public List<? extends RealtyDTO> transformRealtiesToDTO(Collection<? extends Realty> realties) {
        return realties.stream()
                .map(this::transformRealtyToDTO)
                .collect(Collectors.toList());
    }

    public RealtyDTO transformRealtyToDTO(Realty realty) {
        if (realty instanceof Apartment)
            return transformApartmentToDto((Apartment) realty);
        if (realty instanceof House)
            return transformHouseToDto((House) realty);
        if (realty instanceof Land)
            return transformLandToDto((Land) realty);
        return null;
    }

    private ApartmentDTO transformApartmentToDto(Apartment apartment) {
        ApartmentDTO dto = new ApartmentDTO();

        dto.setRealtyType(RealtyDTOType.APARTMENT);
        populateHomeFields(apartment, dto);

        return dto;
    }

    private HouseDTO transformHouseToDto(House house) {
        HouseDTO dto = new HouseDTO();

        dto.setRealtyType(RealtyDTOType.HOUSE);
        populateHomeFields(house, dto);

        return dto;
    }

    private LandDTO transformLandToDto(Land land) {
        LandDTO dto = new LandDTO();

        dto.setRealtyType(RealtyDTOType.LAND);
        populateCommonFields(land, dto);

        return dto;
    }

    private void populateHomeFields(Home home, HomeDTO dto) {
        dto.setRooms(transformToValueDTO(home.getRoomCount()));
        populateCommonFields(home, dto);
    }

    private void populateCommonFields(Realty realty, RealtyDTO dto) {
        dto.setAdSource(transformToValueDTO(realty.getSource()));
        dto.setAdType(transformToValueDTO(realty.getAdType()));
        dto.setTitle(new ValueDTO("Naslov", realty.getTitle()));
        dto.setDescription(new ValueDTO("Opis", realty.getDescription()));
        dto.setLocation(new ValueDTO("Lokacija", realty.getLocation()));
        dto.setPrice(new ValueDTO("Cena", formatPrice(realty.getPrice())));
        dto.setUrl(new ValueDTO("URL", realty.getUrl()));
        dto.setImageUrl(new ValueDTO("Image URL", realty.getImageUrl()));
        dto.setPublishDate(new ValueDTO("Datum oglasa", formatDate(realty.getPublishDate())));
        dto.setAdvertiser(transformToValueDTO(realty.getAdvertiserType()));
        dto.setSurfaceArea(new ValueDTO("Površina", formatSurfaceArea(realty)));
        dto.setRegistration(transformRegistrationToValueDTO(realty.getRegistered()));
    }

    private ValueDTO transformToValueDTO(Object value) {
        return value == null ? null : new ValueDTO(value.toString(), realty2dtoMappings.get(value));
    }

    private ValueDTO transformRegistrationToValueDTO(Object value) {
        String registration = realty2dtoMappings.getOrDefault(value, "");
        return new ValueDTO(value.toString(), registration);
    }

    private String formatPrice(BigDecimal price) {
        return priceFormat.format(price).replace(',', '.');
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(dateFormatter);
    }

    private String formatSurfaceArea(Realty realty) {
        return realty.getSurfaceArea().toString() + realty2dtoMappings.get(realty.getAreaMeasurementUnit());
    }
}
