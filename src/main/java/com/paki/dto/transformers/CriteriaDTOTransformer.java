package com.paki.dto.transformers;

import com.google.gson.*;
import com.paki.dto.ValueDTO;
import com.paki.dto.criteria.*;
import com.paki.dto.criteria.definition.CriteriaDefinitionDTO;
import com.paki.dto.criteria.definition.CriteriaDefinitionsDTO;
import com.paki.dto.criteria.definition.LocationCriteriaDefinitionDTO;
import com.paki.realties.enums.*;
import com.paki.realties.locations.Location;
import com.paki.realties.locations.LocationsGenerator;
import com.paki.scrape.criteria.*;
import com.paki.scrape.criteria.definitions.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CriteriaDTOTransformer {
    private static String TIP_OGLASA = "Tip oglasa";
    private static String TIP_NEKRETNINE = "Tip nekretnine";
    private static String OGLASIVAC = "Oglašivač";
    private static String GRADNJA = "Gradnja";
    private static String GREJANJE = "Grejanje";
    private static String UKNJIZENO = "Uknjiženost";
    private static String TIP_STANA = "Tip stana";
    private static String DODATNO = "Dodatno";
    private static String BROJ_SOBA = "Broj soba";
    private static String CENA = "Cena";
    private static String POVRSINA_M2 = "Površina u m2";
    private static String POVRSINA_ARI = "Površina u arima";
    private static String CENA_PO_M2 = "Cena po m2";
    private static String CENA_PO_ARU = "Cena po aru";
    private static String SPRATNOST = "Spratnost";
    private static String LOKACIJA = "Lokacija";

    private static Map<Object, String> criteria2dtoMappings = new HashMap<>();
    private static Map<String, Object> dto2CriteriaMappings = new HashMap<>();

    private static void addToCriteriaMappings(Object k, String v) {
        criteria2dtoMappings.put(k, v);
        dto2CriteriaMappings.put(v, k);
    }
    static {
        addToCriteriaMappings(AdType.SELL, "Prodaja");
        addToCriteriaMappings(AdType.RENT, "Izdavanje");
        addToCriteriaMappings(AdvertiserType.AGENCY, "Agencija");
        addToCriteriaMappings(AdvertiserType.OWNER, "Vlasnik");
        addToCriteriaMappings(AdvertiserType.INVESTOR, "Investitor");
        addToCriteriaMappings(RealtyType.APARTMENT, "Stan");
        addToCriteriaMappings(RealtyType.HOUSE, "Kuća");
        addToCriteriaMappings(RealtyType.LAND, "Zemljište");
        addToCriteriaMappings(BuildType.NEW_BUILD, "Novogradnja");
        addToCriteriaMappings(BuildType.OLD_BUILD, "Starogradnja");
        addToCriteriaMappings(BuildType.UNDER_CONSTRUCTION, "U izgradnji");
        addToCriteriaMappings(HeatingType.CENTRAL, "Centralno");
        addToCriteriaMappings(HeatingType.OWN_CENTRAL, "Etažno");
        addToCriteriaMappings(HeatingType.GAS, "Gas");
        addToCriteriaMappings(HeatingType.ELECTRIC, "Struja");
        addToCriteriaMappings(RegistrationType.REGISTERED, "Uknjiženo");
        addToCriteriaMappings(ApartmentType.DUPLEX, "Dupleks");
        addToCriteriaMappings(ApartmentType.LOFT, "Potkrovlje");
        addToCriteriaMappings(ApartmentType.PENTHOUSE, "Penthaus");
        addToCriteriaMappings(ApartmentType.WITH_SALON, "Salonac");
        addToCriteriaMappings(ApartmentType.WITH_YARD, "Sa dvorištem");
        addToCriteriaMappings(Facilities.TERRASSE, "Terasa");
        addToCriteriaMappings(Facilities.BALCONY, "Balkon");
        addToCriteriaMappings(Facilities.FRENCH_BALCONY, "Francuski balkon");
        addToCriteriaMappings(Facilities.LOGGIA, "Lođa");
        addToCriteriaMappings(Facilities.GARAGE, "Garaža");
        addToCriteriaMappings(Facilities.PARKING, "Parking");
        addToCriteriaMappings(RoomCount.RC_0, "0");
        addToCriteriaMappings(RoomCount.RC_0_5, "0.5");
        addToCriteriaMappings(RoomCount.RC_1_0, "1.0");
        addToCriteriaMappings(RoomCount.RC_1_5, "1.5");
        addToCriteriaMappings(RoomCount.RC_2_0, "2.0");
        addToCriteriaMappings(RoomCount.RC_2_5, "2.5");
        addToCriteriaMappings(RoomCount.RC_3_0, "3.0");
        addToCriteriaMappings(RoomCount.RC_3_5, "3.5");
        addToCriteriaMappings(RoomCount.RC_4_0, "4.0");
        addToCriteriaMappings(RoomCount.RC_4_5, "4.5");
        addToCriteriaMappings(RoomCount.RC_5_0, "5.0");
        addToCriteriaMappings(RoomCount.RC_5_p, "5+");
        addToCriteriaMappings(CriteriaDefinitions.SUBTERRAIN, "Suteren");
        addToCriteriaMappings(CriteriaDefinitions.LOW_GROUND_FLOOR, "Nisko prizemlje");
        addToCriteriaMappings(CriteriaDefinitions.HIGH_GROUND_FLOOR, "Visoko prizemlje");
        addToCriteriaMappings(CriteriaDefinitions.GROUND_FLOOR, "Prizemlje");
    }

    public CriteriaDefinitionsDTO transformCriteriaDefinitionsToDTO(List<BaseCriteriaDefinition> criteriaDefinitions) {
        List<CriteriaDefinitionDTO> criteriaDefinitionDTOs = new LinkedList<>();
        LocationCriteriaDefinitionDTO locationCriteriaDefinitionDTO = null;

        for (BaseCriteriaDefinition criteriaDefinition: criteriaDefinitions) {
            if (criteriaDefinition instanceof LocationCriteriaDefinition && CriteriaDefinitions.LOCATION.equals(criteriaDefinition.getName())) {
                LocationCriteriaDefinition locationCriteriaDefinition = (LocationCriteriaDefinition) criteriaDefinition;
                locationCriteriaDefinitionDTO = new LocationCriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.LOCATION, LOKACIJA), CriteriaDTOType.MULTI_SELECT_TREE, transformLocationsToLocationDTOs(locationCriteriaDefinition.getLocations()));
            } else {
                criteriaDefinitionDTOs.add(transformCriteriaDefinitionToDTO(criteriaDefinition));
            }
        }

        return new CriteriaDefinitionsDTO(criteriaDefinitionDTOs, locationCriteriaDefinitionDTO);
    }

    private CriteriaDefinitionDTO transformCriteriaDefinitionToDTO(BaseCriteriaDefinition definition) {
        if (definition instanceof SingleSelectCriteriaDefinition) {
            SingleSelectCriteriaDefinition sscDef = (SingleSelectCriteriaDefinition) definition;
            if (CriteriaDefinitions.AD_TYPE.equals(sscDef.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.AD_TYPE, TIP_OGLASA), CriteriaDTOType.SINGLE_SELECT, transformToValueDTOs(sscDef.getValues()));
            else if (CriteriaDefinitions.REALTY_TYPE.equals(sscDef.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.REALTY_TYPE, TIP_NEKRETNINE), CriteriaDTOType.SINGLE_SELECT, transformToValueDTOs(sscDef.getValues()));
        } else if (definition instanceof MultiSelectCriteriaDefinition) {
            MultiSelectCriteriaDefinition mscDef = (MultiSelectCriteriaDefinition) definition;
            if (CriteriaDefinitions.ADVERTISER.equals(mscDef.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.ADVERTISER, OGLASIVAC), CriteriaDTOType.MULTI_SELECT, transformToValueDTOs(mscDef.getValues()));
            else if (CriteriaDefinitions.BUILD_TYPE.equals(mscDef.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.BUILD_TYPE, GRADNJA), CriteriaDTOType.MULTI_SELECT, transformToValueDTOs(mscDef.getValues()));
            else if (CriteriaDefinitions.HEATING_TYPE.equals(mscDef.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.HEATING_TYPE, GREJANJE), CriteriaDTOType.MULTI_SELECT, transformToValueDTOs(mscDef.getValues()));
            else if (CriteriaDefinitions.REGISTRATION.equals(mscDef.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.REGISTRATION, UKNJIZENO), CriteriaDTOType.MULTI_SELECT, transformToValueDTOs(mscDef.getValues()));
            else if (CriteriaDefinitions.APARTMENT_TYPE.equals(mscDef.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.APARTMENT_TYPE, TIP_STANA), CriteriaDTOType.MULTI_SELECT, transformToValueDTOs(mscDef.getValues()));
            else if (CriteriaDefinitions.FACILITIES.equals(mscDef.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.FACILITIES, DODATNO), CriteriaDTOType.MULTI_SELECT, transformToValueDTOs(mscDef.getValues()));
        } else if (definition instanceof RangeWithUnitCriteriaDefinition) {
            RangeWithUnitCriteriaDefinition rwucDef = (RangeWithUnitCriteriaDefinition) definition;
            if (CriteriaDefinitions.SURFACE_M2.equals(rwucDef.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.SURFACE_M2, POVRSINA_M2), CriteriaDTOType.RANGE, null);
            else if (CriteriaDefinitions.SURFACE_ARE.equals(rwucDef.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.SURFACE_ARE, POVRSINA_ARI), CriteriaDTOType.RANGE, null);
            else if (CriteriaDefinitions.PRICE_PER_M2.equals(rwucDef.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.PRICE_PER_M2, CENA_PO_M2), CriteriaDTOType.RANGE, null);
            else if (CriteriaDefinitions.PRICE_PER_ARE.equals(rwucDef.getName()))
                    return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.PRICE_PER_ARE, CENA_PO_ARU), CriteriaDTOType.RANGE, null);
        } else if (definition instanceof RangeSingleSelectCriteriaDefinition) {
            RangeSingleSelectCriteriaDefinition rsscDef = (RangeSingleSelectCriteriaDefinition) definition;
            if (CriteriaDefinitions.ROOM_COUNT.equals(definition.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.ROOM_COUNT, BROJ_SOBA), CriteriaDTOType.RANGE_SELECT, transformToValueDTOs(rsscDef.getValues()));
            else if (CriteriaDefinitions.FLOOR.equals(definition.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.FLOOR, SPRATNOST), CriteriaDTOType.RANGE_SELECT, transformFloorsToValueDTOs((Integer[])rsscDef.getValues()));
        } else if (definition instanceof RangeCriteriaDefinition) {
            if (CriteriaDefinitions.PRICE.equals(definition.getName()))
                return new CriteriaDefinitionDTO(new ValueDTO(CriteriaDefinitions.PRICE, CENA), CriteriaDTOType.RANGE, null);
        }
        return null;
    }

    public CriteriaDTO transformCriteriaToDTO(BaseCriteria criteria) {
        if (criteria instanceof LocationCriteria) {
            LocationCriteria locationCriteria = (LocationCriteria) criteria;
            List<ValueDTO> transformedLocations = locationCriteria.getLocations().stream()
                    .map(v ->
                    {
                        Location location = LocationsGenerator.getLocation(v);
                        return new ValueDTO(v, location.getName());
                    })
                    .collect(Collectors.toList());
            return new MultiValueCriteriaDTO(new ValueDTO(CriteriaDefinitions.LOCATION, LOKACIJA), CriteriaDTOType.MULTI_SELECT_TREE, transformedLocations);
        } else if (criteria instanceof RangeCriteria) {
            RangeCriteria rangeCriteria = (RangeCriteria) criteria;
            if (CriteriaDefinitions.SURFACE_M2.equals(rangeCriteria.getName()))
                return new RangeCriteriaDTO(new ValueDTO(CriteriaDefinitions.SURFACE_M2, POVRSINA_M2), CriteriaDTOType.RANGE, transformIntegerToValueDTO(rangeCriteria.getRangeFrom()), transformIntegerToValueDTO(rangeCriteria.getRangeTo()));
            else if (CriteriaDefinitions.SURFACE_ARE.equals(rangeCriteria.getName()))
                return new RangeCriteriaDTO(new ValueDTO(CriteriaDefinitions.SURFACE_ARE, POVRSINA_ARI), CriteriaDTOType.RANGE, transformIntegerToValueDTO(rangeCriteria.getRangeFrom()), transformIntegerToValueDTO(rangeCriteria.getRangeTo()));
            else if (CriteriaDefinitions.PRICE_PER_M2.equals(rangeCriteria.getName()))
                return new RangeCriteriaDTO(new ValueDTO(CriteriaDefinitions.PRICE_PER_M2, CENA_PO_M2), CriteriaDTOType.RANGE, transformIntegerToValueDTO(rangeCriteria.getRangeFrom()), transformIntegerToValueDTO(rangeCriteria.getRangeTo()));
            else if (CriteriaDefinitions.PRICE_PER_ARE.equals(rangeCriteria.getName()))
                return new RangeCriteriaDTO(new ValueDTO(CriteriaDefinitions.PRICE_PER_ARE, CENA_PO_ARU), CriteriaDTOType.RANGE, transformIntegerToValueDTO(rangeCriteria.getRangeFrom()), transformIntegerToValueDTO(rangeCriteria.getRangeTo()));
            else if (CriteriaDefinitions.PRICE.equals(rangeCriteria.getName()))
                return new RangeCriteriaDTO(new ValueDTO(CriteriaDefinitions.PRICE, CENA), CriteriaDTOType.RANGE, transformIntegerToValueDTO(rangeCriteria.getRangeFrom()), transformIntegerToValueDTO(rangeCriteria.getRangeTo()));
            else if (CriteriaDefinitions.FLOOR.equals(rangeCriteria.getName()))
                return new RangeCriteriaDTO(new ValueDTO(CriteriaDefinitions.FLOOR, SPRATNOST), CriteriaDTOType.RANGE, transformFloorToValueDTO(rangeCriteria.getRangeFrom()), transformFloorToValueDTO(rangeCriteria.getRangeTo()));
            else if (CriteriaDefinitions.ROOM_COUNT.equals(rangeCriteria.getName()))
                return new RangeCriteriaDTO(new ValueDTO(CriteriaDefinitions.ROOM_COUNT, BROJ_SOBA), CriteriaDTOType.RANGE, transformToValueDTO(rangeCriteria.getRangeFrom()), transformToValueDTO(rangeCriteria.getRangeTo()));
        } else if (criteria instanceof MultiValueCriteria) {
            MultiValueCriteria mvCriteria = (MultiValueCriteria) criteria;
            if (CriteriaDefinitions.ADVERTISER.equals(mvCriteria.getName()))
                return new MultiValueCriteriaDTO(new ValueDTO(CriteriaDefinitions.ADVERTISER, OGLASIVAC), CriteriaDTOType.MULTI_SELECT, transformToValueDTOs(mvCriteria.getValues()));
            else if (CriteriaDefinitions.REGISTRATION.equals(mvCriteria.getName()))
                return new MultiValueCriteriaDTO(new ValueDTO(CriteriaDefinitions.REGISTRATION, UKNJIZENO), CriteriaDTOType.MULTI_SELECT, transformToValueDTOs(mvCriteria.getValues()));
            else if (CriteriaDefinitions.BUILD_TYPE.equals(mvCriteria.getName()))
                return new MultiValueCriteriaDTO(new ValueDTO(CriteriaDefinitions.BUILD_TYPE, GRADNJA), CriteriaDTOType.MULTI_SELECT, transformToValueDTOs(mvCriteria.getValues()));
            else if (CriteriaDefinitions.APARTMENT_TYPE.equals(mvCriteria.getName()))
                return new MultiValueCriteriaDTO(new ValueDTO(CriteriaDefinitions.APARTMENT_TYPE, TIP_STANA), CriteriaDTOType.MULTI_SELECT, transformToValueDTOs(mvCriteria.getValues()));
            else if (CriteriaDefinitions.FACILITIES.equals(mvCriteria.getName()))
                return new MultiValueCriteriaDTO(new ValueDTO(CriteriaDefinitions.FACILITIES, DODATNO), CriteriaDTOType.MULTI_SELECT, transformToValueDTOs(mvCriteria.getValues()));
            else if (CriteriaDefinitions.HEATING_TYPE.equals(mvCriteria.getName()))
                return new MultiValueCriteriaDTO(new ValueDTO(CriteriaDefinitions.HEATING_TYPE, GREJANJE), CriteriaDTOType.MULTI_SELECT, transformToValueDTOs(mvCriteria.getValues()));
        } else if (criteria instanceof SingleValueCriteria) {
            SingleValueCriteria svCriteria = (SingleValueCriteria) criteria;
            if (CriteriaDefinitions.AD_TYPE.equals(svCriteria.getName()))
                return new SingleValueCriteriaDTO(new ValueDTO(CriteriaDefinitions.AD_TYPE, TIP_OGLASA), CriteriaDTOType.SINGLE_SELECT, transformToValueDTO(svCriteria.getValue()));
            else if (CriteriaDefinitions.REALTY_TYPE.equals(svCriteria.getName()))
                return new SingleValueCriteriaDTO(new ValueDTO(CriteriaDefinitions.REALTY_TYPE, TIP_NEKRETNINE), CriteriaDTOType.SINGLE_SELECT, transformToValueDTO(svCriteria.getValue()));
        }
        return null;
    }

    public BaseCriteria transformDTOToCriteria(CriteriaDTO dto) {
        switch (dto.getCriteriaType()) {
            case SINGLE_SELECT:
                SingleValueCriteriaDTO svDto = (SingleValueCriteriaDTO) dto;
                if (svDto.getValue() == null)
                    return null;
                else
                    return new SingleValueCriteria(svDto.getName().getName(), svDto.getValue().getName());
            case MULTI_SELECT:
                MultiValueCriteriaDTO mvDto = (MultiValueCriteriaDTO) dto;
                if (mvDto.getValues() == null)
                    return null;
                else
                    return new MultiValueCriteria(mvDto.getName().getName(), mvDto.getValues().stream().map(v -> v.getName()).collect(Collectors.toSet()));
            case MULTI_SELECT_TREE:
                MultiValueCriteriaDTO locationDto = (MultiValueCriteriaDTO) dto;
                if (locationDto.getValues() == null)
                    return null;
                else
                    return transformDTOToLocationCriteria(locationDto);
            case RANGE:
                RangeCriteriaDTO rangeDto = (RangeCriteriaDTO) dto;
                if (rangeDto.getFrom() == null && rangeDto.getTo() == null)
                    return null;
                else {
                    if (Arrays.asList(CriteriaDefinitions.SURFACE_M2, CriteriaDefinitions.SURFACE_ARE, CriteriaDefinitions.PRICE_PER_M2, CriteriaDefinitions.PRICE_PER_ARE).contains(rangeDto.getName().getName())) {
                        AreaMeasurementUnit unit =
                                Arrays.asList(CriteriaDefinitions.SURFACE_M2, CriteriaDefinitions.PRICE_PER_M2).contains(rangeDto.getName().getName())
                                        ? AreaMeasurementUnit.SQUARE_METER
                                        : Arrays.asList(CriteriaDefinitions.SURFACE_ARE, CriteriaDefinitions.PRICE_PER_ARE).contains(rangeDto.getName().getName())
                                            ? AreaMeasurementUnit.ARE
                                            : AreaMeasurementUnit.HECTARE;
                        return new RangeWithUnitCriteria(rangeDto.getName().getName(), Integer.valueOf(rangeDto.getFrom().getName()), Integer.valueOf(rangeDto.getTo().getName()), unit);
                    }
                    else if (Arrays.asList(CriteriaDefinitions.PRICE, CriteriaDefinitions.FLOOR).contains(rangeDto.getName().getName())) {
                        return new IntegerRangeCriteria(rangeDto.getName().getName(), Integer.valueOf(rangeDto.getFrom().getName()), Integer.valueOf(rangeDto.getTo().getName()));
                    } else {
                        return new StringRangeCriteria(rangeDto.getName().getName(), rangeDto.getFrom().getName(), rangeDto.getTo().getName());
                    }
                }
        }
        return null;
    }

    private LocationCriteria transformDTOToLocationCriteria(MultiValueCriteriaDTO dto) {
        LocationCriteria locationCriteria = new LocationCriteria();
        locationCriteria.setName(CriteriaDefinitions.LOCATION);
        Set<String> locationSet = dto.getValues().stream()
                .map(ValueDTO::getName)
                .collect(Collectors.toSet());
        locationCriteria.setLocations(locationSet);
        return locationCriteria;
    }

    private ValueDTO transformIntegerToValueDTO(Object o) {
        if (o == null)
            return null;
        else
            return new ValueDTO(((Integer) o).toString(), ((Integer) o).toString());
    }

    private ValueDTO[] transformToValueDTOs(Object[] values) {
        return Arrays.stream(values).map(value -> new ValueDTO(value.toString(), criteria2dtoMappings.get(value))).toArray(ValueDTO[]::new);
    }

    private List<ValueDTO> transformToValueDTOs(Set<String> values) {
        if (values == null)
            return Collections.emptyList();
        return values.stream().map(value -> new ValueDTO(value, criteria2dtoMappings.get(value))).collect(Collectors.toList());
    }

    public ValueDTO transformToValueDTO(Object value) {
        return value == null ? null : new ValueDTO(value.toString(), criteria2dtoMappings.get(value));
    }

    private ValueDTO[] transformFloorsToValueDTOs(Integer[] values) {
        ValueDTO[] transformedValues = new ValueDTO[values.length];
        for (int i=0; i<values.length; i++)
            transformedValues[i] = new ValueDTO(values[i].toString(), transformFloorToString(values[i]));
        return transformedValues;
    }

    private String transformFloorToString(Integer value) {
        return criteria2dtoMappings.getOrDefault(value, value.toString());
    }

    private ValueDTO transformFloorToValueDTO(Object o) {
        if (o == null)
            return null;
        else {
            Integer value = (Integer) o;
            return new ValueDTO(value.toString(), criteria2dtoMappings.getOrDefault(value, value.toString()));
        }
    }

    private LocationDTO transformLocationToLocationDTO(Location location) {
        LocationDTO dto = new LocationDTO(new ValueDTO(location.getId(), location.getName()), null, null);
        if (location.getSublocations() == null || location.getSublocations().isEmpty())
            return dto;
        else {
            List<LocationDTO> sublocationDTOs = location.getSublocations().stream().map(this::transformLocationToLocationDTO).collect(Collectors.toList());
            dto.setSublocations(sublocationDTOs);
        }
        return dto;
    }

    private List<LocationDTO> transformLocationsToLocationDTOs(List<Location> locations) {
        if (locations == null || locations.isEmpty())
            return Collections.emptyList();
        else
            return locations.stream().map(this::transformLocationToLocationDTO).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        CriteriaDTOTransformer criteriaDtoTransformer = new CriteriaDTOTransformer();
        CriteriaDefinitionsDTO dto = criteriaDtoTransformer.transformCriteriaDefinitionsToDTO(CriteriaDefinitions.getDefinitions(RealtyType.APARTMENT));

        class CollectionAdapter implements JsonSerializer<Collection<?>> {
            @Override
            public JsonElement serialize(Collection<?> src, Type typeOfSrc, JsonSerializationContext context) {
                if (src == null || src.isEmpty()) // exclusion is made here
                    return null;

                JsonArray array = new JsonArray();

                for (Object child : src) {
                    JsonElement element = context.serialize(child);
                    array.add(element);
                }

                return array;
            }
        }

        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(Collection.class, new CollectionAdapter()).create();
        String json = gson.toJson(dto);
        System.out.println(json);
        List<BaseCriteria> criteriaList = testCriteria();
        List<CriteriaDTO> criteriaDTOS = criteriaList.stream().map(criteriaDtoTransformer::transformCriteriaToDTO).collect(Collectors.toList());
        json = gson.toJson(criteriaDTOS);
        System.out.println(json);
    }

    public static List<BaseCriteria> testCriteria() {
        List<BaseCriteria> criteriaList = new ArrayList<>();
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.AD_TYPE, AdType.SELL.name()));
        criteriaList.add(new SingleValueCriteria(CriteriaDefinitions.REALTY_TYPE, RealtyType.APARTMENT.name()));
        criteriaList.add(new MultiValueCriteria(CriteriaDefinitions.REGISTRATION, new HashSet<>(Arrays.asList(RegistrationType.REGISTERED.name()))));
        criteriaList.add(new MultiValueCriteria(CriteriaDefinitions.APARTMENT_TYPE, new HashSet<>(Arrays.asList(ApartmentType.DUPLEX.name()))));
        criteriaList.add(new MultiValueCriteria(CriteriaDefinitions.HEATING_TYPE, new HashSet<>(Arrays.asList(HeatingType.GAS.name()))));
        criteriaList.add(new IntegerRangeCriteria(CriteriaDefinitions.PRICE, null, 123123));
        criteriaList.add(new RangeWithUnitCriteria(CriteriaDefinitions.SURFACE_M2, null, 123, AreaMeasurementUnit.SQUARE_METER));
        criteriaList.add(new StringRangeCriteria(CriteriaDefinitions.ROOM_COUNT, null, RoomCount.RC_5_0.name()));
        criteriaList.add(new IntegerRangeCriteria(CriteriaDefinitions.FLOOR, CriteriaDefinitions.HIGH_GROUND_FLOOR, null));
        criteriaList.add(new LocationCriteria(CriteriaDefinitions.LOCATION, new HashSet<>(Arrays.asList(LocationsGenerator.getLocations().get(2).getId(), LocationsGenerator.getLocations().get(1).getSublocations().get(0).getId()))));
        return criteriaList;
    }
}
