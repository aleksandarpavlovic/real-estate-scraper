package com.paki.scrape.scraper.nekretniners;

import java.util.HashMap;
import java.util.Map;

public class NekretnineRsLocationMapper {
    private static final Map<String, String> cityMappings;
    private static final Map<String, String> locationMappings;

    static {
        cityMappings = new HashMap<>();
        locationMappings = new HashMap<>();

        cityMappings.put("1", "beograd");

        locationMappings.put("1_1", "29-novembra");
        locationMappings.put("1_2", "ada-ciganlija");
        locationMappings.put("1_3", "altina");
        locationMappings.put("1_4", "andricev-venac");
        locationMappings.put("1_5", "bajlonijeva-pijaca");
        locationMappings.put("1_6", "banija");
        locationMappings.put("1_7", "banovo-brdo");
        locationMappings.put("1_8", "batajnica");
        locationMappings.put("1_9", "bele-vode");
        locationMappings.put("1_10", "belvil");
        locationMappings.put("1_11", "beograd-na-vodi");
        locationMappings.put("1_12", "bezanija");
        locationMappings.put("1_13", "bezanijska-kosa-i_bezanijska-kosa-ii_bezanijska-kosa-iii");
        locationMappings.put("1_13_1", "bezanijska-kosa-i");
        locationMappings.put("1_13_1", "bezanijska-kosa-ii");
        locationMappings.put("1_13_1", "bezanijska-kosa-iii");
        locationMappings.put("1_14", "borca_borca-iv_borca-v_borca-iii_borca-ii");
        locationMappings.put("1_15", "centar_terazije_politika_knez-mihajlova_studentski-trg_centar-kod-beogradjanke_centar-kod-trga-republike_centar-kneza-milosa");
        locationMappings.put("1_15_1", "centar-kod-beogradjanke");
        locationMappings.put("1_15_2", "centar");
        locationMappings.put("1_15_3", "knez-mihajlova");
        locationMappings.put("1_15_4", "centar-kneza-milosa");
        locationMappings.put("1_15_5", "politika");
        locationMappings.put("1_15_6", "studentski-trg");
        locationMappings.put("1_15_7", "terazije");
        locationMappings.put("1_15_8", "centar-kod-trga-republike");
        locationMappings.put("1_16", "cerak");
        locationMappings.put("1_17", "cerak-vinogradi");
        locationMappings.put("1_18", "dedinje-25-maj_dedinje-beli-dvor_dedinje-rtv-pink");
        locationMappings.put("1_19", "dorcol_donji-dorcol_strahinjica-bana_dunavski-kej");
        locationMappings.put("1_20", "filmski-grad");
        locationMappings.put("1_21", "galenika");
        locationMappings.put("1_22", "golf-naselje");
        locationMappings.put("1_23", "Grocka");
        locationMappings.put("1_24", "gundulicev-venac");
        locationMappings.put("1_25", "institus-za-majku-i-dete");
        locationMappings.put("1_27", "julino-brdo");
        locationMappings.put("1_28", "kalemegdan");
        locationMappings.put("1_29", "kaluderica");
        locationMappings.put("1_30", "kanarevo-brdo");
        locationMappings.put("1_31", "karadjordjeva");
        locationMappings.put("1_32", "Kijevo");
        locationMappings.put("1_33", "klinicki-centar");
        locationMappings.put("1_34", "knezevac");
        locationMappings.put("1_35", "kopitareva-gradina");
        locationMappings.put("1_36", "kosancicev-venac");
        locationMappings.put("1_37", "kosutnjak");
        locationMappings.put("1_38", "kumodraz-i_kumodraz-ii");
        locationMappings.put("1_39", "labudovo-brdo");
        locationMappings.put("1_40", "ledine");
        locationMappings.put("1_41", "lestane");
        locationMappings.put("1_42", "lisicji-potok");
        locationMappings.put("1_43", "makis");
        locationMappings.put("1_44", "manjez");
        locationMappings.put("1_45", "mackov-kamen");
        locationMappings.put("1_46", "medakovic_medakovic-ii_medakovic-iii_medak-padina");
        locationMappings.put("1_46_1", "medak-padina");
        locationMappings.put("1_46_2", "medakovic");
        locationMappings.put("1_46_3", "medakovic-ii");
        locationMappings.put("1_46_4", "medakovic-iii");
        locationMappings.put("1_47", "miljakovac_miljakovac-ii_miljakovac-iii");
        locationMappings.put("1_48", "mirijevo-i_mirijevo-ii_mirijevo-iii_mirijevoiv");
        locationMappings.put("1_49", "bezanija-blok-60_blok-11a-opstina-nbg_blok-11b_blok-11c-stari-merkator_blok-12-yubc_blok-16-usce-soping-centar_blok-17-staro-sajmiste_blok-19-sava-centar_blok-19a_blok-20-hotel-hayat_blok-21-10-gimnazija_blok-22_blok-23_blok-24-super-vero_blok-25-arena_blok-28-potkovica_blok-29_blok-3_blok-30-b92_blok-31-merkator_blok-33-genex-kula_blok-34-studentski-grad_blok-37_blok-38-os-ratko-mitrovic_blok-39_blok-4-politehnicka-akademija_blok-44-piramida_blok-45-tc-enjub_blok-58_blok-61_blok-62_blok-63_blok-64_blok-67a_blok-7-paviljoni_blok-70-kineski-tc_blok-71_blok-72_blok-7a-paviljoni_blok-8-paviljoni_blok-8a-paviljoni_blok-9a-dom-zdravlja_blok-1-fontana_hotel-jugoslavija-blok-11_blok-67_blok-65_blok-10_blok-70a");
        locationMappings.put("1_49_2", "blok-1-fontana");
        locationMappings.put("1_49_3", "blok-10");
        locationMappings.put("1_49_4", "hotel-jugoslavija-blok-11");
        locationMappings.put("1_49_6", "blok-11a-opstina-nbg");
        locationMappings.put("1_49_7", "blok-11b");
        locationMappings.put("1_49_8", "blok-11c-stari-merkator");
        locationMappings.put("1_49_9", "blok-12-yubc");
        locationMappings.put("1_49_10", "blok-16-usce-soping-centar");
        locationMappings.put("1_49_11", "blok-17-staro-sajmiste");
        locationMappings.put("1_49_12", "blok-19-sava-centar");
        locationMappings.put("1_49_13", "blok-19a");
        locationMappings.put("1_49_14", "blok-20-hotel-hayat");
        locationMappings.put("1_49_15", "blok-21-10-gimnazija");
        locationMappings.put("1_49_16", "blok-22");
        locationMappings.put("1_49_17", "blok-23");
        locationMappings.put("1_49_18", "blok-24-super-vero");
        locationMappings.put("1_49_19", "blok-25-arena");
        locationMappings.put("1_49_20", "blok-28-potkovica");
        locationMappings.put("1_49_21", "blok-29");
        locationMappings.put("1_49_22", "blok-3");
        locationMappings.put("1_49_23", "blok-30-b92");
        locationMappings.put("1_49_24", "blok-31-merkator");
        locationMappings.put("1_49_25", "blok-33-genex-kula");
        locationMappings.put("1_49_26", "blok-34-studentski-grad");
        locationMappings.put("1_49_27", "blok-37");
        locationMappings.put("1_49_28", "blok-38-os-ratko-mitrovic");
        locationMappings.put("1_49_29", "blok-39");
        locationMappings.put("1_49_30", "blok-4-politehnicka-akademija");
        locationMappings.put("1_49_31", "blok-44-piramida");
        locationMappings.put("1_49_32", "blok-45-tc-enjub");
        locationMappings.put("1_49_33", "blok-58");
        locationMappings.put("1_49_34", "bezanija-blok-60");
        locationMappings.put("1_49_35", "blok-61");
        locationMappings.put("1_49_36", "blok-62");
        locationMappings.put("1_49_37", "blok-63");
        locationMappings.put("1_49_38", "blok-64");
        locationMappings.put("1_49_39", "blok-65");
        locationMappings.put("1_49_40", "blok-67");
        locationMappings.put("1_49_41", "blok-67a");
        locationMappings.put("1_49_42", "blok-7-paviljoni_blok-7a-paviljoni_blok-8-paviljoni_blok-8a-paviljoni");
        locationMappings.put("1_49_43", "blok-70-kineski-tc");
        locationMappings.put("1_49_44", "blok-70a");
        locationMappings.put("1_49_45", "blok-71_blok-72");
        locationMappings.put("1_49_50", "blok-9a-dom-zdravlja");
        locationMappings.put("1_49_51", "blok-2");
        locationMappings.put("1_50", "obilicev-venac");
        locationMappings.put("1_51", "palata-pravde");
        locationMappings.put("1_52", "bogoslovija_botanicka-basta_hadzipopovac_karaburma_kotez_krnjaca_ovca_palilula_profesorska-kolonija_tasmajdan_viline-vode_visnjica_visnjicka-banja_cvijiceva_hala-pionir_palilulska-pijaca_postanska-stedionica_rospi-cuprija_padinska-skela");
        locationMappings.put("1_52_1", "bogoslovija");
        locationMappings.put("1_52_2", "botanicka-basta");
        locationMappings.put("1_52_3", "cvijiceva");
        locationMappings.put("1_52_4", "hadzipopovac");
        locationMappings.put("1_52_5", "hala-pionir");
        locationMappings.put("1_52_6", "karaburma");
        locationMappings.put("1_52_7", "kotez");
        locationMappings.put("1_52_8", "krnjaca");
        locationMappings.put("1_52_9", "ovca");
        locationMappings.put("1_52_10", "padinska-skela");
        locationMappings.put("1_52_11", "palilula");
        locationMappings.put("1_52_12", "palilulska-pijaca");
        locationMappings.put("1_52_13", "postanska-stedionica");
        locationMappings.put("1_52_14", "profesorska-kolonija");
        locationMappings.put("1_52_15", "rospi-cuprija");
        locationMappings.put("1_52_16", "tasmajdan");
        locationMappings.put("1_52_17", "viline-vode");
        locationMappings.put("1_52_18", "visnjica");
        locationMappings.put("1_52_19", "visnjicka-banja");
        locationMappings.put("1_53", "partizanov-stadion");
        locationMappings.put("1_54", "petlovo-brdo");
        locationMappings.put("1_55", "pionirski-park");
        locationMappings.put("1_56", "plavi-horizonti");
        locationMappings.put("1_57", "Pregrevica");
        locationMappings.put("1_58", "rakovica");
        locationMappings.put("1_59", "resnik-11869");
        locationMappings.put("1_60", "retenzija");
        locationMappings.put("1_61", "rusanj");
        locationMappings.put("1_62", "sarajevska");
        locationMappings.put("1_63", "sava-mala");
        locationMappings.put("1_64", "savski-trg");
        locationMappings.put("1_65", "savski-venac");
        locationMappings.put("1_66", "senjak");
        locationMappings.put("1_67", "skadarlija");
        locationMappings.put("1_68", "skojevsko-naselje");
        locationMappings.put("1_69", "sremcica");
        locationMappings.put("1_70", "stari-grad");
        locationMappings.put("1_71", "suncana-padina");
        locationMappings.put("1_72", "surcin");
        locationMappings.put("1_73", "tehnicki-fakulteti");
        locationMappings.put("1_74", "toplicin-venac");
        locationMappings.put("1_75", "topcider");
        locationMappings.put("1_76", "tosin-bunar");
        locationMappings.put("1_77", "vidikovac");
        locationMappings.put("1_78", "vidikovacka-padina");
        locationMappings.put("1_79", "vidikovacki-venac");
        locationMappings.put("1_80", "vinca");
        locationMappings.put("1_81", "vojvode-vlahovica");
        locationMappings.put("1_82", "avala_banjica-11732_beli-potok-11735_brace-jerkovic_dusanovac_jajinci_konjarnik_lekino-brdo_marinkova-bara_sumice_torlak_vozdovac_trosarina_autokomanda_farmaceutski-fakultet_veljko-vlahovic_pasino-brdo");
        locationMappings.put("1_82_1", "autokomanda");
        locationMappings.put("1_82_2", "avala");
        locationMappings.put("1_82_3", "banjica-11732");
        locationMappings.put("1_82_4", "beli-potok-11735");
        locationMappings.put("1_82_5", "brace-jerkovic");
        locationMappings.put("1_82_6", "dusanovac");
        locationMappings.put("1_82_7", "farmaceutski-fakultet");
        locationMappings.put("1_82_8", "jajinci");
        locationMappings.put("1_82_9", "konjarnik");
        locationMappings.put("1_82_10", "lekino-brdo");
        locationMappings.put("1_82_11", "marinkova-bara");
        locationMappings.put("1_82_12", "pasino-brdo");
        locationMappings.put("1_82_13", "torlak");
        locationMappings.put("1_82_14", "trosarina");
        locationMappings.put("1_82_15", "veljko-vlahovic");
        locationMappings.put("1_82_16", "vozdovac");
        locationMappings.put("1_82_17", "sumice");
        locationMappings.put("1_83", "crveni-krst_cubura_juzni-bulevar_kalenic-pijaca_neimar_slavija_vracar_cvetni-trg_vracar-hram_gradic-pejton");
        locationMappings.put("1_83_1", "crveni-krst");
        locationMappings.put("1_83_2", "cvetni-trg");
        locationMappings.put("1_83_3", "gradic-pejton");
        locationMappings.put("1_83_4", "juzni-bulevar");
        locationMappings.put("1_83_5", "kalenic-pijaca");
        locationMappings.put("1_83_6", "neimar");
        locationMappings.put("1_83_7", "slavija");
        locationMappings.put("1_83_8", "vracar");
        locationMappings.put("1_83_9", "vracar-hram");
        locationMappings.put("1_83_10", "cubura");
        locationMappings.put("1_84", "zeleni-venac");
        locationMappings.put("1_85", "zemun-centar_zemun-kej_zemun-novi-grad_zemun-save-kovacevica_zemun-kalvarija_zemun-gornji-grad_zemun-gardos_zemun-cukovac_zemun-backa_zemun-cara-dusana_zemun-nova-galenika_zemun-teleoptik_zemun-meandri_zemun-marije-bursac");
        locationMappings.put("1_85_1", "zemun-backa");
        locationMappings.put("1_85_2", "zemun-cara-dusana");
        locationMappings.put("1_85_3", "zemun-centar");
        locationMappings.put("1_85_4", "zemun-gardos");
        locationMappings.put("1_85_5", "zemun-gornji-grad");
        locationMappings.put("1_85_6", "zemun-kalvarija");
        locationMappings.put("1_85_7", "zemun-kej");
        locationMappings.put("1_85_8", "zemun-marije-bursac");
        locationMappings.put("1_85_9", "zemun-meandri");
        locationMappings.put("1_85_10", "zemun-nova-galenika");
        locationMappings.put("1_85_11", "zemun-novi-grad");
        locationMappings.put("1_85_12", "zemun-save-kovacevica");
        locationMappings.put("1_85_13", "zemun-teleoptik");
        locationMappings.put("1_85_14", "zemun-cukovac");
        locationMappings.put("1_86", "zemun-polje");
        locationMappings.put("1_87", "bulbuder_cvetkova-pijaca_denkova-basta_djeram-pijaca_gradska-bolnica_kluz_lion_lipov-lad_mali-mokri-lug_novo-groblje_rudo_uciteljsko-naselje_veliki-mokri-lug_vukov-spomenik_zvezdara_zira_severni-bulevar_zvezdarska-suma_bulevar-kr-aleksandra_slavujev-venac_olimp_cvetanova-cuprija_zeleno-brdo");
        locationMappings.put("1_87_1", "bulbuder");
        locationMappings.put("1_87_2", "bulevar-kr-aleksandra");
        locationMappings.put("1_87_3", "cvetanova-cuprija");
        locationMappings.put("1_87_4", "cvetkova-pijaca");
        locationMappings.put("1_87_5", "denkova-basta");
        locationMappings.put("1_87_6", "gradska-bolnica");
        locationMappings.put("1_87_7", "kluz");
        locationMappings.put("1_87_8", "lion");
        locationMappings.put("1_87_9", "lipov-lad");
        locationMappings.put("1_87_10", "mali-mokri-lug");
        locationMappings.put("1_87_11", "novo-groblje");
        locationMappings.put("1_87_12", "olimp");
        locationMappings.put("1_87_13", "rudo");
        locationMappings.put("1_87_14", "severni-bulevar");
        locationMappings.put("1_87_15", "slavujev-venac");
        locationMappings.put("1_87_16", "uciteljsko-naselje");
        locationMappings.put("1_87_17", "veliki-mokri-lug");
        locationMappings.put("1_87_18", "vukov-spomenik");
        locationMappings.put("1_87_19", "zeleno-brdo");
        locationMappings.put("1_87_20", "zira");
        locationMappings.put("1_87_21", "zvezdara");
        locationMappings.put("1_87_22", "zvezdarska-suma");
        locationMappings.put("1_87_23", "djeram-pijaca");
        locationMappings.put("1_88", "zvezdin-stadion");
        locationMappings.put("1_89", "cukarica");
        locationMappings.put("1_90", "cukaricka-padina");
        locationMappings.put("1_91", "zarkovo");
        locationMappings.put("1_92", "zeleznik");
        locationMappings.put("1_93", "dobanovci");
        locationMappings.put("1_94", "barajevo");

        cityMappings.put("2", "novi-sad");

        locationMappings.put("2_1", "adamovicevo-naselje");
        locationMappings.put("2_2", "adice");
        locationMappings.put("2_3", "avijaticarsko-naselje");
        locationMappings.put("2_4", "banatic");
        locationMappings.put("2_5", "betanija");
        locationMappings.put("2_6", "bistrica-12085");
        locationMappings.put("2_7", "bulevar");
        locationMappings.put("2_8", "cara-dusana");
        locationMappings.put("2_9", "centar-12420");
        locationMappings.put("2_10", "detelinara");
        locationMappings.put("2_11", "detelinara-nova");
        locationMappings.put("2_12", "Dumbovo");
        locationMappings.put("2_13", "futog");
        locationMappings.put("2_14", "futoska-pijaca");
        locationMappings.put("2_15", "futoski-put");
        locationMappings.put("2_16", "gornje-livade");
        locationMappings.put("2_17", "grbavica");
        locationMappings.put("2_18", "gsp");
        locationMappings.put("2_19", "idustrijska-zona-jug");
        locationMappings.put("2_20", "jodna-banja");
        locationMappings.put("2_21", "jugovicevo");
        locationMappings.put("2_22", "kamenicko-ostrvo");
        locationMappings.put("2_23", "kamenjar");
        locationMappings.put("2_24", "karagaca");
        locationMappings.put("2_25", "klisa");
        locationMappings.put("2_26", "Ledinci");
        locationMappings.put("2_27", "liman-1");
        locationMappings.put("2_28", "liman-2");
        locationMappings.put("2_29", "liman-3");
        locationMappings.put("2_30", "liman-4");
        locationMappings.put("2_31", "lipov-gaj");
        locationMappings.put("2_32", "miseluk-12643");
        locationMappings.put("2_33", "novi-majur-12421");
        locationMappings.put("2_34", "novo-naselje");
        locationMappings.put("2_35", "Petrovaradin");
        locationMappings.put("2_36", "podbara");
        locationMappings.put("2_37", "ribljja-pijaca");
        locationMappings.put("2_38", "ribnjak-12109");
        locationMappings.put("2_39", "rotkvarija");
        locationMappings.put("2_40", "sajam_sajmiste");
        locationMappings.put("2_41", "sajlovo");
        locationMappings.put("2_43", "salajka");
        locationMappings.put("2_44", "satelit");
        locationMappings.put("2_45", "savina");
        locationMappings.put("2_46", "slana-bara");
        locationMappings.put("2_47", "socijalno");
        locationMappings.put("2_48", "somborski-bulevar");
        locationMappings.put("2_49", "sonsi");
        locationMappings.put("2_50", "spens");
        locationMappings.put("2_51", "stari-majur-12422");
        locationMappings.put("2_52", "suboticki-bulevar");
        locationMappings.put("2_53", "telep");
        locationMappings.put("2_54", "veliki-rit");
        locationMappings.put("2_55", "Veternik");
        locationMappings.put("2_56", "vidovdansko-naselje");
        locationMappings.put("2_57", "saren-grad");
        locationMappings.put("2_58", "zeleznicka-stanica");

        cityMappings.put("3", "kragujevac");

        locationMappings.put("3_1", "aerodrom");
        locationMappings.put("3_2", "bagremar");
        locationMappings.put("3_3", "bubanj");
        locationMappings.put("3_4", "centar-12429");
        locationMappings.put("3_5", "centar-preko-lepenice");
        locationMappings.put("3_6", "erdoglija");
        locationMappings.put("3_7", "mala-vaga");
        locationMappings.put("3_8", "ozon");
        locationMappings.put("3_9", "palilule");
        locationMappings.put("3_10", "stanovo");
        locationMappings.put("3_11", "stara-kolonija");
        locationMappings.put("3_12", "stara-varos");
        locationMappings.put("3_13", "susice");
        locationMappings.put("3_14", "vasariste");
        locationMappings.put("3_15", "veliki-park");
        locationMappings.put("3_16", "zvezda");

        cityMappings.put("4", "kraljevo");

        locationMappings.put("4_1", "centar-12441");
        locationMappings.put("4_2", "dositejeva-ulica");
        locationMappings.put("4_3", "stara-carsija");
        locationMappings.put("4_4", "vojno-naselje");
        locationMappings.put("4_5", "zelengora");

        cityMappings.put("5", "krusevac");

        locationMappings.put("5_1", "bagdala");
        locationMappings.put("5_2", "blagoja-parovica");
        locationMappings.put("5_3", "bronks");
        locationMappings.put("5_4", "centar-12430");
        locationMappings.put("5_5", "dragomir-gajic");
        locationMappings.put("5_6", "pejton");
        locationMappings.put("5_7", "rasadnik");
        locationMappings.put("5_8", "sup");
        locationMappings.put("5_9", "trg-kosturnica");
        locationMappings.put("5_10", "ujedinjene-nacije");
        locationMappings.put("5_11", "vlado-juric");

        cityMappings.put("6", "nis");

        locationMappings.put("6_1", "apelovac");
        locationMappings.put("6_2", "beverli-hils");
        locationMappings.put("6_3", "bulevar-nemanjica");
        locationMappings.put("6_4", "bulevar-zona");
        locationMappings.put("6_5", "centar-12416");
        locationMappings.put("6_6", "crvena-zvezda");
        locationMappings.put("6_7", "crveni-krst-12027");
        locationMappings.put("6_8", "crveni-pevac");
        locationMappings.put("6_9", "delijski-vis");
        locationMappings.put("6_10", "durlan");
        locationMappings.put("6_11", "duvaniste");
        locationMappings.put("6_12", "gabrovacka-reka");
        locationMappings.put("6_13", "jagodin-mala");
        locationMappings.put("6_14", "kalac-brdo");
        locationMappings.put("6_15", "kicevo");
        locationMappings.put("6_16", "komren");
        locationMappings.put("6_17", "kovanluk");
        locationMappings.put("6_18", "krive-livade");
        locationMappings.put("6_19", "ledena-stena");
        locationMappings.put("6_20", "marger");
        locationMappings.put("6_21", "medijana_mediana");
        locationMappings.put("6_22", "mika-protic-kp-dom");
        locationMappings.put("6_23", "nova-zeleznicka-kolonija");
        locationMappings.put("6_24", "palilula-12051");
        locationMappings.put("6_25", "pantelej-12053");
        locationMappings.put("6_26", "ratko-jovic");
        locationMappings.put("6_27", "staro-groblje");
        locationMappings.put("6_28", "tehnicki-fakultet");
        locationMappings.put("6_29", "trosarina-12418");
        locationMappings.put("6_30", "vrezina");
        locationMappings.put("6_31", "calije");
        locationMappings.put("6_32", "cele-kula");
        locationMappings.put("6_33", "cair");

        cityMappings.put("7", "pancevo");

        locationMappings.put("7_1", "centar-12433");
        locationMappings.put("7_2", "gornji-grad-margita");
        locationMappings.put("7_3", "kotez-1");
        locationMappings.put("7_4", "kotez-2");
        locationMappings.put("7_5", "kudeljarski-nasip");
        locationMappings.put("7_6", "nova-misa_nova-misa-vinogradi");
        locationMappings.put("7_8", "novi-svet");
        locationMappings.put("7_9", "sodara");
        locationMappings.put("7_10", "streliste-1_streliste-2");
        locationMappings.put("7_12", "tesla");
        locationMappings.put("7_13", "tip-stanko");
        locationMappings.put("7_14", "vojlovica");

        cityMappings.put("8", "aleksinac");
        cityMappings.put("9", "apatin");
        cityMappings.put("10", "arandjelovac");
        cityMappings.put("11", "arilje");
        cityMappings.put("12", "backa-palanka");
        cityMappings.put("13", "backa-topola");
        cityMappings.put("14", "backi-petrovac");
        cityMappings.put("15", "bela-crkva-");
        cityMappings.put("16", "beocin");
        cityMappings.put("17", "becej");
        cityMappings.put("18", "bor-");
        cityMappings.put("19", "divcibare");
        cityMappings.put("20", "indjija");
        cityMappings.put("21", "ivanjica");
        cityMappings.put("22", "jagodina");
        cityMappings.put("23", "kikinda");
        cityMappings.put("24", "kladovo");
        cityMappings.put("25", "kopaonik");
        cityMappings.put("26", "kosovska-mitrovica");
        cityMappings.put("27", "kovin");
        cityMappings.put("28", "lazarevac--_lazarevac_lazarevac-");
        cityMappings.put("29", "leskovac-4948");
        cityMappings.put("30", "loznica-5386");
        cityMappings.put("31", "majdanpek");
        cityMappings.put("32", "mali-zvornik");
        cityMappings.put("33", "negotin");
        cityMappings.put("34", "nova-pazova");
        cityMappings.put("35", "novi-pazar");
        cityMappings.put("36", "obrenovac-");
        cityMappings.put("37", "paracin");
        cityMappings.put("38", "petrovac-na-mlavi");
        cityMappings.put("39", "pozarevac");
        cityMappings.put("40", "priboj-");
        cityMappings.put("41", "prijepolje");
        cityMappings.put("42", "pristina");
        cityMappings.put("43", "prokuplje");
        cityMappings.put("44", "raska");
        cityMappings.put("45", "ruma");
        cityMappings.put("46", "senta");
        cityMappings.put("47", "smederevo");
        cityMappings.put("48", "sokobanja");
        cityMappings.put("49", "sombor");
        cityMappings.put("50", "sremska-kamenica");
        cityMappings.put("51", "sremska-mitrovica");
        cityMappings.put("52", "sremski-karlovci");
        cityMappings.put("53", "stara-pazova");
        cityMappings.put("54", "subotica-8239_subotica-9187");
        cityMappings.put("55", "temerin");
        cityMappings.put("56", "trstenik-8750");
        cityMappings.put("57", "ub");
        cityMappings.put("58", "uzice");
        cityMappings.put("59", "valjevo");
        cityMappings.put("60", "velika-plana-");
        cityMappings.put("61", "veliko-gradiste");
        cityMappings.put("62", "vinca-");
        cityMappings.put("63", "vranje");
        cityMappings.put("64", "vrbas");
        cityMappings.put("65", "vrnjacka-banja");
        cityMappings.put("66", "vrsac");
        cityMappings.put("67", "zajecar");
        cityMappings.put("68", "zlatibor");
        cityMappings.put("69", "zrenjanin");
        cityMappings.put("70", "cuprija");
        cityMappings.put("71", "cajetina");
        cityMappings.put("72", "cacak");
        cityMappings.put("73", "sabac");
        cityMappings.put("74", "sid");
        cityMappings.put("75", "zabalj");
        cityMappings.put("76", "zitiste");
    }

    public static String getCity(String id) {
        return cityMappings.get(id);
    }

    public static String getLocation(String id) {
        return locationMappings.get(id);
    }
}