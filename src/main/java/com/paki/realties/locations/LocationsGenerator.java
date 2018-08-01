package com.paki.realties.locations;

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

        beograd.addSublocation(locationCreator.create("1_14", "Borča"));

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
        beograd.addSublocation(locationCreator.create("1_18", "Dedinje"));
        beograd.addSublocation(locationCreator.create("1_19", "Dorćol"));
        beograd.addSublocation(locationCreator.create("1_20", "Filmski grad"));
        beograd.addSublocation(locationCreator.create("1_21", "Galenika"));
        beograd.addSublocation(locationCreator.create("1_22", "Golf naselje"));
        beograd.addSublocation(locationCreator.create("1_23", "Grocka"));
        beograd.addSublocation(locationCreator.create("1_24", "Gundulićev venac"));
        beograd.addSublocation(locationCreator.create("1_25", "Institut za majku i dete"));
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
        beograd.addSublocation(locationCreator.create("1_38", "Kumodraž"));
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

        beograd.addSublocation(locationCreator.create("1_47", "Miljakovac"));
        beograd.addSublocation(locationCreator.create("1_48", "Mirijevo"));

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
        nbg.addSublocation(locationCreator.create("1_49_42", "Paviljoni"));
        nbg.addSublocation(locationCreator.create("1_49_43", "Blok 70 (Kineski TC)"));
        nbg.addSublocation(locationCreator.create("1_49_44", "Blok 70a"));
        nbg.addSublocation(locationCreator.create("1_49_45", "Dr Ivana Ribara"));
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

        // Novi Sad

        Location noviSad = locationCreator.create("2", "Novi Sad");
        noviSad.addSublocation(locationCreator.create("2_1","Adamovićevo naselje"));
        noviSad.addSublocation(locationCreator.create("2_2","Adice"));
        noviSad.addSublocation(locationCreator.create("2_3","Avijatičarsko naselje"));
        noviSad.addSublocation(locationCreator.create("2_4","Banatić"));
        noviSad.addSublocation(locationCreator.create("2_5","Betanija"));
        noviSad.addSublocation(locationCreator.create("2_6","Bistrica"));
        noviSad.addSublocation(locationCreator.create("2_7","Bulevar"));
        noviSad.addSublocation(locationCreator.create("2_8","Cara Dušana"));
        noviSad.addSublocation(locationCreator.create("2_9","Centar"));
        noviSad.addSublocation(locationCreator.create("2_10","Detelinara"));
        noviSad.addSublocation(locationCreator.create("2_11","Detelinara nova"));
        noviSad.addSublocation(locationCreator.create("2_12","Dumbovo"));
        noviSad.addSublocation(locationCreator.create("2_13","Futog"));
        noviSad.addSublocation(locationCreator.create("2_14","Futoška pijaca"));
        noviSad.addSublocation(locationCreator.create("2_15","Futoški put"));
        noviSad.addSublocation(locationCreator.create("2_16","Gornje livade"));
        noviSad.addSublocation(locationCreator.create("2_17","Grbavica"));
        noviSad.addSublocation(locationCreator.create("2_18","Gsp"));
        noviSad.addSublocation(locationCreator.create("2_19","Industrijska zona jug"));
        noviSad.addSublocation(locationCreator.create("2_20","Jodna banja"));
        noviSad.addSublocation(locationCreator.create("2_21","Jugovićevo"));
        noviSad.addSublocation(locationCreator.create("2_22","Kameničko ostrvo"));
        noviSad.addSublocation(locationCreator.create("2_23","Kamenjar"));
        noviSad.addSublocation(locationCreator.create("2_24","Karagača"));
        noviSad.addSublocation(locationCreator.create("2_25","Klisa"));
        noviSad.addSublocation(locationCreator.create("2_26","Ledinci"));
        noviSad.addSublocation(locationCreator.create("2_27","Liman 1"));
        noviSad.addSublocation(locationCreator.create("2_28","Liman 2"));
        noviSad.addSublocation(locationCreator.create("2_29","Liman 3"));
        noviSad.addSublocation(locationCreator.create("2_30","Liman 4"));
        noviSad.addSublocation(locationCreator.create("2_31","Lipov gaj"));
        noviSad.addSublocation(locationCreator.create("2_32","Mišeluk"));
        noviSad.addSublocation(locationCreator.create("2_33","Novi Majur"));
        noviSad.addSublocation(locationCreator.create("2_34","Novo Naselje"));
        noviSad.addSublocation(locationCreator.create("2_35","Petrovaradin"));
        noviSad.addSublocation(locationCreator.create("2_36","Podbara"));
        noviSad.addSublocation(locationCreator.create("2_37","Riblja pijaca"));
        noviSad.addSublocation(locationCreator.create("2_38","Ribnjak"));
        noviSad.addSublocation(locationCreator.create("2_39","Rotkvarija"));
        noviSad.addSublocation(locationCreator.create("2_40","Sajam"));
        noviSad.addSublocation(locationCreator.create("2_41","Sajlovo"));
        noviSad.addSublocation(locationCreator.create("2_43","Salajka"));
        noviSad.addSublocation(locationCreator.create("2_44","Satelit"));
        noviSad.addSublocation(locationCreator.create("2_45","Savina"));
        noviSad.addSublocation(locationCreator.create("2_46","Slana bara"));
        noviSad.addSublocation(locationCreator.create("2_47","Socijalno"));
        noviSad.addSublocation(locationCreator.create("2_48","Somborski bulevar"));
        noviSad.addSublocation(locationCreator.create("2_49","Sonsi"));
        noviSad.addSublocation(locationCreator.create("2_50","Spens"));
        noviSad.addSublocation(locationCreator.create("2_51","Stari Majur"));
        noviSad.addSublocation(locationCreator.create("2_52","Subotički bulevar"));
        noviSad.addSublocation(locationCreator.create("2_53","Telep"));
        noviSad.addSublocation(locationCreator.create("2_54","Veliki rit"));
        noviSad.addSublocation(locationCreator.create("2_55","Veternik"));
        noviSad.addSublocation(locationCreator.create("2_56","Vidovdansko naselje"));
        noviSad.addSublocation(locationCreator.create("2_57","Šarengrad"));
        noviSad.addSublocation(locationCreator.create("2_58","Železnička stanica"));

        // Kragujevac

        Location kragujevac = locationCreator.create("3", "Kragujevac");
        kragujevac.addSublocation(locationCreator.create("3_1","Aerodrom"));
        kragujevac.addSublocation(locationCreator.create("3_2","Bagremar"));
        kragujevac.addSublocation(locationCreator.create("3_3","Bubanj"));
        kragujevac.addSublocation(locationCreator.create("3_4","Centar"));
        kragujevac.addSublocation(locationCreator.create("3_5","Centar preko Lepenice"));
        kragujevac.addSublocation(locationCreator.create("3_6","Erdoglija"));
        kragujevac.addSublocation(locationCreator.create("3_7","Mala vaga"));
        kragujevac.addSublocation(locationCreator.create("3_8","Ozon"));
        kragujevac.addSublocation(locationCreator.create("3_9","Palilule"));
        kragujevac.addSublocation(locationCreator.create("3_10","Stanovo"));
        kragujevac.addSublocation(locationCreator.create("3_11","Stara kolonija"));
        kragujevac.addSublocation(locationCreator.create("3_12","Stara varoš"));
        kragujevac.addSublocation(locationCreator.create("3_13","Sušice"));
        kragujevac.addSublocation(locationCreator.create("3_14","Vašarište"));
        kragujevac.addSublocation(locationCreator.create("3_15","Veliki park"));
        kragujevac.addSublocation(locationCreator.create("3_16","Zvezda"));

        // Kraljevo

        Location kraljevo = locationCreator.create("4", "Kraljevo");
        kraljevo.addSublocation(locationCreator.create("4_1","Centar"));
        kraljevo.addSublocation(locationCreator.create("4_2","Dositejeva ulica"));
        kraljevo.addSublocation(locationCreator.create("4_3","Stara čaršija"));
        kraljevo.addSublocation(locationCreator.create("4_4","Vojno naselje"));
        kraljevo.addSublocation(locationCreator.create("4_5","Zelengora"));

        // Kruševac

        Location krusevac = locationCreator.create("5", "Kruševac");
        krusevac.addSublocation(locationCreator.create("5_1","Bagdala"));
        krusevac.addSublocation(locationCreator.create("5_2","Blagoja Parovića"));
        krusevac.addSublocation(locationCreator.create("5_3","Bronks"));
        krusevac.addSublocation(locationCreator.create("5_4","Centar"));
        krusevac.addSublocation(locationCreator.create("5_5","Dragomir Gajić"));
        krusevac.addSublocation(locationCreator.create("5_6","Pejton"));
        krusevac.addSublocation(locationCreator.create("5_7","Rasadnik"));
        krusevac.addSublocation(locationCreator.create("5_8","Sup"));
        krusevac.addSublocation(locationCreator.create("5_9","Trg Kosturnica"));
        krusevac.addSublocation(locationCreator.create("5_10","Ujedinjene nacije"));
        krusevac.addSublocation(locationCreator.create("5_11","Vlado Jurić"));

        // Niš

        Location nis = locationCreator.create("6", "Niš");
        nis.addSublocation(locationCreator.create("6_1","Apelovac"));
        nis.addSublocation(locationCreator.create("6_2","Beverli hils"));
        nis.addSublocation(locationCreator.create("6_3","Bulevar Nemanjića"));
        nis.addSublocation(locationCreator.create("6_4","Bulevar zona"));
        nis.addSublocation(locationCreator.create("6_5","Centar"));
        nis.addSublocation(locationCreator.create("6_6","Crvena Zvezda"));
        nis.addSublocation(locationCreator.create("6_7","Crveni krst"));
        nis.addSublocation(locationCreator.create("6_8","Crveni pevac"));
        nis.addSublocation(locationCreator.create("6_9","Delijski vis"));
        nis.addSublocation(locationCreator.create("6_10","Durlan"));
        nis.addSublocation(locationCreator.create("6_11","Duvanište"));
        nis.addSublocation(locationCreator.create("6_12","Gabrovačka reka"));
        nis.addSublocation(locationCreator.create("6_13","Jagodin mala"));
        nis.addSublocation(locationCreator.create("6_14","Kalač brdo"));
        nis.addSublocation(locationCreator.create("6_15","Kičevo"));
        nis.addSublocation(locationCreator.create("6_16","Komren"));
        nis.addSublocation(locationCreator.create("6_17","Kovanluk"));
        nis.addSublocation(locationCreator.create("6_18","Krive livade"));
        nis.addSublocation(locationCreator.create("6_19","Ledena stena"));
        nis.addSublocation(locationCreator.create("6_20","Marger"));
        nis.addSublocation(locationCreator.create("6_21","Medijana"));
        nis.addSublocation(locationCreator.create("6_22","Mika Protić (KP dom)"));
        nis.addSublocation(locationCreator.create("6_23","Nova železnička kolonija"));
        nis.addSublocation(locationCreator.create("6_24","Palilula"));
        nis.addSublocation(locationCreator.create("6_25","Pantelej"));
        nis.addSublocation(locationCreator.create("6_26","Ratko Jović"));
        nis.addSublocation(locationCreator.create("6_27","Staro groblje"));
        nis.addSublocation(locationCreator.create("6_28","Tehnički fakultet"));
        nis.addSublocation(locationCreator.create("6_29","Trošarina"));
        nis.addSublocation(locationCreator.create("6_30","Vrežina"));
        nis.addSublocation(locationCreator.create("6_31","Ćalije"));
        nis.addSublocation(locationCreator.create("6_32","Ćele kula"));
        nis.addSublocation(locationCreator.create("6_33","Čair"));

        // Pančevo

        Location pancevo = locationCreator.create("7", "Pančevo");
        pancevo.addSublocation(locationCreator.create("7_1","Centar"));
        pancevo.addSublocation(locationCreator.create("7_2","Gornji grad Margita"));
        pancevo.addSublocation(locationCreator.create("7_3","Kotež 1"));
        pancevo.addSublocation(locationCreator.create("7_4","Kotež 2"));
        pancevo.addSublocation(locationCreator.create("7_5","Kudeljarski nasip"));
        pancevo.addSublocation(locationCreator.create("7_6","Misa"));
        pancevo.addSublocation(locationCreator.create("7_8","Novi svet"));
        pancevo.addSublocation(locationCreator.create("7_9","Sodara"));
        pancevo.addSublocation(locationCreator.create("7_10","Strelište"));
        pancevo.addSublocation(locationCreator.create("7_12","Tesla"));
        pancevo.addSublocation(locationCreator.create("7_13","Tip stanko"));
        pancevo.addSublocation(locationCreator.create("7_14","Vojlovica"));


        locations.add(beograd);
        locations.add(noviSad);
        locations.add(kragujevac);
        locations.add(kraljevo);
        locations.add(krusevac);
        locations.add(nis);
        locations.add(pancevo);
        locations.add(locationCreator.create("8", "Aleksinac"));
        locations.add(locationCreator.create("9", "Apatin"));
        locations.add(locationCreator.create("10", "Aranđelovac"));
        locations.add(locationCreator.create("11", "Arilje"));
        locations.add(locationCreator.create("12", "Bačka Palanka"));
        locations.add(locationCreator.create("13", "Bačka Topola"));
        locations.add(locationCreator.create("14", "Bački Petrovac"));
        locations.add(locationCreator.create("15", "Bela Crkva"));
        locations.add(locationCreator.create("16", "Beočin"));
        locations.add(locationCreator.create("17", "Bečej"));
        locations.add(locationCreator.create("18", "Bor"));
        locations.add(locationCreator.create("19", "Divčibare"));
        locations.add(locationCreator.create("20", "Inđija"));
        locations.add(locationCreator.create("21", "Ivanjica"));
        locations.add(locationCreator.create("22", "Jagodina"));
        locations.add(locationCreator.create("23", "Kikinda"));
        locations.add(locationCreator.create("24", "Kladovo"));
        locations.add(locationCreator.create("25", "Kopaonik"));
        locations.add(locationCreator.create("26", "Kosovska Mitrovica"));
        locations.add(locationCreator.create("27", "Kovin"));
        locations.add(locationCreator.create("28", "Lazarevac"));
        locations.add(locationCreator.create("29", "Leskovac"));
        locations.add(locationCreator.create("30", "Loznica"));
        locations.add(locationCreator.create("31", "Majdanpek"));
        locations.add(locationCreator.create("32", "Mali Zvornik"));
        locations.add(locationCreator.create("33", "Negotin"));
        locations.add(locationCreator.create("34", "Nova Pazova"));
        locations.add(locationCreator.create("35", "Novi Pazar"));
        locations.add(locationCreator.create("36", "Obrenovac"));
        locations.add(locationCreator.create("37", "Paraćin"));
        locations.add(locationCreator.create("38", "Petrovac na Mlavi"));
        locations.add(locationCreator.create("39", "Požarevac"));
        locations.add(locationCreator.create("40", "Priboj"));
        locations.add(locationCreator.create("41", "Prijepolje"));
        locations.add(locationCreator.create("42", "Priština"));
        locations.add(locationCreator.create("43", "Prokuplje"));
        locations.add(locationCreator.create("44", "Raška"));
        locations.add(locationCreator.create("45", "Ruma"));
        locations.add(locationCreator.create("46", "Senta"));
        locations.add(locationCreator.create("47", "Smederevo"));
        locations.add(locationCreator.create("48", "Sokobanja"));
        locations.add(locationCreator.create("49", "Sombor"));
        locations.add(locationCreator.create("50", "Sremska Kamenica"));
        locations.add(locationCreator.create("51", "Sremska Mitrovica"));
        locations.add(locationCreator.create("52", "Sremski Karlovci"));
        locations.add(locationCreator.create("53", "Stara Pazova"));
        locations.add(locationCreator.create("54", "Subotica"));
        locations.add(locationCreator.create("55", "Temerin"));
        locations.add(locationCreator.create("56", "Trstenik"));
        locations.add(locationCreator.create("57", "Ub"));
        locations.add(locationCreator.create("58", "Užice"));
        locations.add(locationCreator.create("59", "Valjevo"));
        locations.add(locationCreator.create("60", "Velika Plana"));
        locations.add(locationCreator.create("61", "Veliko Gradište"));
        locations.add(locationCreator.create("62", "Vinča"));
        locations.add(locationCreator.create("63", "Vranje"));
        locations.add(locationCreator.create("64", "Vrbas"));
        locations.add(locationCreator.create("65", "Vrnjačka Banja"));
        locations.add(locationCreator.create("66", "Vršac"));
        locations.add(locationCreator.create("67", "Zaječar"));
        locations.add(locationCreator.create("68", "Zlatibor"));
        locations.add(locationCreator.create("69", "Zrenjanin"));
        locations.add(locationCreator.create("70", "Ćuprija"));
        locations.add(locationCreator.create("71", "Čajetina"));
        locations.add(locationCreator.create("72", "Čačak"));
        locations.add(locationCreator.create("73", "Šabac"));
        locations.add(locationCreator.create("74", "Šid"));
        locations.add(locationCreator.create("75", "Žabalj"));
        locations.add(locationCreator.create("76", "Žitište"));
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