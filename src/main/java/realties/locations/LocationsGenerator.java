package realties.locations;

import java.util.LinkedList;
import java.util.List;

public class LocationsGenerator {

    private static final List<Location> locations;

    static {
        locations = generate();
    }

    private static List<Location> generate() {
        List<Location> locations = new LinkedList<>();

        LocationCreator locationCreator = new LocationCreator();
        Location beograd = locationCreator.create("1", "Beograd");
        beograd.addSublocation(locationCreator.create("1_1","29. Novembra"));
        beograd.addSublocation(locationCreator.create("1_2", "Ada Ciganlija"));
        beograd.addSublocation(locationCreator.create("1_3", "Altina"));
        beograd.addSublocation(locationCreator.create("1_4", "Andrićev venac"));
        beograd.addSublocation(locationCreator.create("1_5", "Bajlonijeva pijaca"));
        beograd.addSublocation(locationCreator.create("1_6", "Banija"));
        beograd.addSublocation(locationCreator.create("1_7", "Banovo brdo"));
        beograd.addSublocation(locationCreator.create("1_8", "Batajnica"));
        beograd.addSublocation(locationCreator.create("1_9", "Bele vode"));
        beograd.addSublocation(locationCreator.create("1_10", "Belvil"));
        beograd.addSublocation(locationCreator.create("1_11", "Beograd na vodi"));
        beograd.addSublocation(locationCreator.create("1_12", "Bežanija"));

        Location bezanijskaKosa = locationCreator.create("1_13", "Bežanijska kosa");
        bezanijskaKosa.addSublocation(locationCreator.create("1_13_1", "Bežanijska kosa I"));
        bezanijskaKosa.addSublocation(locationCreator.create("1_13_2", "Bežanijska kosa II"));
        bezanijskaKosa.addSublocation(locationCreator.create("1_13_3", "Bežanijska kosa III"));
        beograd.addSublocation(bezanijskaKosa);

        Location borca = locationCreator.create("1_14", "Borča");
        bezanijskaKosa.addSublocation(locationCreator.create("1_14_1", "Borča I "));
        bezanijskaKosa.addSublocation(locationCreator.create("1_14_2", "Borča II "));
        bezanijskaKosa.addSublocation(locationCreator.create("1_14_3", "Borča III"));
        bezanijskaKosa.addSublocation(locationCreator.create("1_14_4", "Borča IV"));
        bezanijskaKosa.addSublocation(locationCreator.create("1_14_5", "Borča V"));
        beograd.addSublocation(borca);

        Location bgCentar = locationCreator.create("1_15", "Centar");
        bgCentar.addSublocation(locationCreator.create("1_15_1", "Beograđanka"));
        bgCentar.addSublocation(locationCreator.create("1_15_2", "Centar (uži)"));
        bgCentar.addSublocation(locationCreator.create("1_15_3", "Knez Mihajlova"));
        bgCentar.addSublocation(locationCreator.create("1_15_4", "Kneza Miloša"));
        bgCentar.addSublocation(locationCreator.create("1_15_5", "Politika"));
        bgCentar.addSublocation(locationCreator.create("1_15_6", "Studentski Trg"));
        bgCentar.addSublocation(locationCreator.create("1_15_7", "Terazije"));
        bgCentar.addSublocation(locationCreator.create("1_15_8", "Trg Republike"));
        beograd.addSublocation(bgCentar);

        beograd.addSublocation(locationCreator.create("1_16", "Cerak"));
        beograd.addSublocation(locationCreator.create("1_17", "Cerak vinogradi"));

        Location dedinje = locationCreator.create("1_18", "Dedinje");
        dedinje.addSublocation(locationCreator.create("1_18_1", "Dedinje (25. maj)"));
        dedinje.addSublocation(locationCreator.create("1_18_2", "Dedinje (Beli dvor)"));
        dedinje.addSublocation(locationCreator.create("1_18_3", "Dedinje (RTV Pink)"));
        beograd.addSublocation(dedinje);

        Location dorcol = locationCreator.create("1_19", "Dorćol");
        dorcol.addSublocation(locationCreator.create("1_19_1", "Donji Dorćol"));
        dorcol.addSublocation(locationCreator.create("1_19_2", "Dunavski kej"));
        dorcol.addSublocation(locationCreator.create("1_19_3", "Gornji Dorćol"));
        dorcol.addSublocation(locationCreator.create("1_19_4", "Strahinjića Bana"));
        beograd.addSublocation(dorcol);

        beograd.addSublocation(locationCreator.create("1_20", "Filmski grad"));
        beograd.addSublocation(locationCreator.create("1_21", "Galenika"));
        beograd.addSublocation(locationCreator.create("1_22", "Golf naselje"));
        beograd.addSublocation(locationCreator.create("1_23", "Grocka"));
        beograd.addSublocation(locationCreator.create("1_24", "Gundulićev venac"));
        beograd.addSublocation(locationCreator.create("1_25", "Institut za majku i dete"));
        beograd.addSublocation(locationCreator.create("1_26", "Jabučki rit"));
        beograd.addSublocation(locationCreator.create("1_27", "Julino brdo"));
        beograd.addSublocation(locationCreator.create("1_28", "Kalemegdan"));
        beograd.addSublocation(locationCreator.create("1_29", "Kaluđerica"));
        beograd.addSublocation(locationCreator.create("1_30", "Kanarevo brdo"));
        beograd.addSublocation(locationCreator.create("1_31", "Karađorđeva"));
        beograd.addSublocation(locationCreator.create("1_32", "Kijevo"));
        beograd.addSublocation(locationCreator.create("1_33", "Klinički centar"));
        beograd.addSublocation(locationCreator.create("1_34", "Kneževac"));
        beograd.addSublocation(locationCreator.create("1_35", "Kopitareva gradina "));
        beograd.addSublocation(locationCreator.create("1_36", "Kosančićev venac"));
        beograd.addSublocation(locationCreator.create("1_37", "Košutnjak"));

        Location kumodraz = locationCreator.create("1_38", "Kumodraž");
        kumodraz.addSublocation(locationCreator.create("1_38_1", "Kumodraž I"));
        kumodraz.addSublocation(locationCreator.create("1_38_2", "Kumodraž II"));
        beograd.addSublocation(kumodraz);

        beograd.addSublocation(locationCreator.create("1_39", "Labudovo brdo"));
        beograd.addSublocation(locationCreator.create("1_40", "Ledine"));
        beograd.addSublocation(locationCreator.create("1_41", "Leštane"));
        beograd.addSublocation(locationCreator.create("1_42", "Lisičji potok"));
        beograd.addSublocation(locationCreator.create("1_43", "Makiš"));
        beograd.addSublocation(locationCreator.create("1_44", "Manjež"));
        beograd.addSublocation(locationCreator.create("1_45", "Mačkov kamen"));

        Location medakovic = locationCreator.create("1_46", "Medaković");
        medakovic.addSublocation(locationCreator.create("1_46_1", "Medak padina"));
        medakovic.addSublocation(locationCreator.create("1_46_2", "Medaković I"));
        medakovic.addSublocation(locationCreator.create("1_46_3", "Medaković II"));
        medakovic.addSublocation(locationCreator.create("1_46_4", "Medaković III"));
        beograd.addSublocation(medakovic);

        Location miljakovac = locationCreator.create("1_47", "Miljakovac");
        miljakovac.addSublocation(locationCreator.create("1_47_1", "Miljakovac I"));
        miljakovac.addSublocation(locationCreator.create("1_47_2", "Miljakovac II"));
        miljakovac.addSublocation(locationCreator.create("1_47_3", "Miljakovac III"));
        beograd.addSublocation(miljakovac);

        Location mirijevo = locationCreator.create("1_48", "Mirijevo");
        mirijevo.addSublocation(locationCreator.create("1_48_1", "Mirijevo I"));
        mirijevo.addSublocation(locationCreator.create("1_48_2", "Mirijevo II"));
        mirijevo.addSublocation(locationCreator.create("1_48_3", "Mirijevo III"));
        mirijevo.addSublocation(locationCreator.create("1_48_4", "Mirijevo IV"));
        beograd.addSublocation(mirijevo);

        Location nbg = locationCreator.create("1_49", "Novi Beograd");
        nbg.addSublocation(locationCreator.create("1_49_2", "Blok 1 (Fontana)"));
        nbg.addSublocation(locationCreator.create("1_49_3", "Blok 10"));
        nbg.addSublocation(locationCreator.create("1_49_4", "Blok 11 (Hotel Jugoslavija)"));
        nbg.addSublocation(locationCreator.create("1_49_6", "Blok 11a (Opština NBG)"));
        nbg.addSublocation(locationCreator.create("1_49_7", "Blok 11b"));
        nbg.addSublocation(locationCreator.create("1_49_8", "Blok 11c (Stari Merkator)"));
        nbg.addSublocation(locationCreator.create("1_49_9", "Blok 12 (YUBC)"));
        nbg.addSublocation(locationCreator.create("1_49_10", "Blok 16 (Ušće šoping centar)"));
        nbg.addSublocation(locationCreator.create("1_49_11", "Blok 17 (Staro sajmište)"));
        nbg.addSublocation(locationCreator.create("1_49_12", "Blok 19 (Sava Centar)"));
        nbg.addSublocation(locationCreator.create("1_49_13", "Blok 19a"));
        nbg.addSublocation(locationCreator.create("1_49_14", "Blok 20 (Hotel Hayat)"));
        nbg.addSublocation(locationCreator.create("1_49_15", "Blok 21 (10. Gimnazija)"));
        nbg.addSublocation(locationCreator.create("1_49_16", "Blok 22"));
        nbg.addSublocation(locationCreator.create("1_49_17", "Blok 23"));
        nbg.addSublocation(locationCreator.create("1_49_18", "Blok 24 (Super Vero)"));
        nbg.addSublocation(locationCreator.create("1_49_19", "Blok 25 (Arena)"));
        nbg.addSublocation(locationCreator.create("1_49_20", "Blok 28 (Potkovica)"));
        nbg.addSublocation(locationCreator.create("1_49_21", "Blok 29"));
        nbg.addSublocation(locationCreator.create("1_49_22", "Blok 3"));
        nbg.addSublocation(locationCreator.create("1_49_23", "Blok 30 (B92)"));
        nbg.addSublocation(locationCreator.create("1_49_24", "Blok 31 (Merkator)"));
        nbg.addSublocation(locationCreator.create("1_49_25", "Blok 33 (Genex kula)"));
        nbg.addSublocation(locationCreator.create("1_49_26", "Blok 34 (Studentski grad)"));
        nbg.addSublocation(locationCreator.create("1_49_27", "Blok 37"));
        nbg.addSublocation(locationCreator.create("1_49_28", "Blok 38 (OŠ Ratko Mitrović)"));
        nbg.addSublocation(locationCreator.create("1_49_29", "Blok 39"));
        nbg.addSublocation(locationCreator.create("1_49_30", "Blok 4 (Politehnička akademija)"));
        nbg.addSublocation(locationCreator.create("1_49_31", "Blok 44 (Piramida)"));
        nbg.addSublocation(locationCreator.create("1_49_32", "Blok 45 (TC Enjub)"));
        nbg.addSublocation(locationCreator.create("1_49_33", "Blok 58"));
        nbg.addSublocation(locationCreator.create("1_49_34", "Blok 60"));
        nbg.addSublocation(locationCreator.create("1_49_35", "Blok 61"));
        nbg.addSublocation(locationCreator.create("1_49_36", "Blok 62"));
        nbg.addSublocation(locationCreator.create("1_49_37", "Blok 63"));
        nbg.addSublocation(locationCreator.create("1_49_38", "Blok 64"));
        nbg.addSublocation(locationCreator.create("1_49_39", "Blok 65"));
        nbg.addSublocation(locationCreator.create("1_49_40", "Blok 67"));
        nbg.addSublocation(locationCreator.create("1_49_41", "Blok 67a"));
        nbg.addSublocation(locationCreator.create("1_49_42", "Blok 7 (Paviljoni)"));
        nbg.addSublocation(locationCreator.create("1_49_43", "Blok 70 (Kineski TC)"));
        nbg.addSublocation(locationCreator.create("1_49_44", "Blok 70a"));
        nbg.addSublocation(locationCreator.create("1_49_45", "Blok 71"));
        nbg.addSublocation(locationCreator.create("1_49_46", "Blok 72"));
        nbg.addSublocation(locationCreator.create("1_49_47", "Blok 7a (Paviljoni)"));
        nbg.addSublocation(locationCreator.create("1_49_48", "Blok 8 (Paviljoni)"));
        nbg.addSublocation(locationCreator.create("1_49_49", "Blok 8a (Paviljoni)"));
        nbg.addSublocation(locationCreator.create("1_49_50", "Blok 9a (Dom Zdravlja)"));
        nbg.addSublocation(locationCreator.create("1_49_51", "Blok 2"));
        beograd.addSublocation(nbg);

        beograd.addSublocation(locationCreator.create("1_50", "Obilićev venac "));
        beograd.addSublocation(locationCreator.create("1_51", "Palata pravde"));

        Location palilula = locationCreator.create("1_52", "Palilula");
        palilula.addSublocation(locationCreator.create("1_52_1", "Bogoslovija"));
        palilula.addSublocation(locationCreator.create("1_52_2", "Botanička bašta"));
        palilula.addSublocation(locationCreator.create("1_52_3", "Cvijićeva"));
        palilula.addSublocation(locationCreator.create("1_52_4", "Hadžipopovac"));
        palilula.addSublocation(locationCreator.create("1_52_5", "Hala Pionir"));
        palilula.addSublocation(locationCreator.create("1_52_6", "Karaburma"));
        palilula.addSublocation(locationCreator.create("1_52_7", "Kotež"));
        palilula.addSublocation(locationCreator.create("1_52_8", "Krnjača"));
        palilula.addSublocation(locationCreator.create("1_52_9", "Ovča"));
        palilula.addSublocation(locationCreator.create("1_52_10", "Padinska skela"));
        palilula.addSublocation(locationCreator.create("1_52_11", "Palilula (centar)"));
        palilula.addSublocation(locationCreator.create("1_52_12", "Palilulska pijaca"));
        palilula.addSublocation(locationCreator.create("1_52_13", "Poštanska štedionica"));
        palilula.addSublocation(locationCreator.create("1_52_14", "Profesorska kolonija"));
        palilula.addSublocation(locationCreator.create("1_52_15", "Rospi ćuprija"));
        palilula.addSublocation(locationCreator.create("1_52_16", "Tašmajdan"));
        palilula.addSublocation(locationCreator.create("1_52_17", "Viline vode"));
        palilula.addSublocation(locationCreator.create("1_52_18", "Višnjica"));
        palilula.addSublocation(locationCreator.create("1_52_19", "Višnjička banja"));
        beograd.addSublocation(palilula);

        beograd.addSublocation(locationCreator.create("1_53", "Partizanov stadion"));
        beograd.addSublocation(locationCreator.create("1_54", "Petlovo brdo"));
        beograd.addSublocation(locationCreator.create("1_55", "Pionirski park"));
        beograd.addSublocation(locationCreator.create("1_56", "Plavi Horizonti "));
        beograd.addSublocation(locationCreator.create("1_57", "Pregrevica"));
        beograd.addSublocation(locationCreator.create("1_58", "Rakovica"));
        beograd.addSublocation(locationCreator.create("1_59", "Resnik"));
        beograd.addSublocation(locationCreator.create("1_60", "Retenzija"));
        beograd.addSublocation(locationCreator.create("1_61", "Rušanj"));
        beograd.addSublocation(locationCreator.create("1_62", "Sarajevska"));
        beograd.addSublocation(locationCreator.create("1_63", "Sava mala"));
        beograd.addSublocation(locationCreator.create("1_64", "Savski trg"));
        beograd.addSublocation(locationCreator.create("1_65", "Savski venac"));
        beograd.addSublocation(locationCreator.create("1_66", "Senjak"));
        beograd.addSublocation(locationCreator.create("1_67", "Skadarlija"));
        beograd.addSublocation(locationCreator.create("1_68", "Skojevsko naselje"));
        beograd.addSublocation(locationCreator.create("1_69", "Sremčica"));
        beograd.addSublocation(locationCreator.create("1_70", "Stari Grad"));
        beograd.addSublocation(locationCreator.create("1_71", "Sunčana padina"));
        beograd.addSublocation(locationCreator.create("1_72", "Surčin"));
        beograd.addSublocation(locationCreator.create("1_73", "Tehnicki fakulteti"));
        beograd.addSublocation(locationCreator.create("1_74", "Topličin venac"));
        beograd.addSublocation(locationCreator.create("1_75", "Topčider"));
        beograd.addSublocation(locationCreator.create("1_76", "Tošin bunar"));
        beograd.addSublocation(locationCreator.create("1_77", "Vidikovac"));
        beograd.addSublocation(locationCreator.create("1_78", "Vidikovačka padina"));
        beograd.addSublocation(locationCreator.create("1_79", "Vidikovački venac"));
        beograd.addSublocation(locationCreator.create("1_80", "Vinča"));
        beograd.addSublocation(locationCreator.create("1_81", "Vojvode Vlahovića"));

        Location vozdovac = locationCreator.create("1_82", "Voždovac");
        vozdovac.addSublocation(locationCreator.create("1_82_1", "Autokomanda"));
        vozdovac.addSublocation(locationCreator.create("1_82_2", "Avala"));
        vozdovac.addSublocation(locationCreator.create("1_82_3", "Banjica"));
        vozdovac.addSublocation(locationCreator.create("1_82_4", "Beli potok"));
        vozdovac.addSublocation(locationCreator.create("1_82_5", "Braće Jerković"));
        vozdovac.addSublocation(locationCreator.create("1_82_6", "Dušanovac"));
        vozdovac.addSublocation(locationCreator.create("1_82_7", "Farmaceutski fakultet"));
        vozdovac.addSublocation(locationCreator.create("1_82_8", "Jajinci"));
        vozdovac.addSublocation(locationCreator.create("1_82_9", "Konjarnik"));
        vozdovac.addSublocation(locationCreator.create("1_82_10", "Lekino brdo"));
        vozdovac.addSublocation(locationCreator.create("1_82_11", "Marinkova bara"));
        vozdovac.addSublocation(locationCreator.create("1_82_12", "Pašino brdo"));
        vozdovac.addSublocation(locationCreator.create("1_82_13", "Torlak"));
        vozdovac.addSublocation(locationCreator.create("1_82_14", "Trošarina"));
        vozdovac.addSublocation(locationCreator.create("1_82_15", "Veljko Vlahović"));
        vozdovac.addSublocation(locationCreator.create("1_82_16", "Voždovac (centar)"));
        vozdovac.addSublocation(locationCreator.create("1_82_17", "Šumice"));
        beograd.addSublocation(vozdovac);

        Location vracar = locationCreator.create("1_83", "Vračar");
        vracar.addSublocation(locationCreator.create("1_83_1", "Crveni krst"));
        vracar.addSublocation(locationCreator.create("1_83_2", "Cvetni trg"));
        vracar.addSublocation(locationCreator.create("1_83_3", "Gradic Pejton"));
        vracar.addSublocation(locationCreator.create("1_83_4", "Južni bulevar"));
        vracar.addSublocation(locationCreator.create("1_83_5", "Kalenić pijaca"));
        vracar.addSublocation(locationCreator.create("1_83_6", "Neimar"));
        vracar.addSublocation(locationCreator.create("1_83_7", "Slavija"));
        vracar.addSublocation(locationCreator.create("1_83_8", "Vračar (Centar)"));
        vracar.addSublocation(locationCreator.create("1_83_9", "Vračar (Hram)"));
        vracar.addSublocation(locationCreator.create("1_83_10", "Čubura"));
        beograd.addSublocation(vracar);

        beograd.addSublocation(locationCreator.create("1_84", "Zeleni venac"));

        Location zemun = locationCreator.create("1_85", "Zemun");
        zemun.addSublocation(locationCreator.create("1_85_1", "Bačka"));
        zemun.addSublocation(locationCreator.create("1_85_2", "Cara Dušana"));
        zemun.addSublocation(locationCreator.create("1_85_3", "Centar"));
        zemun.addSublocation(locationCreator.create("1_85_4", "Gardoš"));
        zemun.addSublocation(locationCreator.create("1_85_5", "Gornji grad"));
        zemun.addSublocation(locationCreator.create("1_85_6", "Kalvarija"));
        zemun.addSublocation(locationCreator.create("1_85_7", "Kej"));
        zemun.addSublocation(locationCreator.create("1_85_8", "Marije Bursać"));
        zemun.addSublocation(locationCreator.create("1_85_9", "Meandri"));
        zemun.addSublocation(locationCreator.create("1_85_10", "Nova Galenika"));
        zemun.addSublocation(locationCreator.create("1_85_11", "Novi Grad"));
        zemun.addSublocation(locationCreator.create("1_85_12", "Save Kovačevića"));
        zemun.addSublocation(locationCreator.create("1_85_13", "Teleoptik"));
        zemun.addSublocation(locationCreator.create("1_85_14", "Ćukovac"));
        beograd.addSublocation(zemun);

        beograd.addSublocation(locationCreator.create("1_86", "Zemun Polje"));

        Location zvezdara = locationCreator.create("1_87", "Zvezdara");
        zvezdara.addSublocation(locationCreator.create("1_87_1", "Bulbulder"));
        zvezdara.addSublocation(locationCreator.create("1_87_2", "Bulevar Kralja Aleksandra"));
        zvezdara.addSublocation(locationCreator.create("1_87_3", "Cvetanova ćuprija"));
        zvezdara.addSublocation(locationCreator.create("1_87_4", "Cvetkova pijaca"));
        zvezdara.addSublocation(locationCreator.create("1_87_5", "Denkova bašta"));
        zvezdara.addSublocation(locationCreator.create("1_87_6", "Gradska bolnica"));
        zvezdara.addSublocation(locationCreator.create("1_87_7", "Kluz"));
        zvezdara.addSublocation(locationCreator.create("1_87_8", "Lion"));
        zvezdara.addSublocation(locationCreator.create("1_87_9", "Lipov lad"));
        zvezdara.addSublocation(locationCreator.create("1_87_10", "Mali mokri lug"));
        zvezdara.addSublocation(locationCreator.create("1_87_11", "Novo groblje"));
        zvezdara.addSublocation(locationCreator.create("1_87_12", "Olimp"));
        zvezdara.addSublocation(locationCreator.create("1_87_13", "Rudo"));
        zvezdara.addSublocation(locationCreator.create("1_87_14", "Severni bulevar"));
        zvezdara.addSublocation(locationCreator.create("1_87_15", "Slavujev venac"));
        zvezdara.addSublocation(locationCreator.create("1_87_16", "Učiteljsko naselje"));
        zvezdara.addSublocation(locationCreator.create("1_87_17", "Veliki mokri lug"));
        zvezdara.addSublocation(locationCreator.create("1_87_18", "Vukov spomenik"));
        zvezdara.addSublocation(locationCreator.create("1_87_19", "Zeleno Brdo"));
        zvezdara.addSublocation(locationCreator.create("1_87_20", "Zira"));
        zvezdara.addSublocation(locationCreator.create("1_87_21", "Zvezdara"));
        zvezdara.addSublocation(locationCreator.create("1_87_22", "Zvezdarska Šuma"));
        zvezdara.addSublocation(locationCreator.create("1_87_23", "Đeram pijaca"));
        beograd.addSublocation(zvezdara);

        beograd.addSublocation(locationCreator.create("1_88", "Zvezdin stadion"));
        beograd.addSublocation(locationCreator.create("1_89", "Čukarica"));
        beograd.addSublocation(locationCreator.create("1_90", "Čukarička padina"));
        beograd.addSublocation(locationCreator.create("1_91", "Žarkovo"));
        beograd.addSublocation(locationCreator.create("1_92", "Železnik"));
        beograd.addSublocation(locationCreator.create("1_93", "Dobanovci"));
        beograd.addSublocation(locationCreator.create("1_94", "Barajevo"));


        locations.add(beograd);
        return locations;
    }

    private static class LocationCreator {

        private Location create(String id, String name) {
            return Location.builder().id(id).name(name).sublocations(new LinkedList<>()).build();
        }
    }

    public static List<Location> getLocations() {
        return locations;
    }
}
