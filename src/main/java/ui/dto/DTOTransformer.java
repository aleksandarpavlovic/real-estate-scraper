package ui.dto;

import com.google.gson.*;
import realties.enums.*;
import realties.locations.Location;
import scrape.criteria.*;
import scrape.criteria.definitions.*;
import ui.dto.criteria.CriteriaDTO;
import ui.dto.criteria.MultiValueCriteriaDTO;
import ui.dto.criteria.RangeCriteriaDTO;
import ui.dto.criteria.SingleValueCriteriaDTO;
import ui.dto.criteria_definition.CriteriaDefinitionDTO;
import ui.dto.criteria_definition.CriteriaDefinitionsDTO;
import ui.dto.criteria_definition.LocationCriteriaDefinitionDTO;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class DTOTransformer {
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
                locationCriteriaDefinitionDTO = new LocationCriteriaDefinitionDTO(LOKACIJA, CriteriaDTOType.MULTI_SELECT_TREE, locationCriteriaDefinition.getLocations());
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
                return new CriteriaDefinitionDTO(TIP_OGLASA, CriteriaDTOType.SINGLE_SELECT, transformValues(sscDef.getValues()));
            else if (CriteriaDefinitions.REALTY_TYPE.equals(sscDef.getName()))
                return new CriteriaDefinitionDTO(TIP_NEKRETNINE, CriteriaDTOType.SINGLE_SELECT, transformValues(sscDef.getValues()));
        } else if (definition instanceof MultiSelectCriteriaDefinition) {
            MultiSelectCriteriaDefinition mscDef = (MultiSelectCriteriaDefinition) definition;
            if (CriteriaDefinitions.ADVERTISER.equals(mscDef.getName()))
                return new CriteriaDefinitionDTO(OGLASIVAC, CriteriaDTOType.MULTI_SELECT, transformValues(mscDef.getValues()));
            else if (CriteriaDefinitions.BUILD_TYPE.equals(mscDef.getName()))
                return new CriteriaDefinitionDTO(GRADNJA, CriteriaDTOType.MULTI_SELECT, transformValues(mscDef.getValues()));
            else if (CriteriaDefinitions.HEATING_TYPE.equals(mscDef.getName()))
                return new CriteriaDefinitionDTO(GREJANJE, CriteriaDTOType.MULTI_SELECT, transformValues(mscDef.getValues()));
            else if (CriteriaDefinitions.REGISTERED.equals(mscDef.getName()))
                return new CriteriaDefinitionDTO(UKNJIZENO, CriteriaDTOType.MULTI_SELECT, transformValues(mscDef.getValues()));
            else if (CriteriaDefinitions.APARTMENT_TYPE.equals(mscDef.getName()))
                return new CriteriaDefinitionDTO(TIP_STANA, CriteriaDTOType.MULTI_SELECT, transformValues(mscDef.getValues()));
            else if (CriteriaDefinitions.FACILITIES.equals(mscDef.getName()))
                return new CriteriaDefinitionDTO(DODATNO, CriteriaDTOType.MULTI_SELECT, transformValues(mscDef.getValues()));
        } else if (definition instanceof RangeWithUnitCriteriaDefinition) {
            RangeWithUnitCriteriaDefinition rwucDef = (RangeWithUnitCriteriaDefinition) definition;
            if (CriteriaDefinitions.SURFACE_AREA.equals(rwucDef.getName())) {
                if (rwucDef.getUnit() == AreaMeasurementUnit.SQUARE_METER)
                    return new CriteriaDefinitionDTO(POVRSINA_M2, CriteriaDTOType.RANGE, null);
                else if (rwucDef.getUnit() == AreaMeasurementUnit.ARE)
                    return new CriteriaDefinitionDTO(POVRSINA_ARI, CriteriaDTOType.RANGE, null);
            } else if (CriteriaDefinitions.PRICE_PER_AREA_UNIT.equals(rwucDef.getName())) {
                if (rwucDef.getUnit() == AreaMeasurementUnit.SQUARE_METER)
                    return new CriteriaDefinitionDTO(CENA_PO_M2, CriteriaDTOType.RANGE, null);
                else if (rwucDef.getUnit() == AreaMeasurementUnit.ARE)
                    return new CriteriaDefinitionDTO(CENA_PO_ARU, CriteriaDTOType.RANGE, null);
            }
        } else if (definition instanceof RangeSingleSelectCriteriaDefinition) {
            RangeSingleSelectCriteriaDefinition rsscDef = (RangeSingleSelectCriteriaDefinition) definition;
            if (CriteriaDefinitions.ROOM_COUNT.equals(definition.getName()))
                return new CriteriaDefinitionDTO(BROJ_SOBA, CriteriaDTOType.RANGE_SELECT, transformValues(rsscDef.getValues()));
            else if (CriteriaDefinitions.FLOOR.equals(definition.getName()))
                return new CriteriaDefinitionDTO(SPRATNOST, CriteriaDTOType.RANGE_SELECT, transformFloors((Integer[])rsscDef.getValues()));
        } else if (definition instanceof RangeCriteriaDefinition) {
            if (CriteriaDefinitions.PRICE.equals(definition.getName()))
                return new CriteriaDefinitionDTO(CENA, CriteriaDTOType.RANGE, null);
        }
        return null;
    }

    public CriteriaDTO transformCriteriaToDTO(BaseCriteria criteria) {
        if (criteria instanceof LocationCriteria) {
            LocationCriteria locationCriteria = (LocationCriteria) criteria;
            List<String> transformedLocations = locationCriteria.getValues().stream().map(Location::getId).collect(Collectors.toList());
            return new MultiValueCriteriaDTO(LOKACIJA, CriteriaDTOType.MULTI_SELECT_TREE, transformedLocations);
        } else if (criteria instanceof RangeWithUnitCriteria) {
            RangeWithUnitCriteria rwuCriteria = (RangeWithUnitCriteria) criteria;
            if (CriteriaDefinitions.SURFACE_AREA.equals(rwuCriteria.getName())) {
                if (rwuCriteria.getUnit() == AreaMeasurementUnit.SQUARE_METER)
                    return new RangeCriteriaDTO(POVRSINA_M2, CriteriaDTOType.RANGE, rangeValueToString(rwuCriteria.getFrom()), rangeValueToString(rwuCriteria.getTo()));
                else if (rwuCriteria.getUnit() == AreaMeasurementUnit.ARE)
                    return new RangeCriteriaDTO(POVRSINA_ARI, CriteriaDTOType.RANGE, rangeValueToString(rwuCriteria.getFrom()), rangeValueToString(rwuCriteria.getTo()));
            } else if (CriteriaDefinitions.PRICE_PER_AREA_UNIT.equals(rwuCriteria.getName())) {
                if (rwuCriteria.getUnit() == AreaMeasurementUnit.SQUARE_METER)
                    return new RangeCriteriaDTO(CENA_PO_M2, CriteriaDTOType.RANGE, rangeValueToString(rwuCriteria.getFrom()), rangeValueToString(rwuCriteria.getTo()));
                else if (rwuCriteria.getUnit() == AreaMeasurementUnit.ARE)
                    return new RangeCriteriaDTO(CENA_PO_ARU, CriteriaDTOType.RANGE, rangeValueToString(rwuCriteria.getFrom()), rangeValueToString(rwuCriteria.getTo()));
            }
        } else if (criteria instanceof RangeCriteria) {
            RangeCriteria rangeCriteria = (RangeCriteria) criteria;
            if (CriteriaDefinitions.PRICE.equals(rangeCriteria.getName()))
                return new RangeCriteriaDTO(CENA, CriteriaDTOType.RANGE, rangeValueToString(rangeCriteria.getFrom()), rangeValueToString(rangeCriteria.getTo()));
            if (CriteriaDefinitions.FLOOR.equals(rangeCriteria.getName()))
                return new RangeCriteriaDTO(SPRATNOST, CriteriaDTOType.RANGE, transformFloor(rangeCriteria.getFrom()), transformFloor(rangeCriteria.getTo()));
        } else if (criteria instanceof MultiValueCriteria) {
            MultiValueCriteria mvCriteria = (MultiValueCriteria) criteria;
            if (CriteriaDefinitions.ADVERTISER.equals(mvCriteria.getName()))
                return new MultiValueCriteriaDTO(OGLASIVAC, CriteriaDTOType.MULTI_SELECT, transformValues(mvCriteria.getValues()));
            if (CriteriaDefinitions.REGISTERED.equals(mvCriteria.getName()))
                return new MultiValueCriteriaDTO(UKNJIZENO, CriteriaDTOType.MULTI_SELECT, transformValues(mvCriteria.getValues()));
            if (CriteriaDefinitions.BUILD_TYPE.equals(mvCriteria.getName()))
                return new MultiValueCriteriaDTO(GRADNJA, CriteriaDTOType.MULTI_SELECT, transformValues(mvCriteria.getValues()));
            if (CriteriaDefinitions.APARTMENT_TYPE.equals(mvCriteria.getName()))
                return new MultiValueCriteriaDTO(TIP_STANA, CriteriaDTOType.MULTI_SELECT, transformValues(mvCriteria.getValues()));
            if (CriteriaDefinitions.FACILITIES.equals(mvCriteria.getName()))
                return new MultiValueCriteriaDTO(DODATNO, CriteriaDTOType.MULTI_SELECT, transformValues(mvCriteria.getValues()));
            if (CriteriaDefinitions.HEATING_TYPE.equals(mvCriteria.getName()))
                return new MultiValueCriteriaDTO(GREJANJE, CriteriaDTOType.MULTI_SELECT, transformValues(mvCriteria.getValues()));
        } else if (criteria instanceof SingleValueCriteria) {
            SingleValueCriteria svCriteria = (SingleValueCriteria) criteria;
            if (CriteriaDefinitions.AD_TYPE.equals(svCriteria.getName()))
                return new SingleValueCriteriaDTO(TIP_OGLASA, CriteriaDTOType.SINGLE_SELECT, transformValue(svCriteria.getValue()));
            if (CriteriaDefinitions.REALTY_TYPE.equals(svCriteria.getName()))
                return new SingleValueCriteriaDTO(TIP_NEKRETNINE, CriteriaDTOType.SINGLE_SELECT, transformValue(svCriteria.getValue()));
        }
        return null;
    }

    private String rangeValueToString(Object o) {
        if (o == null)
            return null;
        else
            return ((Integer) o).toString();
    }

    private String[] transformValues(Object[] values) {
        return Arrays.stream(values).map(criteria2dtoMappings::get).toArray(String[]::new);
    }

    private List<String> transformValues(List<Object> values) {
        if (values == null)
            return Collections.emptyList();
        return values.stream().map(criteria2dtoMappings::get).collect(Collectors.toList());
    }

    private String transformValue(Object value) {
        return value == null ? null : criteria2dtoMappings.get(value);
    }

    private String[] transformFloors(Integer[] values) {
        String[] transformedValues = new String[values.length];
        for (int i=0; i<values.length; i++)
            transformedValues[i] = transformFloor(values[i]);
        return transformedValues;
    }

    private String transformFloor(Integer value) {
        return criteria2dtoMappings.getOrDefault(value, value.toString());
    }

    private String transformFloor(Object o) {
        if (o == null)
            return null;
        else {
            Integer value = (Integer) o;
            return criteria2dtoMappings.getOrDefault(value, value.toString());
        }
    }

    public static void main(String[] args) {
        DTOTransformer dtoTransformer = new DTOTransformer();
        CriteriaDefinitionsDTO dto = dtoTransformer.transformCriteriaDefinitionsToDTO(CriteriaDefinitions.apartmentCriteriaDefinitions);

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
    }
}
