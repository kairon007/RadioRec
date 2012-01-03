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
		ignoreList.add(RADIO_TIROL);
		ignoreList.add(RADIO_WIEN);
		ignoreList.add(RADIO_KAERNTEN);
		ignoreList.add(RADIO_SALZBURG);
		ignoreList.add(RADIO_NIEDEROESTERREICH);
		ignoreList.add(RADIO_BUURERADIO);
		ignoreList.add(RADIO_INDUSTRIE);
		ignoreList.add(RADIO_21);
		ignoreList.add(RADIO_WAVE);
		ignoreList.add(RADIO_TOP100STATION);
		ignoreList.add(RADIO_ROCK_STATION);
		ignoreList.add(RADIO_SKYLIVE);
		ignoreList.add(RADIO_TRANCERADIO);
		ignoreList.add(RADIO_STAR_FM);
		ignoreList.add(RADIO_STAR_FM_FROM_HELL);
		ignoreList.add(RADIO_IBIZA_SONICA);
		ignoreList.add(RADIO_AMNESIA);
		ignoreList.add(RADIO_BLUE_MARLIN);
		ignoreList.add(RADIO_TIROL_IT);
		ignoreList.add(RADIO_CELITIC_MUSIC);
		ignoreList.add(RADIO_MITTELALTERKLANG);
		ignoreList.add(RADIO_SECRETTUBE);
		ignoreList.add(RADIO_GONG_NUERNBERG);
		ignoreList.add(RADIO_PALOMA);
		ignoreList.add(RADIO_DREYECKLAND);
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

	public static final String RADIO_32 = "Radio 32";
	public static final String RADIO_32_GOLDIES = "Radio 32 Goldies";
	public static final String RADIO_24 = "Radio 24";
	public static final String RADIO_24_ROCK = "Radio 24 Rock";
	public static final String RADIO_RABE = "Radio RaBe";
	public static final String RADIO_CAPITAL_FM = "Capital FM";
	public static final String RADIO_DRS1 = "DRS1";
	public static final String RADIO_DRS1_AGSO = "DRS1 AG/SO";
	public static final String RADIO_DRS1_BSBL = "DRS1 BS/BL";
	public static final String RADIO_DRS1_BEFRVS = "DRS1 BE/FR/VS";
	public static final String RADIO_DRS2 = "DRS2";
	public static final String RADIO_DRS3 = "DRS3";
	public static final String RADIO_DRS4 = "DRS4 News";
	public static final String RADIO_DRS_VIRUS = "DRS Virus";
	public static final String RADIO_DRS_MUSIKWELLE = "DRS Musikwelle";
	public static final String RADIO_HOCH2 = "Radio Hoch2";
	public static final String RADIO_ENERGY_BERN = "Energy Bern";
	public static final String RADIO_ENERGY_ZUERICH = "Energy Zürich";
	public static final String RADIO_ENERGY_SWISS = "Energy Swiss";
	public static final String RADIO_ENERGY_CHARTS = "Energy Charts";
	public static final String RADIO_ENERGY_LOVE = "Energy Love";
	public static final String RADIO_ENERGY_80 = "Energy 80s";
	public static final String RADIO_ENERGY_90 = "Energy 90s";
	public static final String RADIO_ENERGY_ITALY = "Energy Italy";
	public static final String RADIO_SWISS_CLASSIC = "Swiss Classic";
	public static final String RADIO_SWISS_JAZZ = "Swiss Jazz";
	public static final String RADIO_SWISS_POP = "Swiss Pop";
	public static final String RADIO_1 = "Radio 1";
	public static final String RADIO_ZUERISEE = "Radio Zürisee";
	public static final String RADIO_PILATUS = "Radio Pilatus";
	public static final String RADIO_BEO = "Radio BeO";
	public static final String RADIO_TOP = "Top";
	public static final String RADIO_TOP_TWO = "Top Two";
	public static final String RADIO_20MIN = "20 Minuten";
	public static final String RADIO_BASEL = "Radio Basel";
	public static final String RADIO_NEO_1 = "neo 1";
	public static final String RADIO_NEO_ZWEI = "neo zwei";
	public static final String RADIO_ARGOVIA = "Argovia";
	public static final String RADIO_ARGOVIA_HITMIX = "Argovia Hitmix";
	public static final String RADIO_ARGOVIA_CLASSIC_ROCK = "Argovia Classic Rock";
	public static final String RADIO_105 = "105";
	public static final String RADIO_105_CLASSICS = "105 Classics";
	public static final String RADIO_FM1 = "FM1";
	public static final String RADIO_FM1_MELODY = "FM1 Melody";
	public static final String RADIO_ANTENNE_BAYERN = "Antenne Bayern";
	public static final String RADIO_ANTENNE_CHILLOUT = "Antenne Cillout";
	public static final String RADIO_ANTENNE_ROCK = "Antenne Rock";
	public static final String RADIO_ANTENNE_CLASSIC_ROCK = "Antenne Classic Rock";
	public static final String RADIO_KANAL_K = "Kanal K";
	public static final String RADIO_BAYERN_1 = "Bayern 1";
	public static final String RADIO_BAYERN_2 = "Bayern 2";
	public static final String RADIO_BAYERN_3 = "Bayern 3";
	public static final String RADIO_ON_3 = "On-3";
	public static final String RADIO_SWR_1 = "SWR 1";
	public static final String RADIO_SWR_2 = "SWR 2";
	public static final String RADIO_SWR_3 = "SWR 3";
	public static final String RADIO_SWR_4 = "SWR 4";
	public static final String RADIO_DAS_DING = "DasDing";
	public static final String RADIO_BIG_FM = "BigFM";
	public static final String RADIO_TOXIC_FM = "Toxic FM";
	public static final String RADIO_X = "Radio X";
	public static final String RADIO_SEEFUNK = "Seefunk";
	public static final String RADIO_INSIDE = "Inside";
	public static final String RADIO_FREIBURG = "Radio Freiburg";
	public static final String RADIO_LIECHSTENSTEIN = "Radio Liechtenstein";
	public static final String RADIO_COULEUR_3 = "Couleur  3";
	public static final String RADIO_RFJ = "RFJ - Frequence Jura";
	public static final String RADIO_ROUGE_FM = "Rouge FM";
	public static final String RADIO_SOMA_FM = "soma fm - Sound of Goa";
	public static final String RADIO_STADTFILTER = "Stadtfilter";
	public static final String RADIO_LORA = "LoRa";
	public static final String RADIO_KAISEREGG = "Kaiseregg";
	public static final String RADIO_SUNSHINE = "Sunshine";
	public static final String RADIO_TROPIC = "Tropic";
	public static final String RADIO_DJRADIO = "DJ Radio";
	public static final String RADIO_LOUNGE_RADIO = "Lounge Radio";
	public static final String RADIO_RSI_RETE_3 = "RSI Rete 3";
	public static final String RADIO_SWISS_GROOVE = "SwissGroove";
	public static final String RADIO_ROCK_NATION = "Rock Nation";
	public static final String RADIO_3FACH = "3fach";
	public static final String RADIO_COUNTRY_RADIO = "Country Radio";
	public static final String RADIO_LIFE_CHANNEL = "Life Channel";
	public static final String RADIO_GLORIA = "Radio Gloria";
	public static final String RADIO_GELB_SCHWARZ = "Gelb-Schwarz YB";
	public static final String RADIO_FCB_LIVE_RADIO = "FC Basel Live Radio";
	public static final String RADIO_FC_ZUERICH = "FC Zürich";
	public static final String RADIO_BACKSTAGE_RADIO = "Backstage Radio";
	public static final String RADIO_RRO = "Radio Rottu Oberwallis";
	public static final String RADIO_MONTE_CARLO = "Monte Carlo";
	public static final String RADIO_RASA = "Radio RaSa";
	public static final String RADIO_BLIND_POWER = "Blind Power";
	public static final String RADIO_SINE_MUSIC = "Sine-Music";
	public static final String RADIO_CENTRAL = "Central";
	public static final String RADIO_CENTRAL_ROCK = "Central Rock";
	public static final String RADIO_CENTRAL_SWISS = "Central Swiss";
	public static final String RADIO_EVIVA = "Eviva";
	public static final String RADIO_GRISCHA = "Grischa";
	public static final String RADIO_SUNSHINE_LIVE = "sunshine-live";
	public static final String RADIO_OE3 = "Ö3 - ORF";
	public static final String RADIO_OE1 = "Ö1 - ORF";
	public static final String RADIO_FM4 = "FM4 - ORF";
	public static final String RADIO_WIEN = "Wien - ORF";
	public static final String RADIO_TIROL = "Tirol - ORF";
	public static final String RADIO_KAERNTEN = "Kärnten - ORF";
	public static final String RADIO_SALZBURG = "Salzburg - ORF";
	public static final String RADIO_NIEDEROESTERREICH = "Niederösterreich - ORF";
	public static final String RADIO_BUURERADIO = "BuureRadio";
	public static final String RADIO_INDUSTRIE = "Industrie";
	public static final String RADIO_AUDIOASYL = "Audioasyl";
	public static final String RADIO_PIRATENRADIO = "Piratenradio";
	public static final String RADIO_21 = "Radio 21";
	public static final String RADIO_KINGSTONHOT = "Kingston Hot";
	public static final String RADIO_ALLGAEUHIT = "AllgäuHIT";
	public static final String RADIO_WAVE = "Wave";
	public static final String RADIO_HELSINKI_GRAZ = "Helsinki Graz";
	public static final String RADIO_SOUNDPORTAL = "Soundportal";
	public static final String RADIO_1LIVE = "1Live";
	public static final String RADIO_TOP100STATION = "Top 100 Station";
	public static final String RADIO_ROCK_STATION = "Rockstation";
	public static final String RADIO_SKYLIVE = "SkyLive";
	public static final String RADIO_TRANCERADIO = "TranceRadio";
	public static final String RADIO_7 = "Radio 7";
	public static final String RADIO_SALUE = "Salü";
	public static final String RADIO_FFH = "FFH";
	public static final String RADIO_RT1_HITRADIO = "rt1 Hitradio";
	public static final String RADIO_HR_3 = "HR 3";
	public static final String RADIO_STAR_FM = "Star FM";
	public static final String RADIO_STAR_FM_FROM_HELL = "Star FM From Hell";
	public static final String RADIO_IBIZA_SONICA = "Ibiza Sonica";
	public static final String RADIO_AMNESIA = "Amnesia";
	public static final String RADIO_BLUE_MARLIN = "Blue Marlin Ibiza";
	public static final String RADIO_REGENBOGEN = "Regenbogen";
	public static final String RADIO_FANTASY_DANCE = "Fantasy Dance FM";
	public static final String RADIO_TIROL_IT = "Radio Tirol (IT)";
	public static final String RADIO_DELTA_RADIO = "Delta Radio";
	public static final String RADIO_NDR_1 = "NDR 1";
	public static final String RADIO_NDR_2 = "NDR 2";
	public static final String RADIO_N_JOY = "N-Joy";
	public static final String RADIO_STEPHANSDOM = "Stephansdom";
	public static final String RADIO_CELITIC_MUSIC = "Celtic Music Radio";
	public static final String RADIO_MITTELALTERKLANG = "Mittelalterklang";
	public static final String RADIO_SECRETTUBE = "Secrettube";
	public static final String RADIO_GONG_NUERNBERG = "Gong Nürnberg";
	public static final String RADIO_PALOMA = "Radio Paloma";
	public static final String RADIO_EINS_RBB = "eins rbb";
	public static final String RADIO_DREYECKLAND = "Dreyeckland";
	public static final String RADIO_NOVAPLANET = "Novaplanet";
}