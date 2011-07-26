package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.List;

public class Constants {

	/**
	 * Shoutcast streams work only with Andorid 2.2 an later. Shoutcast streams
	 * will be ignored for older Android levels.
	 * 
	 * @return
	 */
	public static final List<String> getIgnoreListKleinerAndroid22() {
		List<String> ignoreList = new ArrayList<String>();
		ignoreList.add(RADIO_105);
		ignoreList.add(RADIO_ANTENNE_BAYERN);
		ignoreList.add(RADIO_KANAL_K);
		ignoreList.add(RADIO_SEEFUNK);
		ignoreList.add(RADIO_ARGOVIA);
		ignoreList.add(RADIO_ARGOVIA_HITMIX);
		ignoreList.add(RADIO_ARGOVIA_CLASSIC_ROCK);
		ignoreList.add(RADIO_INSIDE);
		ignoreList.add(RADIO_STADTFILTER);
		ignoreList.add(RADIO_LORA);
		ignoreList.add(RADIO_KAISEREGG);
		ignoreList.add(RADIO_SUNSHINE);
		ignoreList.add(RADIO_DJRADIO);
		ignoreList.add(RADIO_LOUNGE_RADIO);
		ignoreList.add(RADIO_SWISS_GROOVE);
		ignoreList.add(RADIO_3FACH);
		ignoreList.add(RADIO_COUNTRY_RADIO);
		ignoreList.add(RADIO_FCB_LIVE_RADIO);
		return ignoreList;
	}

	/**
	 * Android 2.2 has problems playing Icecast streams. They will be ignored.
	 * 
	 * @return
	 */
	public static final List<String> getIgnoreListGleichAndroid22() {
		List<String> ignoreList = new ArrayList<String>();
		ignoreList.add(RADIO_PILATUS);
		return ignoreList;
	}

	public static String PREFERENCES_FILE = "RadioRecPrefs";
	public static final String SELECTED_STATION_INDEX = "MySelectedStationIndex";
	public static final String SELECTED_STATION_NAME = "MySelectedStationName";
	public static final String SELECTED_STATION_ICON = "MySelectedStationIcon";
	public static final String SELECTED_STATION_HOMEPAGE = "MySelectedStationHomepage";
	public static final String SELECTED_STATION_STREAM = "MySelectedStationStream";
	public static final String SELECTED_STATION_WEBCAM = "MySelectedStationWebcam";
	public static final String SELECTED_STATION_CONTACT = "MySelectedStationContact";

	public static final int NOTIFICATION_ID_RADIO_IS_PLAYING = 1;
	public static final int NOTIFICATION_ID_RECORDING = 2;
	public static final int NOTIFICATION_ID_ERROR_CONNECTION = 3;
	public static final int PRESS_BACK_BUTTON = 10;

	public static String ADMOB_PUBLISHER_ID = "a14dc9885936cd9";

	public static final String RADIO_DRS1 = "Radio DRS1";
	public static final String RADIO_DRS2 = "Radio DRS2";
	public static final String RADIO_DRS3 = "Radio DRS3";
	public static final String RADIO_DRS4 = "Radio DRS4 News";
	public static final String RADIO_DRS_VIRUS = "Radio DRS Virus";
	public static final String RADIO_SWISS_CLASSIC = "Radio Swiss Classic";
	public static final String RADIO_SWISS_JAZZ = "Radio Swiss Jazz";
	public static final String RADIO_SWISS_POP = "Radio Swiss Pop";
	public static final String RADIO_ENERGY_BERN = "Radio Energy Bern";
	public static final String RADIO_ENERGY_ZUERICH = "Radio Energy Zürich";
	public static final String RADIO_ENERGY_SWISS = "Radio Energy Swiss";
	public static final String RADIO_32 = "Radio 32";
	public static final String RADIO_32_GOLDIES = "Radio 32 Goldies";
	public static final String RADIO_24 = "Radio 24";
	public static final String RADIO_24_ROCK = "Radio 24 Rock";
	public static final String RADIO_RABE = "Radio RaBe";
	public static final String RADIO_CAPITAL_FM = "Capital FM";
	public static final String RADIO_HOCH2 = "Radio Hoch2";
	public static final String RADIO_1 = "Radio 1";
	public static final String RADIO_ZUERISEE = "Radio Zürisee";
	public static final String RADIO_PILATUS = "Radio Pilatus";
	public static final String RADIO_BEO = "Radio BeO";
	public static final String RADIO_TOP = "Radio Top";
	public static final String RADIO_TOP_TWO = "Radio Top Two";
	public static final String RADIO_20MIN = "Radio 20 Minuten";
	public static final String RADIO_BASEL = "Radio Basel";
	public static final String RADIO_NEO_1 = "Radio neo 1";
	public static final String RADIO_NEO_ZWEI = "Radio neo zwei";
	public static final String RADIO_ARGOVIA = "Radio Argovia";
	public static final String RADIO_ARGOVIA_HITMIX = "Radio Argovia Hitmix";
	public static final String RADIO_ARGOVIA_CLASSIC_ROCK = "Radio Argovia Classic Rock";
	public static final String RADIO_105 = "Radio 105";
	public static final String RADIO_FM1 = "Radio FM1";
	public static final String RADIO_ANTENNE_BAYERN = "Radio Antenne Bayern";
	public static final String RADIO_KANAL_K = "Radio Kanal K";
	public static final String RADIO_TOXIC_FM = "Radio Toxic FM";
	public static final String RADIO_X = "Radio X";
	public static final String RADIO_SEEFUNK = "Radio Seefunk";
	public static final String RADIO_INSIDE = "Radio Inside";
	public static final String RADIO_FREIBURG = "Radio Freiburg";
	public static final String RADIO_LIECHSTENSTEIN = "Radio Liechtenstein";
	public static final String RADIO_STADTFILTER = "Radio Stadtfilter";
	public static final String RADIO_LORA = "Radio LoRa";
	public static final String RADIO_KAISEREGG = "Radio Kaiseregg";
	public static final String RADIO_SUNSHINE = "Radio Sunshine";
	public static final String RADIO_DJRADIO = "Radio DJ Radio";
	public static final String RADIO_LOUNGE_RADIO = "Radio Lounge Radio";
	public static final String RADIO_SWISS_GROOVE = "Radio SwissGroove";
	public static final String RADIO_3FACH = "Radio 3fach";
	public static final String RADIO_COUNTRY_RADIO = "Radio Country Radio";
	public static final String RADIO_GLORIA = "Radio GLoria";
	public static final String RADIO_FCB_LIVE_RADIO = "Radio FC Basel Live Radio";

}