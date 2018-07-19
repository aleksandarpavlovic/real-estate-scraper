package scrape.criteria;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CriteriaDefinitions {

    public static final String TIP_OGLASA = "tip oglasa";
    public static final String PRODAJA = "prodaja";
    public static final String IZDAVANJE = "izdavanje";

    public static final String OGLASIVAC = "oglasivac";
    public static final String AGENCIJA = "agencija";
    public static final String VLASNIK = "vlasnik";
    public static final String INVESTITOR = "investitor";

    public static final String TIP_NEKRETNINE = "tip nekretnine";
    public static final String STAN = "stan";
    public static final String KUCA = "kuca";
    public static final String SOBA = "soba";
    public static final String PLAC = "plac";

    public static final String GRADNJA = "gradnja";
    public static final String STAROGRADNJA = "starogradnja";
    public static final String NOVOGRADNJA = "novogradnja";
    public static final String IZGRADNJA = "u izgradnji";

    // TODO vidi sta ces sa stanjem stana. ta kategorija ne postoji na nekretnine.rs

    public static final String GREJANJE = "grejanje";
    public static final String CENTRALNO = "centralno";
    public static final String ETAZNO = "etazno";
    public static final String STRUJA = "struja";
    public static final String GAS = "gas";

    public static final String UKNJIZENO = "registered";

    public static final String TIP_STANA = "tip stana";
    public static final String SALONAC = "salonac";
    public static final String DUPLEX = "duplex";
    public static final String PENTHOUSE = "penthouse";
    public static final String POTKROVLJE = "potkrovlje";
    public static final String DVORISNI = "dvorisni";

    public static final String PRATECI_OBJEKTI = "prateci objekti";
    public static final String TERASA = "terasa";
    public static final String LODJA = "lodja";
    public static final String BALKON = "balkon";
    public static final String FRANCUSKI_BALKON = "francuski balkon";
    public static final String GARAZA = "garaza";
    public static final String PARKING = "parking";

    public static final String BROJ_SOBA = "broj soba";
    public static final String BROJ_SOBA_0 = "0";
    public static final String BROJ_SOBA_0_5 = "0.5";
    public static final String BROJ_SOBA_1_0 = "1.0";
    public static final String BROJ_SOBA_1_5 = "1.5";
    public static final String BROJ_SOBA_2_0 = "2.0";
    public static final String BROJ_SOBA_2_5 = "2.5";
    public static final String BROJ_SOBA_3_0 = "3.0";
    public static final String BROJ_SOBA_3_5 = "3.5";
    public static final String BROJ_SOBA_4_0 = "4.0";
    public static final String BROJ_SOBA_4_5 = "4.5";
    public static final String BROJ_SOBA_5_0 = "5.0";
    public static final String BROJ_SOBA_5_p = "5+";

    public static final String CENA = "cena";

    public static final String KVADRATURA = "kvadratura";

    public static final String SPRATNOST = "spratnost";
//    public static final String SUTEREN = "suteren";
//    public static final String NISKO_PRIZEMLJE = "nisko prizemlje";
//    public static final String PRIZEMLJE = "prizemlje";
//    public static final String VISOKO_PRIZEMLJE = "visoko prizemlje";
    public static final Integer SUTEREN = -4;
    public static final Integer NISKO_PRIZEMLJE = -3;
    public static final Integer PRIZEMLJE = -2;
    public static final Integer VISOKO_PRIZEMLJE = -1;

    public static final CriteriaDefinition adCategory;
    public static final CriteriaDefinition advertiser;
    public static final CriteriaDefinition realtyType;
    public static final CriteriaDefinition realtyBuild;
    public static final CriteriaDefinition heating;
    public static final CriteriaDefinition appartmentType;
    public static final CriteriaDefinition facilities;
    public static final CriteriaDefinition roomCount;
    public static final CriteriaDefinition price;
    public static final CriteriaDefinition surfaceArea;
    public static final CriteriaDefinition floor;

    static {
        adCategory = new CriteriaDefinition(TIP_OGLASA, PRODAJA, IZDAVANJE);
        advertiser = new CriteriaDefinition(OGLASIVAC, AGENCIJA, VLASNIK, INVESTITOR);
        realtyType = new CriteriaDefinition(TIP_NEKRETNINE, STAN, KUCA, SOBA, PLAC);
        realtyBuild = new CriteriaDefinition(GRADNJA, STAROGRADNJA, NOVOGRADNJA, IZGRADNJA);
        heating = new CriteriaDefinition(GREJANJE, CENTRALNO, ETAZNO, STRUJA, GAS);
        appartmentType = new CriteriaDefinition(TIP_STANA, SALONAC, DUPLEX, PENTHOUSE, POTKROVLJE, DVORISNI);
        facilities = new CriteriaDefinition(PRATECI_OBJEKTI, TERASA, LODJA, BALKON, FRANCUSKI_BALKON, GARAZA, PARKING);
        roomCount = new CriteriaDefinition(BROJ_SOBA, BROJ_SOBA_0, BROJ_SOBA_0_5, BROJ_SOBA_1_0, BROJ_SOBA_1_5, BROJ_SOBA_2_0, BROJ_SOBA_2_5, BROJ_SOBA_3_0, BROJ_SOBA_3_5, BROJ_SOBA_4_0, BROJ_SOBA_4_5, BROJ_SOBA_5_0, BROJ_SOBA_5_p);
        price = new CriteriaDefinition(CENA);
        surfaceArea = new CriteriaDefinition(KVADRATURA);
        floor = new CriteriaDefinition(SPRATNOST);
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CriteriaDefinition {
        String name;
        String[] values;

        private CriteriaDefinition(String name, String... values) {
            this.name = name;
            this.values = values;
        }
    }
}
