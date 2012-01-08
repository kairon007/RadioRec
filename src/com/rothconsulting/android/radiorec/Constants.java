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
		ignoreList.add(Stations.RADIO_ANTENNE_BAYERN);
		ignoreList.add(Stations.RADIO_ANTENNE_ROCK);
		ignoreList.add(Stations.RADIO_KANAL_K);
		ignoreList.add(Stations.RADIO_SEEFUNK);
		ignoreList.add(Stations.RADIO_ARGOVIA);
		ignoreList.add(Stations.RADIO_ARGOVIA_HITMIX);
		ignoreList.add(Stations.RADIO_ARGOVIA_CLASSIC_ROCK);
		ignoreList.add(Stations.RADIO_INSIDE);
		ignoreList.add(Stations.RADIO_STADTFILTER);
		ignoreList.add(Stations.RADIO_LORA);
		ignoreList.add(Stations.RADIO_KAISEREGG);
		ignoreList.add(Stations.RADIO_SUNSHINE);
		ignoreList.add(Stations.RADIO_DJRADIO);
		ignoreList.add(Stations.RADIO_LOUNGE_RADIO);
		ignoreList.add(Stations.RADIO_SWISS_GROOVE);
		ignoreList.add(Stations.RADIO_3FACH);
		ignoreList.add(Stations.RADIO_COUNTRY_RADIO);
		ignoreList.add(Stations.RADIO_GELB_SCHWARZ);
		ignoreList.add(Stations.RADIO_FCB_LIVE_RADIO);
		ignoreList.add(Stations.RADIO_FC_ZUERICH);
		ignoreList.add(Stations.RADIO_BACKSTAGE_RADIO);
		ignoreList.add(Stations.RADIO_RRO);
		ignoreList.add(Stations.RADIO_MONTE_CARLO);
		ignoreList.add(Stations.RADIO_RASA);
		ignoreList.add(Stations.RADIO_BLIND_POWER);
		ignoreList.add(Stations.RADIO_SINE_MUSIC);
		ignoreList.add(Stations.RADIO_CENTRAL);
		ignoreList.add(Stations.RADIO_CENTRAL_ROCK);
		ignoreList.add(Stations.RADIO_CENTRAL_SWISS);
		ignoreList.add(Stations.RADIO_EVIVA);
		ignoreList.add(Stations.RADIO_GRISCHA);
		ignoreList.add(Stations.RADIO_SUNSHINE_LIVE);
		ignoreList.add(Stations.RADIO_OE1);
		ignoreList.add(Stations.RADIO_OE3);
		ignoreList.add(Stations.RADIO_FM4);
		ignoreList.add(Stations.RADIO_TIROL);
		ignoreList.add(Stations.RADIO_WIEN);
		ignoreList.add(Stations.RADIO_KAERNTEN);
		ignoreList.add(Stations.RADIO_SALZBURG);
		ignoreList.add(Stations.RADIO_NIEDEROESTERREICH);
		ignoreList.add(Stations.RADIO_BUURERADIO);
		ignoreList.add(Stations.RADIO_INDUSTRIE);
		ignoreList.add(Stations.RADIO_21);
		ignoreList.add(Stations.RADIO_WAVE);
		ignoreList.add(Stations.RADIO_TOP100STATION);
		ignoreList.add(Stations.RADIO_ROCK_STATION);
		ignoreList.add(Stations.RADIO_TRANCERADIO);
		ignoreList.add(Stations.RADIO_STAR_FM);
		ignoreList.add(Stations.RADIO_STAR_FM_FROM_HELL);
		ignoreList.add(Stations.RADIO_IBIZA_SONICA);
		ignoreList.add(Stations.RADIO_AMNESIA);
		ignoreList.add(Stations.RADIO_BLUE_MARLIN);
		ignoreList.add(Stations.RADIO_TIROL_IT);
		ignoreList.add(Stations.RADIO_CELITIC_MUSIC);
		ignoreList.add(Stations.RADIO_LEGENDE);
		ignoreList.add(Stations.RADIO_SECRETTUBE);
		ignoreList.add(Stations.RADIO_GONG_NUERNBERG);
		ignoreList.add(Stations.RADIO_PALOMA);
		ignoreList.add(Stations.RADIO_DREYECKLAND);
		ignoreList.add(Stations.RADIO_PULS_RADIO);
		ignoreList.add(Stations.RADIO_OBERKRAIN);
		return ignoreList;
	}

	/**
	 * Android 2.2 has problems playing Icecast streams. They will be ignored.
	 * 
	 * @return
	 */
	public static final List<String> getIgnoreListGleichAndroid22() {
		List<String> ignoreList = new ArrayList<String>();
		ignoreList.add(Stations.RADIO_PILATUS);
		return ignoreList;
	}

	/**
	 * Only working while live broadcasting.
	 * 
	 * @return
	 */
	public static final List<String> getLiveStreamStations() {
		List<String> liveStreamStationList = new ArrayList<String>();
		liveStreamStationList.add(Stations.RADIO_GELB_SCHWARZ);
		liveStreamStationList.add(Stations.RADIO_FCB_LIVE_RADIO);
		liveStreamStationList.add(Stations.RADIO_FC_ZUERICH);
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
	protected static int THE_SELECTED_STATION_ICON_SMALL;
	protected static String THE_SELECTED_STATION_NAME;
	protected static String THE_URL_LIVE_STREAM;
	protected static String THE_URL_HOMEPAGE;
	protected static String THE_URL_WEBCAM;
	protected static String THE_URL_CONTACT;
	protected static String THE_URL_SONGTICKER;
	protected static String THE_ANTI_ADS_KEY;
	protected static String THE_SD_CARD_PATH;
	protected static int THE_BUFFER;

	public static int CURRENT_TAB = 0;

	public static final String DEFAULT_SD_CARD_PATH = "sdcard/RadioRecorder";
	public static final int DEFAULT_BUFFER = 8192;

	public static final int NOTIFICATION_ID_RADIO_IS_PLAYING = 1;
	public static final int NOTIFICATION_ID_RECORDING = 2;
	public static final int NOTIFICATION_ID_ERROR_CONNECTION = 3;
	public static final int PRESS_BACK_BUTTON = 10;
	public static final int LIVE_STREAM_STATION = 11;

	public static final int INTENT_REQUEST_FROM_FAV = 1;

	public static final String FROM_NOTIFICATION = "fromNotification";

	public static String ADMOB_PUBLISHER_ID = "a14dc9885936cd9";

	public static int SPINNER_SELECTION = -1;
	public static int SPINNER_ALL_STATIONS = -2;
	public static int SPINNER_FAVORITEN = -3;
	public static int SPINNER_LAENDER = -4;
	public static int SPINNER_STIL = -5;

}