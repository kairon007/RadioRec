package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.HashMap;

public class Stations {

	private HashMap<String, Object> m;
	private ArrayList<HashMap<String, Object>> stationList;

	// 0 Name
	// 1 Icon (int)
	// 2 Stream URL
	// 3 Webcam URL
	// 4 Contact
	// 5 Sprache
	// 6 Land
	// 7 Stil

	public ArrayList<HashMap<String, Object>> getAllStations() {
		stationList = new ArrayList<HashMap<String, Object>>();

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_32);
		m.put("icon", R.drawable.radio_32);
		m.put("icon_small", R.drawable.radio_32_small);
		m.put("stream", "http://radio32-stream.customer.solnet.ch:8000/radio32");
		m.put("homepage", "http://www.radio32.ch");
		m.put("webcam", "http://www.radio32.ch/pages/inc/getcampicfront.cfm");
		m.put("email", "http://www.radio32.ch/?id=257&rub=55");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_32_GOLDIES);
		m.put("icon", R.drawable.radio_32_goldies);
		m.put("icon_small", R.drawable.radio_32_goldies_small);
		m.put("stream",
				"http://radio32-stream.customer.solnet.ch:8100/radio32goldies");
		m.put("homepage", "http://www.goldies.ch");
		m.put("webcam", "http://www.radio32.ch/pages/inc/getcampicfront.cfm");
		m.put("email", "http://www.radio32.ch/?id=685&rub=124");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_HOCH2);
		m.put("icon", R.drawable.radio_hoch_2);
		m.put("icon_small", R.drawable.radio_hoch_2_small);
		m.put("stream", "http://stream1.radiohoch2.ch:80/web");
		m.put("homepage", "http://www.radiohoch2.ch");
		m.put("webcam", "http://webcam.radiohoch2.ch/webcam_big.php");
		m.put("email", "http://radiohoch2.ch/contact");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_CAPITAL_FM);
		m.put("icon", R.drawable.radio_capital_fm);
		m.put("icon_small", R.drawable.radio_capital_fm_small);
		m.put("stream", "http://capitalfm.ice.infomaniak.ch/capitalfm-high.mp3");
		m.put("homepage", "http://www.capitalfm.ch");
		m.put("webcam", "");
		m.put("email", "http://capitalfm.ch/kontakt.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_RABE);
		m.put("icon", R.drawable.radio_rabe);
		m.put("icon_small", R.drawable.radio_rabe_small);
		m.put("stream", "http://stream.rabe.ch:8000/livestream/rabe-mid.mp3");
		m.put("homepage", "http://www.rabe.ch");
		m.put("webcam", "");
		m.put("email", "http://www.rabe.ch/ueber-uns/kontakt.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_24);
		m.put("icon", R.drawable.radio_24);
		m.put("icon_small", R.drawable.radio_24_small);
		m.put("stream", "http://live.radio24.ch/radio24");
		m.put("homepage", "http://www.radio24.ch");
		m.put("webcam",
				"http://80.86.200.92/ext/webcams/thumb.php?file=webcamS1.jpg&width=700&quality=80");
		m.put("email",
				"http://www.radio24.ch/service/standard/Kontakt/story/17621407");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_24_ROCK);
		m.put("icon", R.drawable.radio_24_rock);
		m.put("icon_small", R.drawable.radio_24_rock_small);
		m.put("stream", "http://live.radio24.ch:80/radio24Rock");
		m.put("homepage", "http://www.radio24.ch");
		m.put("webcam",
				"http://80.86.200.92/ext/webcams/thumb.php?file=webcamS1.jpg&width=700&quality=80");
		m.put("email",
				"http://www.radio24.ch/service/standard/Kontakt/story/17621407");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_DRS1);
		m.put("icon", R.drawable.radio_drs_1);
		m.put("icon_small", R.drawable.radio_drs_1_small);
		m.put("stream", "http://glb-stream12.streamserver.ch/2/drs1/mp3_128");
		m.put("homepage", "http://www.drs1.ch");
		m.put("webcam", "http://www.drs1.ch/import/webcam/drs1/webdrs1a.jpg");
		m.put("email",
				"http://www.drs1.ch/www/de/drs1/kontakt/mail-ins-studio.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_DRS1_AGSO);
		m.put("icon", R.drawable.radio_drs_1_ag_so);
		m.put("icon_small", R.drawable.radio_drs_1_ag_so_small);
		m.put("stream",
				"http://glb-stream12.streamserver.ch/1/regi_ag_so/mp3_128");
		m.put("homepage",
				"http://www.drs1.ch/www/de/drs1/nachrichten/regional/aargau-solothurn.html");
		m.put("webcam", "http://www.drs1.ch/import/webcam/drs1/webdrs1a.jpg");
		m.put("email",
				"http://www.drs1.ch/www/de/drs1/kontakt/mail-ins-studio.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_DRS1_BSBL);
		m.put("icon", R.drawable.radio_drs_1_bs_bl);
		m.put("icon_small", R.drawable.radio_drs_1_bs_bl_small);
		m.put("stream",
				"http://glb-stream12.streamserver.ch/1/regi_bs_bl/mp3_128");
		m.put("homepage",
				"http://www.drs1.ch/www/de/drs1/nachrichten/regional/basel-baselland.html");
		m.put("webcam", "http://www.drs1.ch/import/webcam/drs1/webdrs1a.jpg");
		m.put("email",
				"http://www.drs1.ch/www/de/drs1/kontakt/mail-ins-studio.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_DRS1_BEFRVS);
		m.put("icon", R.drawable.radio_drs_1_be_fr_vs);
		m.put("icon_small", R.drawable.radio_drs_1_be_fr_vs_small);
		m.put("stream",
				"http://glb-stream12.streamserver.ch/1/regi_be_fr_vs/mp3_128");
		m.put("homepage",
				"http://www.drs1.ch/www/de/drs1/nachrichten/regional/bern-freiburg-wallis.html");
		m.put("webcam", "http://www.drs1.ch/import/webcam/drs1/webdrs1a.jpg");
		m.put("email",
				"http://www.drs1.ch/www/de/drs1/kontakt/mail-ins-studio.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_DRS2);
		m.put("icon", R.drawable.radio_drs_2);
		m.put("icon_small", R.drawable.radio_drs_2_small);
		m.put("stream", "http://glb-stream12.streamserver.ch/2/drs2/mp3_128");
		m.put("homepage", "http://www.drs2.ch");
		m.put("webcam", "");
		m.put("email",
				"http://www.drs2.ch/www/de/drs2/kontakt/kundendienst.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_DRS3);
		m.put("icon", R.drawable.radio_drs_3);
		m.put("icon_small", R.drawable.radio_drs_3_small);
		m.put("stream", "http://glb-stream12.streamserver.ch/2/drs3/mp3_128");
		m.put("homepage", "http://www.drs3.ch");
		m.put("webcam", "http://www.drs3.ch/import/webcam/drs3/webdrs3a.jpg");
		m.put("email",
				"http://www.drs3.ch/www/de/drs3/kontakt/mail-ins-studio.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_DRS4);
		m.put("icon", R.drawable.radio_drs_4_news);
		m.put("icon_small", R.drawable.radio_drs_4_news_small);
		m.put("stream",
				"http://glb-stream12.streamserver.ch/2/drs4news/mp3_128");
		m.put("homepage", "http://www.drs4news.ch");
		m.put("webcam", "");
		m.put("email",
				"http://www.drs4news.ch/www/de/drs4/kontakt/mail-ins-studio.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_DRS_VIRUS);
		m.put("icon", R.drawable.radio_drs_virus);
		m.put("icon_small", R.drawable.radio_drs_virus_small);
		m.put("stream",
				"http://glb-stream12.streamserver.ch/2/drsvirus/mp3_128");
		m.put("homepage", "http://www.virus.ch");
		m.put("webcam",
				"http://www.drs.ch/import/webcam/virus/virus2.jpg?1304112781446");
		m.put("email", "http://www.virus.ch/virus/kontakt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_DRS_MUSIKWELLE);
		m.put("icon", R.drawable.radio_drs_musikwelle);
		m.put("icon_small", R.drawable.radio_drs_musikwelle_small);
		m.put("stream", "http://glb-stream12.streamserver.ch/2/drsmw/mp3_128");
		m.put("homepage", "http://www.drsmusikwelle.ch");
		m.put("webcam",
				"http://www.drsmusikwelle.ch/import/webcam/drsmw/webdrsmwb.jpg");
		m.put("email",
				"http://www.drsmusikwelle.ch/www/de/drsmusikwelle/kontakt/mail-ins-studio.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ENERGY_BERN);
		m.put("icon", R.drawable.radio_energy_bern);
		m.put("icon_small", R.drawable.radio_energy_bern_small);
		m.put("stream",
				"http://energybern.ice.infomaniak.ch:80/energybern-high.mp3");
		m.put("homepage", "http://www.energy.ch/bern");
		m.put("webcam", "");
		m.put("email", "http://www.energy.ch/bern/inside/kontakt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ENERGY_ZUERICH);
		m.put("icon", R.drawable.radio_energy_zurich);
		m.put("icon_small", R.drawable.radio_energy_zurich_small);
		m.put("stream",
				"http://energyzuerich.ice.infomaniak.ch:80/energyzuerich-high.mp3");
		m.put("homepage", "http://www.energy.ch/zuerich");
		m.put("webcam", "");
		m.put("email", "http://www.energy.ch/zurich/inside/kontakt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ENERGY_SWISS);
		m.put("icon", R.drawable.radio_energy_swiss);
		m.put("icon_small", R.drawable.radio_energy_swiss_small);
		m.put("stream",
				"http://energyswiss.ice.infomaniak.ch:80/energyswiss-high.mp3");
		m.put("homepage", "http://www.energy.ch/webradios");
		m.put("webcam", "");
		m.put("email", "http://www.energy.ch/zurich/inside/kontakt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ENERGY_CHARTS);
		m.put("icon", R.drawable.radio_energy_charts);
		m.put("icon_small", R.drawable.radio_energy_charts_small);
		m.put("stream", "http://broadcast.infomaniak.net/energycharts-high.mp3");
		m.put("homepage", "http://www.energy.ch/webradios");
		m.put("webcam", "");
		m.put("email", "http://www.energy.ch/zurich/inside/kontakt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ENERGY_LOVE);
		m.put("icon", R.drawable.radio_energy_love);
		m.put("icon_small", R.drawable.radio_energy_love_small);
		m.put("stream",
				"http://broadcast.infomaniak.net:80/energylove-high.mp3");
		m.put("homepage", "http://www.energy.ch/webradios");
		m.put("webcam", "");
		m.put("email", "http://www.energy.ch/zurich/inside/kontakt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ENERGY_80);
		m.put("icon", R.drawable.radio_energy_80s);
		m.put("icon_small", R.drawable.radio_energy_80s_small);
		m.put("stream",
				"http://energy80s.ice.infomaniak.ch:80/energy80s-high.mp3");
		m.put("homepage", "http://www.energy.ch/webradios");
		m.put("webcam", "");
		m.put("email", "http://www.energy.ch/zurich/inside/kontakt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ENERGY_90);
		m.put("icon", R.drawable.radio_energy_90s);
		m.put("icon_small", R.drawable.radio_energy_90s_small);
		m.put("stream",
				"http://energy90s.ice.infomaniak.ch:80/energy90s-high.mp3");
		m.put("homepage", "http://www.energy.ch/webradios");
		m.put("webcam", "");
		m.put("email", "http://www.energy.ch/zurich/inside/kontakt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ENERGY_ITALY);
		m.put("icon", R.drawable.radio_energy_italy);
		m.put("icon_small", R.drawable.radio_energy_italy_small);
		m.put("stream",
				"http://energyitaly.ice.infomaniak.ch:80/energyitaly-high.mp3");
		m.put("homepage", "http://www.energy.ch/webradios");
		m.put("webcam", "");
		m.put("email", "http://www.energy.ch/zurich/inside/kontakt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SWISS_CLASSIC);
		m.put("icon", R.drawable.radio_swiss_classic);
		m.put("icon_small", R.drawable.radio_swiss_classic_small);
		m.put("stream", "http://glb-stream12.streamserver.ch/2/rsc_de/mp3_128");
		m.put("homepage", "http://www.radioswissclassic.ch");
		m.put("webcam", "");
		m.put("email", "http://www.radioswissclassic.ch/de/feedback");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SWISS_JAZZ);
		m.put("icon", R.drawable.radio_swiss_jazz);
		m.put("icon_small", R.drawable.radio_swiss_jazz_small);
		m.put("stream", "http://glb-stream12.streamserver.ch/2/rsj/mp3_128");
		m.put("homepage", "http://www.radioswissjazz.ch");
		m.put("webcam", "");
		m.put("email", "http://www.radioswissjazz.ch/de/feedback");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SWISS_POP);
		m.put("icon", R.drawable.radio_swiss_pop);
		m.put("icon_small", R.drawable.radio_swiss_pop_small);
		m.put("stream", "http://glb-stream12.streamserver.ch/2/rsp/mp3_128");
		m.put("homepage", "http://www.radioswisspop.ch");
		m.put("webcam", "");
		m.put("email", "http://www.radioswisspop.ch/de/feedback");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_1);
		m.put("icon", R.drawable.radio_1);
		m.put("icon_small", R.drawable.radio_1_small);
		m.put("stream", "http://stream.radio1.ch:8000/128k");
		m.put("homepage", "http://www.radio1.ch");
		m.put("webcam", "");
		m.put("email", "studio@radio1.ch");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ZUERISEE);
		m.put("icon", R.drawable.radio_zuerichsee);
		m.put("icon_small", R.drawable.radio_zuerichsee_small);
		m.put("stream", "http://mp3.radio.ch:80/radiozuerisee128k");
		m.put("homepage", "http://www.radio.ch");
		m.put("webcam",
				"http://live.radiozurisee.divio.ch//media/external/data_upload/CAM1/SR1_CAM1.jpg");
		m.put("email", "http://www.radio.ch/de/kontakt/mail-ins-studio/");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_PILATUS);
		m.put("icon", R.drawable.radio_pilatus);
		m.put("icon_small", R.drawable.radio_pilatus_small);
		m.put("stream", "http://media.radiopilatus.ch:80/pilatus128");
		m.put("homepage", "http://www.radiopilatus.ch");
		m.put("webcam", "");
		m.put("email", "http://mobile.radiopilatus.ch/kontakt.php");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_BEO);
		m.put("icon", R.drawable.radio_beo);
		m.put("icon_small", R.drawable.radio_beo_small);
		m.put("stream", "http://87.237.169.123:8000/radiobeo.mp3");
		m.put("homepage", "http://www.radiobeo.ch");
		m.put("webcam", "http://www.radiobeo.ch/webcam/current.jpg");
		m.put("email", "sendestudio@radiobeo.ch");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_TOP);
		m.put("icon", R.drawable.radio_top);
		m.put("icon_small", R.drawable.radio_top_small);
		m.put("stream", "http://icecast.radiotop.ch:8000/radiotop");
		m.put("homepage", "http://www.toponline.ch");
		m.put("webcam", "http://www.toponline.ch/webcam/cam_640.jpg");
		m.put("email", "studiofeedback@radiotop.ch");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_TOP_TWO);
		m.put("icon", R.drawable.radio_top_two);
		m.put("icon_small", R.drawable.radio_top_two_small);
		m.put("stream", "http://icecast.radiotop.ch:8000/toptwo");
		m.put("homepage", "http://www.toponline.ch/toptwo");
		m.put("webcam", "http://www.toponline.ch/webcam/cam_640.jpg");
		m.put("email", "studiofeedback@radiotop.ch");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_20MIN);
		m.put("icon", R.drawable.radio_20_min);
		m.put("icon_small", R.drawable.radio_20_min_small);
		m.put("stream", "http://ch4.streamhosting.ch:8080/20min_ger");
		m.put("homepage", "http://www.20min.ch/radio/radio.tmpl");
		m.put("webcam", "");
		m.put("email", "feedback@20minuten.ch");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_NEO_1);
		m.put("icon", R.drawable.radio_neo_1);
		m.put("icon_small", R.drawable.radio_neo_1_small);
		m.put("stream", "http://stream-02.neo1.ch:80/neo1");
		m.put("homepage", "http://www.neo1.ch");
		m.put("webcam",
				"http://www.neo1.ch/index.php?eID=tx_cms_showpic&file=fileadmin%2Fuser_upload%2Fwebcam%2Fwebcam.jpg&md5=94dde500b68cb331ee6493b0e7f2604282c29723&parameters[0]=YTo0OntzOjU6IndpZHRoIjtzOjM6IjYwMCI7czo2OiJoZWlnaHQiO3M6MzoiNjAw&parameters[1]=IjtzOjc6ImJvZHlUYWciO3M6NjoiPGJvZHk%2BIjtzOjQ6IndyYXAiO3M6MzU6Ijxh&parameters[2]=IGhyZWY9ImphdmFzY3JpcHQ6Y2xvc2UoKTsiPnw8L2E%2BIjt9");
		m.put("email", "http://www.neo1.ch/studiomail.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_NEO_ZWEI);
		m.put("icon", R.drawable.radio_neo_2);
		m.put("icon_small", R.drawable.radio_neo_2_small);
		m.put("stream", "http://stream-02.neo1.ch:80/neo2");
		m.put("homepage", "http://www.neozwei.ch");
		m.put("webcam",
				"http://www.neo1.ch/index.php?eID=tx_cms_showpic&file=fileadmin%2Fuser_upload%2Fwebcam%2Fwebcam.jpg&md5=94dde500b68cb331ee6493b0e7f2604282c29723&parameters[0]=YTo0OntzOjU6IndpZHRoIjtzOjM6IjYwMCI7czo2OiJoZWlnaHQiO3M6MzoiNjAw&parameters[1]=IjtzOjc6ImJvZHlUYWciO3M6NjoiPGJvZHk%2BIjtzOjQ6IndyYXAiO3M6MzU6Ijxh&parameters[2]=IGhyZWY9ImphdmFzY3JpcHQ6Y2xvc2UoKTsiPnw8L2E%2BIjt9");
		m.put("email", "http://www.neozwei.ch/index.php?id=314");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_BASEL);
		m.put("icon", R.drawable.radio_basel);
		m.put("icon_small", R.drawable.radio_basel_small);
		m.put("stream", "http://stream3.radiobasel.ch:8000/rb13");
		m.put("homepage", "http://www.radiobasel.ch");
		m.put("webcam", "http://radiobasel.ch/webcam/studio2.jpg");
		m.put("email", "http://radiobasel.ch/kontakt?cid=Studio");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ARGOVIA);
		m.put("icon", R.drawable.radio_argovia);
		m.put("icon_small", R.drawable.radio_argovia_small);
		m.put("stream", "http://argovia.mp3.green.ch");
		m.put("homepage", "http://www.argovia.ch");
		m.put("webcam", "http://www.argovia.ch/live/");
		m.put("email", "http://www.argovia.ch/miniwin-jukes.php");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ARGOVIA_HITMIX);
		m.put("icon", R.drawable.radio_argovia_hitmix);
		m.put("icon_small", R.drawable.radio_argovia_hitmix_small);
		m.put("stream", "http://shoutcast.argovia.ch:8095");
		m.put("homepage", "http://www.argovia.ch");
		m.put("webcam", "http://www.argovia.ch/live/");
		m.put("email", "http://www.argovia.ch/miniwin-jukes.php");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ARGOVIA_CLASSIC_ROCK);
		m.put("icon", R.drawable.radio_argovia_swissclassicrock);
		m.put("icon_small", R.drawable.radio_argovia_swissclassicrock_small);
		m.put("stream", "http://shoutcast.argovia.ch:8090");
		m.put("homepage", "http://www.argovia.ch");
		m.put("webcam", "http://www.argovia.ch/live/");
		m.put("email", "http://www.argovia.ch/miniwin-jukes.php");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_105);
		m.put("icon", R.drawable.radio_105);
		m.put("icon_small", R.drawable.radio_105_small);
		m.put("stream", "http://bas-flu-stream-01.datacomm.ch:8000/105fm");
		m.put("homepage", "http://www.105.ch");
		m.put("webcam", "");
		m.put("email", "http://www.105.ch/channels/105-fm/?article=65");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_105_CLASSICS);
		m.put("icon", R.drawable.radio_105_classics);
		m.put("icon_small", R.drawable.radio_105_classics_small);
		m.put("stream", "http://bas-flu-stream-01.datacomm.ch:8000/105classics");
		m.put("homepage", "http://www.105classics.ch");
		m.put("webcam", "");
		m.put("email",
				"http://www.105.ch/channels/radio-105-classic/?article=65");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_FM1);
		m.put("icon", R.drawable.radio_fm_1);
		m.put("icon_small", R.drawable.radio_fm_1_small);
		m.put("stream", "http://radiofm1.ice.infomaniak.ch:80/radiofm1-128.mp3");
		m.put("homepage", "http://www.radiofm1.ch");
		m.put("webcam", "http://www.radiofm1.ch/index.php?article_id=323");
		m.put("email", "http://www.radiofm1.ch/index.php?article_id=75");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_FM1_MELODY);
		m.put("icon", R.drawable.radio_fm1_melody);
		m.put("icon_small", R.drawable.radio_fm1_melody_small);
		m.put("stream", "http://fm1melody.ice.infomaniak.ch/fm1melody-128.mp3");
		m.put("homepage", "http://www.fm1melody.ch");
		m.put("webcam", "");
		m.put("email", "http://www.fm1melody.ch/index.php?article_id=20");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ANTENNE_BAYERN);
		m.put("icon", R.drawable.radio_antenne_bayern);
		m.put("icon_small", R.drawable.radio_antenne_bayern_small);
		m.put("stream", "http://mp3.webradio.antenne.de:80");
		m.put("homepage", "http://www.antenne.de");
		m.put("webcam",
				"http://www.antenne.de/antenne/incl/php/thumb.php?image=/webcam/studiocam-b.jpg&y=360&resize=1&thumb_path=/webcam/thumb/440x360");
		m.put("email", "http://www.antenne.de/antenne/radio/kontakt/index.php");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ANTENNE_CHILLOUT);
		m.put("icon", R.drawable.radio_antenne_chillout);
		m.put("icon_small", R.drawable.radio_antenne_chillout_small);
		m.put("stream", "http://mp3channels.webradio.antenne.de/chillout");
		m.put("homepage", "http://www.antenne.de");
		m.put("webcam",
				"http://www.antenne.de/antenne/incl/php/thumb.php?image=/webcam/studiocam-b.jpg&y=360&resize=1&thumb_path=/webcam/thumb/440x360");
		m.put("email", "http://www.antenne.de/antenne/radio/kontakt/index.php");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ANTENNE_ROCK);
		m.put("icon", R.drawable.radio_antenne_rock);
		m.put("icon_small", R.drawable.radio_antenne_rock_small);
		m.put("stream", "http://mp3.webradio.rockantenne.de:80");
		m.put("homepage", "http://www.rockantenne.de");
		m.put("webcam",
				"http://www.antenne.de/antenne/incl/php/thumb.php?image=/webcam/studiocam-b.jpg&y=360&resize=1&thumb_path=/webcam/thumb/440x360");
		m.put("email", "http://www.rockantenne.de/mailinsstudio");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "rock");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ANTENNE_CLASSIC_ROCK);
		m.put("icon", R.drawable.radio_antenne_classic_rock);
		m.put("icon_small", R.drawable.radio_antenne_classic_rock_small);
		m.put("stream",
				"http://mp3channels.webradio.antenne.de/classic-rock-live");
		m.put("homepage", "http://www.antenne.de");
		m.put("webcam",
				"http://www.antenne.de/antenne/incl/php/thumb.php?image=/webcam/studiocam-b.jpg&y=360&resize=1&thumb_path=/webcam/thumb/440x360");
		m.put("email", "http://www.antenne.de/antenne/radio/kontakt/index.php");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "rock");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_BAYERN_1);
		m.put("icon", R.drawable.radio_bayern1);
		m.put("icon_small", R.drawable.radio_bayern1_small);
		m.put("stream", "http://gffstream.ic.llnwd.net/stream/gffstream_w10b");
		m.put("homepage", "http://www.bayern1.de");
		m.put("webcam",
				"http://www.br-online.de/content/import/webcams/bayern1/cam2_pult.jpg");
		m.put("email", "studio@bayern1.de");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_BAYERN_2);
		m.put("icon", R.drawable.radio_bayern2);
		m.put("icon_small", R.drawable.radio_bayern2_small);
		m.put("stream", "http://gffstream.ic.llnwd.net/stream/gffstream_w11b");
		m.put("homepage", "http://www.bayern2.de");
		m.put("webcam", "");
		m.put("email",
				"http://www.br.de/radio/bayern2/sendungen/land-und-leute/kontakt/index.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_BAYERN_3);
		m.put("icon", R.drawable.radio_bayern3);
		m.put("icon_small", R.drawable.radio_bayern3_small);
		m.put("stream", "http://gffstream.ic.llnwd.net/stream/gffstream_w12b");
		m.put("homepage", "http://www.bayern2.de");
		m.put("webcam",
				"http://www.br-online.de/content/import/webcams/bayern3/cam7_gast.jpg");
		m.put("email",
				"http://www.br.de/radio/bayern3/service/kontakt/index.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ON_3);
		m.put("icon", R.drawable.radio_bayern_on3);
		m.put("icon_small", R.drawable.radio_bayern_on3_small);
		m.put("stream", "http://gffstream.ic.llnwd.net/stream/gffstream_w9a");
		m.put("homepage", "http://www.on3.de");
		m.put("webcam", "");
		m.put("email", "http://on3.de/focus/29/kontakt");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SWR_1);
		m.put("icon", R.drawable.radio_swr1);
		m.put("icon_small", R.drawable.radio_swr1_small);
		m.put("stream", "http://swr.ic.llnwd.net/stream/swr_mp3_m_swr1bwb");
		m.put("homepage", "http://www.swr1.de");
		m.put("webcam", "");
		m.put("email",
				"http://www.swr.de/swr1/bw/kontakt/-/id=446430/cf=42/s4inx1/index.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SWR_2);
		m.put("icon", R.drawable.radio_swr2);
		m.put("icon_small", R.drawable.radio_swr2_small);
		m.put("stream", "http://swr.ic.llnwd.net/stream/swr_mp3_m_swr2b");
		m.put("homepage", "http://www.swr2.de");
		m.put("webcam", "");
		m.put("email",
				"http://www.swr.de/swr2/-/id=7576/vv=email/nid=7576/did=661414/wfs5qs/index.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SWR_3);
		m.put("icon", R.drawable.radio_swr3);
		m.put("icon_small", R.drawable.radio_swr3_small);
		m.put("stream", "http://swr.ic.llnwd.net/stream/swr_mp3_m_swr3b");
		m.put("homepage", "http://www.swr3.ch");
		m.put("webcam", "http://www.swr3.de/studiocam");
		m.put("email",
				"http://www.swr3.de/service/kontakt/-/id=49820/nsuyg4/index.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SWR_4);
		m.put("icon", R.drawable.radio_swr4);
		m.put("icon_small", R.drawable.radio_swr4_small);
		m.put("stream", "http://swr.ic.llnwd.net/stream/swr_mp3_m_swr4bwb");
		m.put("homepage", "http://www.swr4.de");
		m.put("webcam", "");
		m.put("email",
				"http://www.swr.de/swr4/bw/-/id=233374/vv=email/nid=233374/did=259762/1gqvrz9/index.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_DAS_DING);
		m.put("icon", R.drawable.radio_dasding);
		m.put("icon_small", R.drawable.radio_dasding_small);
		m.put("stream", "http://swr.ic.llnwd.net/stream/swr_mp3_m_dasdinga");
		m.put("homepage", "http://www.dasding.de");
		m.put("webcam", "http://www.dasding.de/ext/webcam/webcam770.php?cam=2");
		m.put("email", "http://www.dasding.de/ext/dingmessage/index.php");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_BIG_FM);
		m.put("icon", R.drawable.radio_bigfm);
		m.put("icon_small", R.drawable.radio_bigfm_small);
		m.put("stream", "http://srv05.bigstreams.de/bigfm-mp3-96");
		m.put("homepage", "http://www.bigfm.de");
		m.put("webcam", "http://static.bigfm.de/webcam/cam6/webcam06.jpg");
		m.put("email",
				"http://www.bigfm.de/content/html/shared/studiomail/index.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_TOXIC_FM);
		m.put("icon", R.drawable.radio_toxic_fm);
		m.put("icon_small", R.drawable.radio_toxic_fm_small);
		m.put("stream", "http://toxicfm.ice.infomaniak.ch:80/toxicfm-128.mp3");
		m.put("homepage", "http://www.toxicfm.ch");
		m.put("webcam", "");
		m.put("email", "studio@toxic.fm");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_X);
		m.put("icon", R.drawable.radio_x);
		m.put("icon_small", R.drawable.radio_x_small);
		m.put("stream", "http://mp3.radiox.ch/standard.mp3");
		m.put("homepage", "http://www.radiox.ch");
		m.put("webcam", "");
		m.put("email", "http://www.radiox.ch/service/wuko");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_KANAL_K);
		m.put("icon", R.drawable.radio_kanal_k);
		m.put("icon_small", R.drawable.radio_kanal_k_small);
		m.put("stream", "http://82.197.165.150");
		m.put("homepage", "http://www.kanalk.ch");
		m.put("webcam", "");
		m.put("email", "http://www.kanalk.ch/ueber-uns/kontakt/");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SEEFUNK);
		m.put("icon", R.drawable.radio_seefunk);
		m.put("icon_small", R.drawable.radio_seefunk_small);
		m.put("stream", "http://webradio.radio-seefunk.de:8000");
		m.put("homepage", "http://www.radio-seefunk.de");
		m.put("webcam", "");
		m.put("email", "http://www.radio-seefunk.de/default.aspx?ID=11550");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_INSIDE);
		m.put("icon", R.drawable.radio_inside);
		m.put("icon_small", R.drawable.radio_inside_small);
		m.put("stream",
				"http://rs43.stream24.org:8310/;stream0.238134464714676.mp3");
		m.put("homepage", "http://www.radioinside.ch");
		m.put("webcam", "http://www.radioinside.ch/~kamera/studio1.jpg");
		m.put("email", "http://www.radioinside.ch/index.php/studiomail");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_FREIBURG);
		m.put("icon", R.drawable.radio_fr);
		m.put("icon_small", R.drawable.radio_fr_small);
		m.put("stream",
				"http://radiofreiburg.ice.infomaniak.ch:80/radiofreiburg-high.mp3");
		m.put("homepage", "http://www.radiofr.ch");
		m.put("webcam", "");
		m.put("email", "http://www.radiofr.ch/de/kontakt.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_LIECHSTENSTEIN);
		m.put("icon", R.drawable.radio_li);
		m.put("icon_small", R.drawable.radio_li_small);
		m.put("stream", "http://live.radiol.li:8000/live");
		m.put("homepage", "http://www.radio.li");
		m.put("webcam", "");
		m.put("email",
				"http://www.radio.li/hoererservice/mein-musikwunsch.html?type=3");
		m.put("sprache", "de");
		m.put("land", "li");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_COULEUR_3);
		m.put("icon", R.drawable.radio_couleur3);
		m.put("icon_small", R.drawable.radio_couleur3_small);
		m.put("stream", "http://broadcast.infomaniak.ch/rsr-couleur3-high.mp3");
		m.put("homepage", "http://www.couleur3.ch");
		m.put("webcam", "");
		m.put("email",
				"http://www.rsr.ch/#/corporate/contact/?contact=couleur3");
		m.put("sprache", "fr");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_RFJ);
		m.put("icon", R.drawable.radio_rfj);
		m.put("icon_small", R.drawable.radio_rfj_small);
		m.put("stream", "http://broadcast.infomaniak.net/rfj-high.mp3");
		m.put("homepage", "http://www.rfj.ch");
		m.put("webcam", "");
		m.put("email", "http://www.rfj.ch/rfj/Radio/Contact.html");
		m.put("sprache", "fr");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ROUGE_FM);
		m.put("icon", R.drawable.radio_rougefm);
		m.put("icon_small", R.drawable.radio_rougefm_small);
		m.put("stream", "http://broadcast.infomaniak.ch/rougefm-high.mp3");
		m.put("homepage", "http://www.rougefm.com");
		m.put("webcam", "");
		m.put("email", "http://www.rougefm.com/radio/contact.php");
		m.put("sprache", "fr");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SOMA_FM);
		m.put("icon", R.drawable.radio_somafm_sgoa);
		m.put("icon_small", R.drawable.radio_somafm_sgoa_small);
		m.put("stream", "http://ice.somafm.com/suburbsofgoa");
		m.put("homepage", "http://www.somafm.com");
		m.put("webcam", "");
		m.put("email", "http://somafm.com/contact");
		m.put("sprache", "en");
		m.put("land", "de");
		m.put("stil", "pop");
		stationList.add(m);

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_STADTFILTER);
		m.put("icon", R.drawable.radio_stadtfilter);
		m.put("icon_small", R.drawable.radio_stadtfilter_small);
		m.put("stream", "http://stream.stadtfilter.net:8000");
		m.put("homepage", "http://www.stadtfilter.ch");
		m.put("webcam", "");
		m.put("email", "http://www.stadtfilter.ch/StadtfilterKontakt/Kontakt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		stationList.add(m);

		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);

		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);

		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);

		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);

		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);
		//
		// m = new HashMap<String, Object>();
		// m.put("name", Constants.RADIO_);
		// m.put("icon", R.drawable.radio_);
		// m.put("icon_small", R.drawable.radio_);
		// m.put("stream", "");
		// m.put("homepage", "http://www.radiopilatus.ch");
		// m.put("webcam", "");
		// m.put("email", "");
		// m.put("sprache", "de");
		// m.put("land", "ch");
		// m.put("stil", "pop");
		// stationList.add(m);

		return stationList;
	}
}
