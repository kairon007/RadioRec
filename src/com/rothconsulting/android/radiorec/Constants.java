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
		ignoreList.add(RADIO_ANTENNE_ROCK);
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
		ignoreList.add(RADIO_GELB_SCHWARZ);
		ignoreList.add(RADIO_FCB_LIVE_RADIO);
		ignoreList.add(RADIO_FC_ZUERICH);
		ignoreList.add(RADIO_BACKSTAGE_RADIO);
		ignoreList.add(RADIO_RRO);
		ignoreList.add(RADIO_MONTE_CARLO);
		ignoreList.add(RADIO_RASA);
		ignoreList.add(RADIO_BLIND_POWER);
		ignoreList.add(RADIO_SINE_MUSIC);
		ignoreList.add(RADIO_CENTRAL);
		ignoreList.add(RADIO_CENTRAL_ROCK);
		ignoreList.add(RADIO_CENTRAL_SWISS);
		ignoreList.add(RADIO_EVIVA);
		ignoreList.add(RADIO_GRISCHA);
		ignoreList.add(RADIO_SUNSHINE_LIVE);
		ignoreList.add(RADIO_OE1);
		ignoreList.add(RADIO_OE3);
		ignoreList.add(RADIO_FM4);
		ignoreList.add(RADIO_BUURERADIO);
		ignoreList.add(RADIO_INDUSTRIE);
		ignoreList.add(RADIO_21);
		ignoreList.add(RADIO_WAVE);
		ignoreList.add(RADIO_TOP100STATION);
		ignoreList.add(RADIO_ROCKSTATION);
		ignoreList.add(RADIO_SKYLIVE);
		ignoreList.add(RADIO_TRANCERADIO);
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

	/**
	 * Only working while live broadcasting.
	 * 
	 * @return
	 */
	public static final List<String> getLiveStreamStations() {
		List<String> liveStreamStationList = new ArrayList<String>();
		liveStreamStationList.add(RADIO_GELB_SCHWARZ);
		liveStreamStationList.add(RADIO_FCB_LIVE_RADIO);
		liveStreamStationList.add(RADIO_FC_ZUERICH);
		return liveStreamStationList;
	}

	public static final String PREFERENCES_FILE = "RadioRecPrefs";
	public static final String SELECTED_STATION_INDEX = "MySelectedStationIndex";
	public static final String SELECTED_STATION_NAME = "MySelectedStationName";
	public static final String SELECTED_STATION_ICON = "MySelectedStationIcon";
	public static final String SELECTED_STATION_HOMEPAGE = "MySelectedStationHomepage";
	public static final String SELECTED_STATION_STREAM = "MySelectedStationStream";
	public static final String SELECTED_STATION_WEBCAM = "MySelectedStationWebcam";
	public static final String SELECTED_STATION_CONTACT = "MySelectedStationContact";
	public static final String ANTI_ADS_KEY = "MyAntiAdsKey";
	public static final String SD_CARD_PATH = "MySdCardPath";
	public static final String BUFFER = "MyBuffer";

	// Default Constants
	protected static int THE_SELECTED_STATION_INDEX;
	protected static int THE_SELECTED_STATION_ICON;
	protected static String THE_SELECTED_STATION_NAME;
	protected static String THE_URL_LIVE_STREAM;
	protected static String THE_URL_HOMEPAGE;
	protected static String THE_URL_WEBCAM;
	protected static String THE_URL_CONTACT;
	protected static String THE_URL_SONGTICKER;
	protected static String THE_ANTI_ADS_KEY;
	protected static String THE_SD_CARD_PATH;
	protected static int THE_BUFFER;

	public static final String DEFAULT_SD_CARD_PATH = "sdcard/RadioRecorder";
	public static final int DEFAULT_BUFFER = 8192;

	public static final int NOTIFICATION_ID_RADIO_IS_PLAYING = 1;
	public static final int NOTIFICATION_ID_RECORDING = 2;
	public static final int NOTIFICATION_ID_ERROR_CONNECTION = 3;
	public static final int PRESS_BACK_BUTTON = 10;
	public static final int LIVE_STREAM_STATION = 11;

	public static final String FROM_NOTIFICATION = "fromNotification";

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
	public static final String RADIO_ANTENNE_ROCK = "Radio Antenne Rock";
	public static final String RADIO_ANTENNE_CLASSIC_ROCK = "Radio Antenne Classic Rock";
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
	public static final String RADIO_GLORIA = "Radio Gloria";
	public static final String RADIO_GELB_SCHWARZ = "Radio Gelb-Schwarz YB";
	public static final String RADIO_FCB_LIVE_RADIO = "Radio FC Basel Live Radio";
	public static final String RADIO_FC_ZUERICH = "Radio FC Zürich";
	public static final String RADIO_BACKSTAGE_RADIO = "Backstage Radio";
	public static final String RADIO_RRO = "Radio Rottu Oberwallis";
	public static final String RADIO_MONTE_CARLO = "Radio Monte Carlo";
	public static final String RADIO_RASA = "Radio RaSa";
	public static final String RADIO_BLIND_POWER = "Radio Blind Power";
	public static final String RADIO_SINE_MUSIC = "Radio Sine-Music";
	public static final String RADIO_CENTRAL = "Radio Central";
	public static final String RADIO_CENTRAL_ROCK = "Radio Central Rock";
	public static final String RADIO_CENTRAL_SWISS = "Radio Central Swiss";
	public static final String RADIO_EVIVA = "Radio Eviva";
	public static final String RADIO_GRISCHA = "Radio Grischa";
	public static final String RADIO_SUNSHINE_LIVE = "Radio sunshine-live";
	public static final String RADIO_OE3 = "Radio Ö3 - ORF";
	public static final String RADIO_OE1 = "Radio Ö1 - ORF";
	public static final String RADIO_FM4 = "Radio FM4 - ORF";
	public static final String RADIO_BUURERADIO = "BuureRadio";
	public static final String RADIO_INDUSTRIE = "Radio Industrie";
	public static final String RADIO_21 = "Radio 21";
	public static final String RADIO_KINGSTONHOT = "Radio Kingston Hot";
	public static final String RADIO_WAVE = "Radio Wave";
	public static final String RADIO_TOP100STATION = "Radio Top 100 Station";
	public static final String RADIO_ROCKSTATION = "Radio Rockstation";
	public static final String RADIO_SKYLIVE = "Radio SkyLive";
	public static final String RADIO_TRANCERADIO = "TranceRadio";
}