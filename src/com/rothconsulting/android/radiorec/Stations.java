package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Build;
import android.util.Log;

public class Stations {

	private final HashMap<String, Integer> landListCh = new HashMap<String, Integer>();
	private final HashMap<String, Integer> landListDe = new HashMap<String, Integer>();
	private final HashMap<String, Integer> landListAt = new HashMap<String, Integer>();
	private final HashMap<String, Integer> landListDiv = new HashMap<String, Integer>();

	private void fillLandList(HashMap<String, Object> m) {
		if (m.get("land").equals("ch")) {
			landListCh.put("" + m.get("name"), (Integer) m.get("icon_small"));
		} else if (m.get("land").equals("de")) {
			landListDe.put("" + m.get("name"), (Integer) m.get("icon_small"));
		} else if (m.get("land").equals("at")) {
			landListAt.put("" + m.get("name"), (Integer) m.get("icon_small"));
		} else {
			landListDiv.put("" + m.get("name"), (Integer) m.get("icon_small"));
		}
	}

	public ArrayList<HashMap<String, Integer>> getLandListCh() {
		Log.d("Stations", "getLandListCh");
		ArrayList<HashMap<String, Integer>> list = new ArrayList<HashMap<String, Integer>>();
		list.add(landListCh);
		return list;
	}

	public HashMap<String, Integer> getLandListDe() {
		return landListDe;
	}

	public HashMap<String, Integer> getLandListAt() {
		return landListAt;
	}

	public HashMap<String, Integer> getLandListDiv() {
		return landListDiv;
	}

	/**
	 * name<br>
	 * icon<br>
	 * icon_small<br>
	 * stream<br>
	 * homepage<br>
	 * webcam<br>
	 * email<br>
	 * sprache<br>
	 * land<br>
	 * stil<br>
	 * 
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getAllStations() {
		ArrayList<HashMap<String, Object>> stationList = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> m;

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

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
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_LORA);
		m.put("icon", R.drawable.radio_lora);
		m.put("icon_small", R.drawable.radio_lora_small);
		m.put("stream", "http://h1788467.stratoserver.net:8100");
		m.put("homepage", "http://www.lora.ch");
		m.put("webcam", "");
		m.put("email", "http://lora.ch/ueberuns/kontakt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_KAISEREGG);
		m.put("icon", R.drawable.radio_kaiseregg);
		m.put("icon_small", R.drawable.radio_kaiseregg_small);
		m.put("stream", "http://kaiseregg.ice.infomaniak.ch/stream128k.mp3");
		m.put("homepage", "http://www.kaiseregg.ch");
		m.put("webcam", "");
		m.put("email",
				"http://www.kaiseregg.ch/index.php?option=com_contact&view=contact&id=1&Itemid=7");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SUNSHINE);
		m.put("icon", R.drawable.radio_sunshine);
		m.put("icon_small", R.drawable.radio_sunshine_small);
		m.put("stream", "http://mp3stream.sunshine.ch:80");
		m.put("homepage", "http://www.sunshine.ch");
		m.put("webcam", "http://www.sunshine.ch/data/webcam/studio2.jpg");
		m.put("email", "http://www.sunshine.ch/dynasite.cfm?dsmid=98592");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_TROPIC);
		m.put("icon", R.drawable.radio_tropic);
		m.put("icon_small", R.drawable.radio_tropic_small);
		m.put("stream", "http://tropic.ice.infomaniak.ch:80/tropic-high.mp3");
		m.put("homepage", "http://www.radio-tropic.ch");
		m.put("webcam", "");
		m.put("email", "http://radio-tropic.ch/contact");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_DJRADIO);
		m.put("icon", R.drawable.radio_djradio);
		m.put("icon_small", R.drawable.radio_djradio_small);
		m.put("stream", "http://streamplus16.leonex.de:17130");
		m.put("homepage", "http://www.djradio.ch");
		m.put("webcam", "");
		m.put("email", "http://www.djradio.ch/kontakt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_LOUNGE_RADIO);
		m.put("icon", R.drawable.radio_loungeradio);
		m.put("icon_small", R.drawable.radio_loungeradio_small);
		m.put("stream", "http://ch3.streamhosting.ch:8080");
		m.put("homepage", "http://www.lounge-radio.com");
		m.put("webcam", "");
		m.put("email",
				"http://www.lounge-radio.com/index.php?option=com_contact&Itemid=42");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SWISS_GROOVE);
		m.put("icon", R.drawable.radio_swissgroove);
		m.put("icon_small", R.drawable.radio_swissgroove_small);
		m.put("stream", "http://server4.digital-webstream.de:13600");
		m.put("homepage", "http://www.swissgroove.ch");
		m.put("webcam", "");
		m.put("email", "http://www.swissgroove.ch/index.php?content=main&id=17");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_RSI_RETE_3);
		m.put("icon", R.drawable.radio_rete3);
		m.put("icon_small", R.drawable.radio_rete3_small);
		m.put("stream", "http://glb-stream12.streamserver.ch/1/retetre/mp3_128");
		m.put("homepage", "http://retetre.rtsi.ch.ch");
		m.put("webcam", "");
		m.put("email",
				"http://retetre.rtsi.ch/index.php?option=com_contact&catid=12&Itemid=30");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ROCK_NATION);
		m.put("icon", R.drawable.radio_rocknation);
		m.put("icon_small", R.drawable.radio_rocknation_small);
		m.put("stream", "http://105-stream-02.datacomm.ch:8000/rocknation");
		m.put("homepage", "http://www.rocknation.ch");
		m.put("webcam", "");
		m.put("email", "info@rocknation.ch");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_3FACH);
		m.put("icon", R.drawable.radio_3fach);
		m.put("icon_small", R.drawable.radio_3fach_small);
		m.put("stream", "http://94.126.18.195:8000");
		m.put("homepage", "http://www.3fach.ch");
		m.put("webcam", "");
		m.put("email", "https://radio3fach.wordpress.com/kontakt/");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_COUNTRY_RADIO);
		m.put("icon", R.drawable.radio_country_radio_switzerland);
		m.put("icon_small", R.drawable.radio_country_radio_switzerland_small);
		m.put("stream", "http://rs3.radiostreamer.com:9330");
		m.put("homepage", "http://www.countryradio.ch");
		m.put("webcam", "");
		m.put("email", "http://www.countryradio.ch/index.php?id=22");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_LIFE_CHANNEL);
		m.put("icon", R.drawable.radio_life_channel);
		m.put("icon_small", R.drawable.radio_life_channel_small);
		m.put("stream", "http://mp3.lifechannel.ch/LC128");
		m.put("homepage", "http://www.lifechannel.ch");
		m.put("webcam", "");
		m.put("email",
				"http://www.lifechannel.ch/fenster_zum_sonntag-feedback___kontakt.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "spirituell");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_GLORIA);
		m.put("icon", R.drawable.radio_gloria);
		m.put("icon_small", R.drawable.radio_gloria_small);
		m.put("stream", "http://live-two.dmd2.ch:8080/radiogloria_lo");
		m.put("homepage", "http://www.radiogloria.ch");
		m.put("webcam", "");
		m.put("email", "http://www.radiogloria.ch/kontakt.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "spirituell");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_GELB_SCHWARZ);
		m.put("icon", R.drawable.radio_gelb_schwarz_yb);
		m.put("icon_small", R.drawable.radio_gelb_schwarz_yb_small);
		m.put("stream",
				"http://radiogelb-schwarz.ice.infomaniak.ch:80/radiogelbschwarz.mp3");
		m.put("homepage", "http://www.radio-gelb-schwarz.ch");
		m.put("webcam",
				"http://data.bkw-fmb.ch/opd/camPics/Stade-de-suisse.jpg");
		m.put("email", "rgs@im-stadion.ch");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "sport");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_FCB_LIVE_RADIO);
		m.put("icon", R.drawable.radio_fcb_live_radio);
		m.put("icon_small", R.drawable.radio_fcb_live_radio_small);
		m.put("stream", "http://195.49.27.25:8000");
		m.put("homepage", "http://www.fcbliveradio.ch");
		m.put("webcam", "");
		m.put("email",
				"http://www.fcbliveradio.ch/index.php?option=com_contact&view=category&catid=12&Itemid=53");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "sport");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_FC_ZUERICH);
		m.put("icon", R.drawable.radio_fc_zuerich);
		m.put("icon_small", R.drawable.radio_fc_zuerich_small);
		m.put("stream", "http://stream.fcz.ch:8000/fcz01");
		m.put("homepage", "http://www.fcz.ch/radio");
		m.put("webcam", "");
		m.put("email", "http://www.fcz.ch/tv/fcz_radio_kontakt.htm");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "sport");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_BACKSTAGE_RADIO);
		m.put("icon", R.drawable.radio_backstage_radio);
		m.put("icon_small", R.drawable.radio_backstage_radio_small);
		m.put("stream",
				"http://broadcast.infomaniak.ch/backstageradio-high.mp3");
		m.put("homepage", "http://www.backstageradio.ch");
		m.put("webcam", "");
		m.put("email",
				"http://www.facebook.com/apps/application.php?id=194482473899008");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_RRO);
		m.put("icon", R.drawable.radio_rro);
		m.put("icon_small", R.drawable.radio_rro_small);
		m.put("stream",
				"http://stream.rro.ch/shoutcast/radiostation2.stream/playlist.m3u8");
		m.put("homepage", "http://www.rro.ch");
		m.put("webcam", "");
		m.put("email", "http://www.rro.ch/cms/?page=kontakt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_MONTE_CARLO);
		m.put("icon", R.drawable.radio_monte_carlo);
		m.put("icon_small", R.drawable.radio_monte_carlo_small);
		m.put("stream", "http://212.40.13.22:11000");
		m.put("homepage", "http://www.radiomontecarlo.ch");
		m.put("webcam", "");
		m.put("email", "info@radiomontecarlo.ch");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_RASA);
		m.put("icon", R.drawable.radio_rasa);
		m.put("icon_small", R.drawable.radio_rasa_small);
		m.put("stream", "http://s7.pop-stream.de:7830");
		m.put("homepage", "http://www.rasa.ch");
		m.put("webcam", "");
		m.put("email", "http://www.rasa.ch/feedback.php");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_BLIND_POWER);
		m.put("icon", R.drawable.radio_blindpower);
		m.put("icon_small", R.drawable.radio_blindpower_small);
		m.put("stream", "http://live-two.dmd2.ch:8080/rbp_hi");
		m.put("homepage", "http://www.radioblindpower.ch");
		m.put("webcam", "");
		m.put("email", "http://www.radioblindpower.ch/studio/studiomail.php");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SINE_MUSIC);
		m.put("icon", R.drawable.radio_sinemusic);
		m.put("icon_small", R.drawable.radio_sinemusic_small);
		m.put("stream", "http://stream.laut.fm/sine-music");
		m.put("homepage", "http://www.sine-music.de");
		m.put("webcam", "");
		m.put("email", "http://www.sine-music.de/?page_id=679");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_KINGSTONHOT);
		m.put("icon", R.drawable.radio_kingston_hot);
		m.put("icon_small", R.drawable.radio_kingston_hot_small);
		m.put("stream", "http://www.kingstonhot.de/shows/");
		m.put("homepage", "http://www.kingstonhot.de");
		m.put("webcam", "");
		m.put("email", "http://34073.speechbox.de");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_CENTRAL);
		m.put("icon", R.drawable.radio_central);
		m.put("icon_small", R.drawable.radio_central_small);
		m.put("stream", "http://central128.mp3.green.ch");
		m.put("homepage", "http://www.radiocenral.ch");
		m.put("webcam", "http://www.radiocentral.ch/data/WebCam/brunnen.jpg");
		m.put("email", "http://www.radiocentral.ch/dynasite.cfm?dsmid=77268");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_CENTRAL_ROCK);
		m.put("icon", R.drawable.radio_central_rock);
		m.put("icon_small", R.drawable.radio_central_rock_small);
		m.put("stream", "http://sp7128.mp3.green.ch");
		m.put("homepage", "http://www.radiocenral.ch");
		m.put("webcam", "http://www.radiocentral.ch/data/WebCam/brunnen.jpg");
		m.put("email", "http://www.radiocentral.ch/dynasite.cfm?dsmid=77268");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_CENTRAL_SWISS);
		m.put("icon", R.drawable.radio_central_swiss);
		m.put("icon_small", R.drawable.radio_central_swiss_small);
		m.put("stream", "http://sp3128.mp3.green.ch");
		m.put("homepage", "http://www.radiocenral.ch");
		m.put("webcam", "http://www.radiocentral.ch/data/WebCam/brunnen.jpg");
		m.put("email", "http://www.radiocentral.ch/dynasite.cfm?dsmid=77268");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_EVIVA);
		m.put("icon", R.drawable.radio_eviva);
		m.put("icon_small", R.drawable.radio_eviva_small);
		m.put("stream", "http://eviva128.mp3.green.ch");
		m.put("homepage", "http://www.eviva.ch");
		m.put("webcam", "http://www.radiocentral.ch/data/WebCam/brunnen.jpg");
		m.put("email", "http://eviva.ch/dynasite.cfm?dsmid=78524");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_GRISCHA);
		m.put("icon", R.drawable.radio_grischa);
		m.put("icon_small", R.drawable.radio_grischa_small);
		m.put("stream", "http://grischa.mp3.green.ch");
		m.put("homepage", "http://www.radiogrischa.ch");
		m.put("webcam",
				"http://www.radiogrischa.ch/sites/default/files/webcams/studio_1.jpg");
		m.put("email", "http://www.radiogrischa.ch/adresseanfahrt");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SUNSHINE_LIVE);
		m.put("icon", R.drawable.radio_sunshinelive);
		m.put("icon_small", R.drawable.radio_sunshinelive_small);
		m.put("stream", "http://62.27.47.15/sunshinelive/livestream.mp3");
		m.put("homepage", "http://www.sunshine-live.de");
		m.put("webcam", "");
		m.put("email", "http://www.sunshine-live.de/pop-ups/mail-ins-studio/");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_OE1);
		m.put("icon", R.drawable.radio_oe1);
		m.put("icon_small", R.drawable.radio_oe1_small);
		m.put("stream", "http://mp3stream3.apasf.apa.at:8000");
		m.put("homepage", "http://oe1.orf.at");
		m.put("webcam", "");
		m.put("email", "http://oe1.orf.at/kontakt");
		m.put("sprache", "de");
		m.put("land", "at");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_OE3);
		m.put("icon", R.drawable.radio_oe3);
		m.put("icon_small", R.drawable.radio_oe3_small);
		m.put("stream", "http://mp3stream7.apasf.apa.at:8000");
		m.put("homepage", "http://oe3.orf.at");
		m.put("webcam", "http://onapp1.orf.at/webcam/oe3/cam2_oe3.jpg");
		m.put("email", "http://cluboe3.orf.at/song/index.php?round=24");
		m.put("sprache", "de");
		m.put("land", "at");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_FM4);
		m.put("icon", R.drawable.radio_oe_fm4);
		m.put("icon_small", R.drawable.radio_oe_fm4_small);
		m.put("stream", "http://mp3stream1.apasf.apa.at:8000");
		m.put("homepage", "http://http://fm4.orf.at");
		m.put("webcam", "");
		m.put("email", "http://our.orf.at/mailform/fm4_kontakt");
		m.put("sprache", "de");
		m.put("land", "at");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_TIROL);
		m.put("icon", R.drawable.radio_orf_tirol);
		m.put("icon_small", R.drawable.radio_orf_tirol_small);
		m.put("stream", "http://mp3stream2.apasf.apa.at:8000");
		m.put("homepage", "http://tirol.orf.at");
		m.put("webcam", "");
		m.put("email", "publikumsservice.tirol@orf.at");
		m.put("sprache", "de");
		m.put("land", "at");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_WIEN);
		m.put("icon", R.drawable.radio_orf_wien);
		m.put("icon_small", R.drawable.radio_orf_wien_small);
		m.put("stream", "http://194.232.200.145:8000");
		m.put("homepage", "http://wien.orf.at");
		m.put("webcam", "");
		m.put("email", "899953@orf.at");
		m.put("sprache", "de");
		m.put("land", "at");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_KAERNTEN);
		m.put("icon", R.drawable.radio_orf_kaernten);
		m.put("icon_small", R.drawable.radio_orf_kaernten_small);
		m.put("stream", "http://194.232.200.159:8000");
		m.put("homepage", "http://kaernten.orf.at");
		m.put("webcam", "");
		m.put("email", "service.kaernten@orf.at");
		m.put("sprache", "de");
		m.put("land", "at");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SALZBURG);
		m.put("icon", R.drawable.radio_orf_salzburg);
		m.put("icon_small", R.drawable.radio_orf_salzburg_small);
		m.put("stream", "http://194.232.200.147:8000");
		m.put("homepage", "http://salzburg.orf.at");
		m.put("webcam", "");
		m.put("email", "publikumsservice.salzburg@orf.at");
		m.put("sprache", "de");
		m.put("land", "at");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_NIEDEROESTERREICH);
		m.put("icon", R.drawable.radio_orf_niederoesterreich);
		m.put("icon_small", R.drawable.radio_orf_niederoesterreich_small);
		m.put("stream", "http://194.232.200.147:8000");
		m.put("homepage", "http://noe.orf.at");
		m.put("webcam", "");
		m.put("email", "noe-publikum@orf.at");
		m.put("sprache", "de");
		m.put("land", "at");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_BUURERADIO);
		m.put("icon", R.drawable.radio_buureradio);
		m.put("icon_small", R.drawable.radio_buureradio_small);
		m.put("stream", "http://live-three.dmd2.ch");
		m.put("homepage", "http://www.buureradio.ch");
		m.put("webcam", "");
		m.put("email",
				"http://www.buureradio.ch/buureradio20/module/songs/searchform.asp");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "volks");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_INDUSTRIE);
		m.put("icon", R.drawable.radio_industrie);
		m.put("icon_small", R.drawable.radio_industrie_small);
		m.put("stream", "http://96.31.83.87:8063");
		m.put("homepage", "http://www.radioindustrie.ch");
		m.put("webcam", "");
		m.put("email", "info@radioindustrie.ch");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_AUDIOASYL);
		m.put("icon", R.drawable.radio_audioasyl);
		m.put("icon_small", R.drawable.radio_audioasyl_small);
		m.put("stream", "http://stream.audioasyl.net/audioasyl");
		m.put("homepage", "http://www.audioasyl.net");
		m.put("webcam", "");
		m.put("email", "info@audioasyl.net");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_PIRATENRADIO);
		m.put("icon", R.drawable.radio_piratenradio);
		m.put("icon_small", R.drawable.radio_piratenradio_small);
		m.put("stream",
				"http://piratenradioch.ice.infomaniak.ch/piratenradioch-128.mp3");
		m.put("homepage", "http://www.piratenradio.ch");
		m.put("webcam", "");
		m.put("email", "info@piratenradio.ch");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_21);
		m.put("icon", R.drawable.radio_21);
		m.put("icon_small", R.drawable.radio_21_small);
		m.put("stream", "http://rs3.stream24.org:8690");
		m.put("homepage", "http://www.radio21.ch");
		m.put("webcam", "");
		m.put("email", "http://radio21.ch/wp/?page_id=481");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ALLGAEUHIT);
		m.put("icon", R.drawable.radio_allgaeuhit);
		m.put("icon_small", R.drawable.radio_allgaeuhit_small);
		m.put("stream", "http://81.169.132.81:7000/dsl");
		m.put("homepage", "http://www.allgaeuhit.de");
		m.put("webcam", "http://www.allgaeuhit.de/webcam.jpg");
		m.put("email", "http://www.allgaeuhit.de/wugru.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_WAVE);
		m.put("icon", R.drawable.radio_wave);
		m.put("icon_small", R.drawable.radio_wave_small);
		m.put("stream", "http://212.83.60.57:2100");
		m.put("homepage", "http://www.radio-wave.ch");
		m.put("webcam", "http://www.radiowave.ch/studio.jpg");
		m.put("email",
				"http://www.radio-wave.ch/cms/index.php?section=contact&cmd=8");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_HELSINKI_GRAZ);
		m.put("icon", R.drawable.radio_helsinki_graz);
		m.put("icon_small", R.drawable.radio_helsinki_graz_small);
		m.put("stream", "http://live.helsinki.at:8088/live128.mp3");
		m.put("homepage", "http://www.helsinki.at");
		m.put("webcam", "");
		m.put("email", "http://helsinki.at/info/kontakt");
		m.put("sprache", "de");
		m.put("land", "at");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SOUNDPORTAL);
		m.put("icon", R.drawable.radio_soundportal);
		m.put("icon_small", R.drawable.radio_soundportal_small);
		m.put("stream", "http://netradio01.soundportal.at:8100/live160");
		m.put("homepage", "http://www.soundportal.at");
		m.put("webcam", "http://soundportal.at/service/webcam");
		m.put("email", "http://soundportal.at/service/musikwunsch/");
		m.put("sprache", "de");
		m.put("land", "at");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_1LIVE);
		m.put("icon", R.drawable.radio_1live);
		m.put("icon_small", R.drawable.radio_1live_small);
		m.put("stream",
				"http://1live.akacast.akamaistream.net/7/706/119434/v1/gnl.akacast.akamaistream.net/1live");
		m.put("homepage", "http://www.einslive.de");
		m.put("webcam", "http://www.einslive.de/webcam/studio_a_1.jpg");
		m.put("email", "http://www.einslive.de/team/kontakt");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_TOP100STATION);
		m.put("icon", R.drawable.radio_top100station);
		m.put("icon_small", R.drawable.radio_top100station_small);
		m.put("stream", "http://87.230.101.11:8000");
		m.put("homepage", "http://www.top100station.de");
		m.put("webcam", "");
		m.put("email", "http://mobile.top100station.de/index.php?mobilesite=20");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_ROCK_STATION);
		m.put("icon", R.drawable.radio_rockstation);
		m.put("icon_small", R.drawable.radio_rockstation_small);
		m.put("stream", "http://rs11.stream24.org:8130");
		m.put("homepage", "http://www.rockstation.ch");
		m.put("webcam", "");
		m.put("email", "http://www.rockstation.ch/pages/contact.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "rock");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SKYLIVE);
		m.put("icon", R.drawable.radio_skylive);
		m.put("icon_small", R.drawable.radio_skylive_small);
		m.put("stream", "http://s10.pop-stream.de:10242");
		m.put("homepage", "http://www.skylive.ch");
		m.put("webcam", "http://www.skylive.ch/viewpage.php?page_id=6");
		m.put("email",
				"http://skylive.ch/infusions/ls_tinychat_panel/ls_tinychat.php");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_TRANCERADIO);
		m.put("icon", R.drawable.radio_tranceradio);
		m.put("icon_small", R.drawable.radio_tranceradio_small);
		m.put("stream", "http://stream3144.init7.net");
		m.put("homepage", "http://www.tranceradio.ch");
		m.put("webcam", "");
		m.put("email", "http://technoradio.ch/contact.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "techno");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_7);
		m.put("icon", R.drawable.radio_7);
		m.put("icon_small", R.drawable.radio_7_small);
		m.put("stream", "http://stream.hoerradar.de:80/mp3-radio7-128");
		m.put("homepage", "http://www.radio7.de");
		m.put("webcam", "");
		m.put("email", "http://radio7.de/index.php?id=75");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SALUE);
		m.put("icon", R.drawable.radio_salue);
		m.put("icon_small", R.drawable.radio_salue_small);
		m.put("stream", "http://62.27.26.45/radiosalue/livestream64m.mp3");
		m.put("homepage", "http://www.radiosalue.de");
		m.put("webcam", "http://www.salue.de/img2/studio.jpg");
		m.put("email", "http://www.salue.de/inside/kontakt.phtml");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_FFH);
		m.put("icon", R.drawable.radio_ffh);
		m.put("icon_small", R.drawable.radio_ffh_small);
		m.put("stream", "http://mp3.ffh.de/radioffh/hqlivestream.mp3");
		m.put("homepage", "http://www.ffh.de");
		m.put("webcam", "http://resource.ffh.de/webcams/ffh/cam1-fullsize.jpg");
		m.put("email", "http://www.ffh.de/ffh-welt/kontakt.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_RT1_SUEDSCHWABEN);
		m.put("icon", R.drawable.radio_rt1_suedschwaben);
		m.put("icon_small", R.drawable.radio_rt1_suedschwaben_small);
		m.put("stream",
				"http://62.27.44.60/rt1suedschwaben/livestream.mp3?token=");
		m.put("homepage", "http://www.rt1-suedschwaben.de");
		m.put("webcam", "");
		m.put("email",
				"http://www.rt1-suedschwaben.de/index.php?pageid=174&puid=7");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_HR_3);
		m.put("icon", R.drawable.radio_hr3);
		m.put("icon_small", R.drawable.radio_hr3_small);
		m.put("stream",
				"http://gffstream.ic.llnwd.net/stream/gffstream_mp3_w69b");
		m.put("homepage", "http://www.hr3.de");
		m.put("webcam", "http://www.hrfoto.dunkel.de/webcams/hr3/studio2.jpg");
		m.put("email",
				"http://www.hr-online.de/website/radio/hr3/index.jsp?rubrik=3532");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_STAR_FM);
		m.put("icon", R.drawable.radio_starfm);
		m.put("icon_small", R.drawable.radio_starfm_small);
		m.put("stream", "http://87.230.53.43:8004");
		m.put("homepage", "http://www.starfm.de/nuernberg");
		m.put("webcam", "");
		m.put("email", "http://www.starfm.de/nuernberg/kontakt.php");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_STAR_FM_FROM_HELL);
		m.put("icon", R.drawable.radio_starfm_hell);
		m.put("icon_small", R.drawable.radio_starfm_hell_small);
		m.put("stream", "http://87.230.53.43:7000");
		m.put("homepage", "http://www.starfm.de/berlin");
		m.put("webcam", "");
		m.put("email", "http://www.starfm.de/berlin/kontakt.php");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_IBIZA_SONICA);
		m.put("icon", R.drawable.radio_ibizasonica);
		m.put("icon_small", R.drawable.radio_ibizasonica_small);
		m.put("stream", "http://stream.wft.es:7005");
		m.put("homepage", "http://www.ibizasonica.com");
		m.put("webcam", "");
		m.put("email", "https://www.facebook.com/ibizasonicaradio");
		m.put("sprache", "es");
		m.put("land", "es");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_AMNESIA);
		m.put("icon", R.drawable.radio_amnesia);
		m.put("icon_small", R.drawable.radio_amnesia_small);
		m.put("stream", "http://stream.estudiosonico.com:7015");
		m.put("homepage", "http://www.amnesia.es");
		m.put("webcam", "");
		m.put("email", "http://www.amnesia.es/webapp/contact");
		m.put("sprache", "es");
		m.put("land", "es");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_BLUE_MARLIN);
		m.put("icon", R.drawable.radio_bluemarlin);
		m.put("icon_small", R.drawable.radio_bluemarlin_small);
		m.put("stream", "http://89.238.146.142:8635");
		m.put("homepage", "http://www.bluemarlinibiza.com");
		m.put("webcam", "");
		m.put("email", "http://www.bluemarlinibiza.com/contact/contact");
		m.put("sprache", "es");
		m.put("land", "es");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_REGENBOGEN);
		m.put("icon", R.drawable.radio_regenbogen);
		m.put("icon_small", R.drawable.radio_regenbogen_small);
		m.put("stream", "http://217.151.151.91:80/live");
		m.put("homepage", "http://www.regenbogen.de");
		m.put("webcam",
				"http://www.regenbogen.de/content/html/shared/webcams/index.html");
		m.put("email",
				"http://www.regenbogen.de/views/service-navigation/kontakt/e-mail.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_FANTASY_DANCE);
		m.put("icon", R.drawable.radio_fantasy_dance);
		m.put("icon_small", R.drawable.radio_fantasy_dance_small);
		m.put("stream", "http://dancefmwebradio.de:8000/dsl_stream.mp3");
		m.put("homepage", "http://www.fantasy967.de");
		m.put("webcam", "");
		m.put("email", "http://www.fantasy967.de/index.php?id=88");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_TIROL_IT);
		m.put("icon", R.drawable.radio_tirol_it);
		m.put("icon_small", R.drawable.radio_tirol_it_small);
		m.put("stream", "http://str2.creacast.com/radiotirol_a");
		m.put("homepage", "http://www.radiotirol.it");
		m.put("webcam", "");
		m.put("email", "http://www.radiotirol.it/index.php?id=18");
		m.put("sprache", "de");
		m.put("land", "it");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_DELTA_RADIO);
		m.put("icon", R.drawable.radio_delta_radio);
		m.put("icon_small", R.drawable.radio_delta_radio_small);
		m.put("stream", "http://stream.hoerradar.de/deltaradio128");
		m.put("homepage", "http://www.deltaradio.ch");
		m.put("webcam", "");
		m.put("email", "http://www.deltaradio.de/delta/kontakt/mail/index.html");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_NDR_1);
		m.put("icon", R.drawable.radio_ndr1);
		m.put("icon_small", R.drawable.radio_ndr1_small);
		m.put("stream",
				"http://ndrstream.ic.llnwd.net/stream/ndrstream_ndr1wellenord_hi_mp3");
		m.put("homepage", "http://www.ndr1.de");
		m.put("webcam", "");
		m.put("email",
				"http://www.ndr.de/wellenord/service/mail_ins_studio/index.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_NDR_2);
		m.put("icon", R.drawable.radio_ndr2);
		m.put("icon_small", R.drawable.radio_ndr2_small);
		m.put("stream",
				"http://ndrstream.ic.llnwd.net/stream/ndrstream_ndr2_hi_mp3");
		m.put("homepage", "http://www.ndr2.de");
		m.put("webcam", "");
		m.put("email", "http://www.ndr.de/ndr2/musik/musikwunsch/index.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_N_JOY);
		m.put("icon", R.drawable.radio_n_joy);
		m.put("icon_small", R.drawable.radio_n_joy_small);
		m.put("stream",
				"http://ndrstream.ic.llnwd.net/stream/ndrstream_n-joy_hi_mp3");
		m.put("homepage", "http://www.n-joy.de");
		m.put("webcam", "");
		m.put("email", "http://www.n-joy.de/kontakt377.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_STEPHANSDOM);
		m.put("icon", R.drawable.radio_stephansdom);
		m.put("icon_small", R.drawable.radio_stephansdom_small);
		m.put("stream",
				"http://srvhost24.serverhosting.apa.net:8000/rsdstream128");
		m.put("homepage", "http://www.radiostephansdom.at");
		m.put("webcam", "");
		m.put("email", "http://www.radiostephansdom.at/kontakt");
		m.put("sprache", "de");
		m.put("land", "at");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_CELITIC_MUSIC);
		m.put("icon", R.drawable.radio_celtic_radio);
		m.put("icon_small", R.drawable.radio_celtic_radio_small);
		m.put("stream", "http://173.192.224.123:8214");
		m.put("homepage", "http://www.celticmusicradio.com");
		m.put("webcam", "");
		m.put("email",
				"http://www.celticradio.net/php/service_mod.php?type=contact");
		m.put("sprache", "de");
		m.put("land", "ir");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_MITTELALTERKLANG);
		m.put("icon", R.drawable.radio_mittelalterklang);
		m.put("icon_small", R.drawable.radio_mittelalterklang_small);
		m.put("stream", "http://62.75.216.154:8050");
		m.put("homepage", "http://www.mittelalterklang.de");
		m.put("webcam", "");
		m.put("email", "administration@mittelalterklang.de");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_SECRETTUBE);
		m.put("icon", R.drawable.radio_secrettube);
		m.put("icon_small", R.drawable.radio_secrettube_small);
		m.put("stream", "http://secrettube.ch:6010");
		m.put("homepage", "http://radio.secrettube.ch");
		m.put("webcam", "");
		m.put("email", "webmaster@secrettube.ch");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_GONG_NUERNBERG);
		m.put("icon", R.drawable.radio_gong_nuernberg);
		m.put("icon_small", R.drawable.radio_gong_nuernberg_small);
		m.put("stream", "http://webradio.gong971.de:8000");
		m.put("homepage", "http://www.gong971.de");
		m.put("webcam", "");
		m.put("email", "http://www.gong971.de/gong971.de/kontakt/index.php");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_PALOMA);
		m.put("icon", R.drawable.radio_paloma);
		m.put("icon_small", R.drawable.radio_paloma_small);
		m.put("stream", "http://he-srv1.radiopaloma.de:80");
		m.put("homepage", "http://www.radiopaloma.de");
		m.put("webcam", "");
		m.put("email", "http://www.radiopaloma.de/kontakt.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_EINS_RBB);
		m.put("icon", R.drawable.radio_eins_rbb);
		m.put("icon_small", R.drawable.radio_eins_rbb_small);
		m.put("stream", "http://rbb.ic.llnwd.net/stream/rbb_radioeins_mp3_m_a");
		m.put("homepage", "http://www.radioeins.de");
		m.put("webcam", "");
		m.put("email", "http://www.radioeins.de/kontakt/index.html");
		m.put("sprache", "de");
		m.put("land", "de");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_DREYECKLAND);
		m.put("icon", R.drawable.radio_dreyeckland);
		m.put("icon_small", R.drawable.radio_dreyeckland_small);
		m.put("stream", "http://85.214.250.73:8000/rdl");
		m.put("homepage", "http://www.rdl.de");
		m.put("webcam", "");
		m.put("email",
				"http://www.rdl.de/index.php?option=com_content&view=article&id=8825&Itemid=466");
		m.put("sprache", "de");
		m.put("land", "ch");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.

		} else {
			stationList.add(m);
			fillLandList(m);
		}

		m = new HashMap<String, Object>();
		m.put("name", Constants.RADIO_NOVAPLANET);
		m.put("icon", R.drawable.radio_novaplanet);
		m.put("icon_small", R.drawable.radio_novaplanet_small);
		m.put("stream", "http://broadcast.infomaniak.ch/radionova-high.mp3");
		m.put("homepage", "http://www.novaplanet.com");
		m.put("webcam", "");
		m.put("email", "http://www.novaplanet.com/content/contact");
		m.put("sprache", "fr");
		m.put("land", "fr");
		m.put("stil", "pop");
		if (Build.VERSION.SDK_INT < 8
				&& Constants.getIgnoreListKleinerAndroid22().contains(
						m.get("name"))) {
			// wenn kleiner 2.1 und in ignoreList, dann nicht hinzufügen.
		} else {
			stationList.add(m);
			fillLandList(m);
		}

		return stationList;
	}
}
