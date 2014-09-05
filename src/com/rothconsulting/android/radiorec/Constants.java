package com.rothconsulting.android.radiorec;

import java.util.ArrayList;
import java.util.List;

public class Constants {

	/**
	 * Shoutcast streams work only with Andorid 2.2 an later. Shoutcast streams will be ignored for older Android levels.
	 * 
	 * @return
	 */
	public static final List<String> getIgnoreListShoutcast() {
		List<String> ignoreList = new ArrayList<String>();
		ignoreList.add(Stations.RADIO_KANAL_K);
		ignoreList.add(Stations.RADIO_SEEFUNK);
		ignoreList.add(Stations.RADIO_INSIDE);
		ignoreList.add(Stations.RADIO_STADTFILTER);
		ignoreList.add(Stations.RADIO_SUNSHINE);
		ignoreList.add(Stations.RADIO_DJRADIO);
		ignoreList.add(Stations.RADIO_LOUNGE_RADIO);
		ignoreList.add(Stations.RADIO_COUNTRY_RADIO);
		ignoreList.add(Stations.RADIO_GELB_SCHWARZ);
		ignoreList.add(Stations.RADIO_FCB_LIVE_RADIO);
		ignoreList.add(Stations.RADIO_FC_ZUERICH);
		ignoreList.add(Stations.RADIO_RASA);
		ignoreList.add(Stations.RADIO_CENTRAL);
		ignoreList.add(Stations.RADIO_CENTRAL_ROCK);
		ignoreList.add(Stations.RADIO_CENTRAL_SWISS);
		ignoreList.add(Stations.RADIO_CENTRAL_LAENDLER);
		ignoreList.add(Stations.RADIO_CENTRAL_COUNTRY);
		ignoreList.add(Stations.RADIO_CENTRAL_DANCE);
		ignoreList.add(Stations.RADIO_CENTRAL_CHARTS);
		ignoreList.add(Stations.RADIO_CENTRAL_HIP_HOP);
		ignoreList.add(Stations.RADIO_CENTRAL_JAZZ);
		ignoreList.add(Stations.RADIO_CENTRAL_FUNK);
		ignoreList.add(Stations.RADIO_CENTRAL_SPEZIAL);
		ignoreList.add(Stations.RADIO_EVIVA);
		ignoreList.add(Stations.RADIO_OE1);
		ignoreList.add(Stations.RADIO_OE3);
		ignoreList.add(Stations.RADIO_FM4);
		ignoreList.add(Stations.RADIO_TIROL);
		ignoreList.add(Stations.RADIO_WIEN);
		ignoreList.add(Stations.RADIO_KAERNTEN);
		ignoreList.add(Stations.RADIO_SALZBURG);
		ignoreList.add(Stations.RADIO_OBEROESTERREICH);
		ignoreList.add(Stations.RADIO_NIEDEROESTERREICH);
		ignoreList.add(Stations.RADIO_ROCK_STATION);
		ignoreList.add(Stations.RADIO_STAR_FM);
		ignoreList.add(Stations.RADIO_STAR_FM_FROM_HELL);
		ignoreList.add(Stations.RADIO_IBIZA_SONICA);
		ignoreList.add(Stations.RADIO_BLUE_MARLIN);
		ignoreList.add(Stations.RADIO_LEGENDE);
		ignoreList.add(Stations.RADIO_GONG_NUERNBERG);
		ignoreList.add(Stations.RADIO_PALOMA);
		ignoreList.add(Stations.RADIO_PULS_RADIO);
		ignoreList.add(Stations.RADIO_100_5_DAS_HITRADIO);
		ignoreList.add(Stations.RADIO_KIEPENKERL);
		ignoreList.add(Stations.RADIO_ANTENNE_STEIERMARK);
		ignoreList.add(Stations.RADIO_ANTENNE_KAERNTEN);
		ignoreList.add(Stations.RADIO_ANTENNE_TIROL);
		ignoreList.add(Stations.RADIO_ANTENNE_SALZBURG);
		ignoreList.add(Stations.RADIO_ANTENNE_VORARLBERG);
		ignoreList.add(Stations.RADIO_H1_RADIO_HITTNAU);
		ignoreList.add(Stations.RADIO_IISCHERS_RADIO);
		ignoreList.add(Stations.RADIO_ARABELLA_AT);
		ignoreList.add(Stations.RADIO_HOUSETIME_FM);
		ignoreList.add(Stations.RADIO_PLANET_FM);
		ignoreList.add(Stations.RADIO_886);
		ignoreList.add(Stations.RADIO_SHOUTED_FM);
		ignoreList.add(Stations.RADIO_SHOUTED_FM_CLUB);
		ignoreList.add(Stations.RADIO_SHOUTED_FM_ELECTRO);
		ignoreList.add(Stations.RADIO_SHOUTED_FM_BREAK);
		ignoreList.add(Stations.RADIO_SHOUTED_FM_HOUSE);
		ignoreList.add(Stations.RADIO_SHOUTED_FM_ALTERNATIVE);
		ignoreList.add(Stations.RADIO_RTL_LUXEMBOURG);
		ignoreList.add(Stations.RADIO_OTTO_FM);
		ignoreList.add(Stations.RADIO_ZWICKAU);
		ignoreList.add(Stations.RADIO_PARTYRADIO_24);
		ignoreList.add(Stations.RADIO_BALLERMANN_RADIO);
		ignoreList.add(Stations.RADIO_APOLLORADIO);
		ignoreList.add(Stations.RADIO_DEFJAY);
		ignoreList.add(Stations.RADIO_MARGHERITA);
		ignoreList.add(Stations.RADIO_IBIZA_GLOBAL_RADIO);
		ignoreList.add(Stations.RADIO_RTL_IT);
		ignoreList.add(Stations.RADIO_SOUND_CITY);
		ignoreList.add(Stations.RADIO_DANCEFOX_RADIO);
		ignoreList.add(Stations.RADIO_U1_TIROL);
		ignoreList.add(Stations.RADIO_DANCE_NATION_1);
		ignoreList.add(Stations.RADIO_CRAZY_CLASSIC);
		ignoreList.add(Stations.RADIO_CRAZY_OPERA);
		ignoreList.add(Stations.RADIO_CRAZY_SANCTUS);
		ignoreList.add(Stations.RADIO_CRAZY_JAZZ_SWING);
		ignoreList.add(Stations.RADIO_CRAZY_MODERN_JAZZ);
		ignoreList.add(Stations.RADIO_REVIVAL_KULT);
		ignoreList.add(Stations.RADIO_SWISS_MOUNTAIN_HOLIDAY);
		ignoreList.add(Stations.RADIO_JUGGLERZ_DANCEHALL);
		ignoreList.add(Stations.RADIO_SCHWANY_OBERKRAIN);
		ignoreList.add(Stations.RADIO_SCHWANY_VOLKSTUEMLICH);
		ignoreList.add(Stations.RADIO_CHARIVARI_NUERNBERG);
		ignoreList.add(Stations.RADIO_HIT_RADIO_N1);
		ignoreList.add(Stations.RADIO_HITRADIO_RTL_SACHSEN);
		ignoreList.add(Stations.RADIO_MUSICBASE_FM_DANCE);
		ignoreList.add(Stations.RADIO_FK_AUSTRIA_WIEN);
		ignoreList.add(Stations.RADIO_SK_RAPID_WIEN);
		ignoreList.add(Stations.RADIO_FC_REDBULL_SALBURG);
		ignoreList.add(Stations.RADIO_SK_PUNTIGAMER_STURM_GRAZ);
		ignoreList.add(Stations.RADIO_HOLIDAY);
		ignoreList.add(Stations.RADIO_CITTA_FUTURA);
		ignoreList.add(Stations.RADIO_MRS_905);
		ignoreList.add(Stations.RADIO_ENERGY_98_USA);
		ignoreList.add(Stations.RADIO_BASSLOVER);
		ignoreList.add(Stations.RADIO_HITSTATION_FM);
		ignoreList.add(Stations.RADIO_HITSTATION_FM_80);
		ignoreList.add(Stations.RADIO_GAY_FM);
		ignoreList.add(Stations.RADIO_91_2);
		ignoreList.add(Stations.RADIO_BURGENLAND);
		ignoreList.add(Stations.RADIO_UNO_AT);

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
		liveStreamStationList.add(Stations.RADIO_BVB_NETRADIO);
		liveStreamStationList.add(Stations.RADIO_FK_AUSTRIA_WIEN);
		liveStreamStationList.add(Stations.RADIO_SK_RAPID_WIEN);
		liveStreamStationList.add(Stations.RADIO_SK_PUNTIGAMER_STURM_GRAZ);
		liveStreamStationList.add(Stations.RADIO_FC_REDBULL_SALBURG);
		liveStreamStationList.add(Stations.RADIO_HOCKEY_FANRADIO_1);
		liveStreamStationList.add(Stations.RADIO_HOCKEY_FANRADIO_2);
		liveStreamStationList.add(Stations.RADIO_RWW_EHC_WINTERTHUR);
		return liveStreamStationList;
	}

	// Settings Keys!
	public static final String PREFERENCES_FILE = "RadioRecPrefs";
	public static final String SELECTED_STATION_INDEX_KEY = "MySelectedStationIndex";
	public static final String SELECTED_STATION_NAME_KEY = "MySelectedStationName";
	public static final String SELECTED_STATION_ICON_KEY = "MySelectedStationIcon";
	public static final String SELECTED_STATION_HOMEPAGE_KEY = "MySelectedStationHomepage";
	public static final String SELECTED_STATION_STREAM_KEY = "MySelectedStationStream";
	public static final String SELECTED_STATION_WEBCAM_KEY = "MySelectedStationWebcam";
	public static final String SELECTED_STATION_CONTACT_KEY = "MySelectedStationContact";
	public static final String ANTI_ADS_KEY = "MyAntiAdsKey";
	public static final String SD_CARD_PATH_KEY = "MySdCardPath";
	public static final String BUFFER_KEY = "MyBuffer";
	public static final String CLOSE_APP_TIMER_END_KEY = "CloseAppWhenTimerEnds";
	public static final String ROTATION_OFF_KEY = "RotationOff";

	// Settings Values!
	public static int SELECTED_STATION_INDEX_VALUE;
	public static int SELECTED_STATION_ICON_VALUE;
	public static int SELECTED_STATION_ICON_SMALL_VALUE;
	public static String SELECTED_STATION_NAME_VALUE;
	public static String URL_LIVE_STREAM_VALUE;
	public static String URL_HOMEPAGE_VALUE;
	public static String URL_WEBCAM_VALUE;
	public static String URL_CONTACT_VALUE;
	protected static String URL_SONGTICKER_VALUE;
	public static String ANTI_ADS_VALUE;
	public static String SD_CARD_PATH_VALUE;
	public static int BUFFER_VALUE;
	public static boolean CLOSE_APP_TIMER_END_VALUE;
	public static boolean ROTATION_OFF_VALUE;

	public static String ANTI_ADS_PLAY_DONATOR_VALUE = "rrplaydonso";
	public static final String DEFAULT_SD_CARD_PATH = "sdcard/RadioRec";
	public static final int DEFAULT_BUFFER = 8192;

	public static final String FROM_NOTIFICATION = "fromNotification";
	public static final int FROM_FAVOURITES = 99;

	public static final String ANALYTICS_PROPERTY_ID = "UA-38114228-1";
	public static final String AMAZON_APPLICATION_KEY = "363b43a633564737b350fc0f5c8096f0";

	public static int CURRENT_TAB = 0;

	public static final int INTENT_REQUEST_FROM_FAV = -1;

	public static final int NOTIFICATION_ID_RADIO_IS_PLAYING = -2;
	public static final int NOTIFICATION_ID_RECORDING = -3;
	public static final int NOTIFICATION_ID_ERROR_CONNECTION = -4;
	public static final int PRESS_BACK_BUTTON = -10;
	public static final int LIVE_STREAM_STATION = -20;

	public static int SPINNER_SELECTION = -100;
	public static int SPINNER_ALL_STATIONS = -200;
	public static int SPINNER_LAENDER = -400;
	public static int SPINNER_ALPHABETISCH = -500;
	public static int SPINNER_SPRACHE = -600;

	public static String FAV_OFF = "favOff";
	public static String FAV_ON = "favOn";

	public static Boolean JUNIT_TEST = Boolean.FALSE;

}