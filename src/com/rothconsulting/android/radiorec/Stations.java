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
		m.put("email", "http://www.radio32.ch/?id=257&amp;rub=55");
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
		m.put("email", "http://www.radio32.ch/?id=685&amp;rub=124");
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
				"http://80.86.200.92/ext/webcams/thumb.php?file=webcamS1.jpg&amp;width=700&amp;quality=80");
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
				"http://80.86.200.92/ext/webcams/thumb.php?file=webcamS1.jpg&amp;width=700&amp;quality=80");
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

		//
		//
		// <item>Radio DRS2</item>
		// <item>Radio DRS3</item>
		// <item>Radio DRS4 News</item>
		// <item>Radio DRS Musikwelle</item>
		// <item>Radio DRS Virus</item>
		// <item>Radio Hoch2</item>
		// <item>Radio Energy Bern</item>
		// <item>Radio Energy Z�rich</item>
		// <item>Radio Energy Swiss</item>
		// <item>Radio Energy Charts</item>
		// <item>Radio Energy Love</item>
		// <item>Radio Energy 80s</item>
		// <item>Radio Energy 90s</item>
		// <item>Radio Energy Italy</item>
		// <item>Radio Swiss Classic</item>
		// <item>Radio Swiss Jazz</item>
		// <item>Radio Swiss Pop</item>
		// <item>Radio 1</item>
		// <item>Radio Z�risee</item>
		// <item>Radio Pilatus</item>
		// <item>Radio BeO</item>
		// <item>Radio Top</item>
		// <item>Radio Top Two</item>
		// <item>Radio 20 Minuten</item>
		// <item>Radio neo 1</item>
		// <item>Radio neo zwei</item>
		// <item>Radio Basel</item>
		// <item>Radio Argovia</item>
		// <item>Radio Argovia Hitmix</item>
		// <item>Radio Argovia Classic Rock</item>
		// <item>Radio 105</item>
		// <item>Radio 105 Classics</item>
		// <item>Radio FM1</item>
		// <item>Radio FM1 Melody</item>
		// <item>Radio Antenne Bayern</item>
		// <item>Radio Antenne Chillout</item>
		// <item>Radio Antenne Rock</item>
		// <item>Radio Antenne Classic Rock</item>
		// <item>Radio Bayern 1</item>
		// <item>Radio Bayern 2</item>
		// <item>Radio Bayern 3</item>
		// <item>Radio On-3</item>
		// <item>Radio SWR1</item>
		// <item>Radio SWR2</item>
		// <item>Radio SWR3</item>
		// <item>Radio SWR4</item>
		// <item>Radio DasDing</item>
		// <item>Radio BigFM</item>
		// <item>Radio Toxic FM</item>
		// <item>Radio X</item>
		// <item>Radio Kanal K</item>
		// <item>Radio Seefunk</item>
		// <item>Radio Inside</item>
		// <item>Radio Freiburg</item>
		// <item>Radio Liechtenstein</item>
		// <item>Radio Couleur 3</item>
		// <item>Radio RFJ (Frequence Jura)</item>
		// <item>Radio Rouge FM</item>
		// <item>Radio soma fm - S.o.Goa</item>
		// <item>Radio Stadtfilter</item>
		// <item>Radio LoRa</item>
		// <item>Radio Kaiseregg</item>
		// <item>Radio Sunshine</item>
		// <item>Radio Tropic</item>
		// <item>Radio DJ Radio</item>
		// <item>Radio Lounge Radio</item>
		// <item>Radio SwissGroove</item>
		// <item>Radio RSI Rete 3</item>
		// <item>Radio Rock Nation</item>
		// <item>Radio 3fach</item>
		// <item>Radio Country Radio</item>
		// <item>Radio Life Channel</item>
		// <item>Radio Gloria</item>
		// <item>Radio Gelb-Schwarz YB</item>
		// <item>Radio FC Basel Live Radio</item>
		// <item>Radio FC Z�rich</item>
		// <item>Backstage Radio</item>
		// <item>Radio Rottu Oberwallis</item>
		// <item>Radio Monte Carlo</item>
		// <item>Radio RaSa</item>
		// <item>Radio Blind Power</item>
		// <item>Radio Sine-Music</item>
		// <item>Radio Kingston Hot</item>
		// <item>Radio Central</item>
		// <item>Radio Central Rock</item>
		// <item>Radio Central Swiss</item>
		// <item>Radio Eviva</item>
		// <item>Radio Grischa</item>
		// <item>Radio sunshine-live</item>
		// <item>Radio �1 - ORF</item>
		// <item>Radio �3 - ORF</item>
		// <item>Radio FM4 - ORF</item>
		// <item>Radio Tirol - ORF</item>
		// <item>Radio Wien - ORF</item>
		// <item>Radio K�rnten - ORF</item>
		// <item>Radio Salzburg - ORF</item>
		// <item>Radio Nieder�sterreich - ORF</item>
		// <item>BuureRadio</item>
		// <item>Radio Industrie</item>
		// <item>Radio Audioasyl</item>
		// <item>Piratenradio</item>
		// <item>Radio 21</item>
		// <item>Radio Allg�uHIT</item>
		// <item>Radio Wave</item>
		// <item>Radio Helsinki Graz</item>
		// <item>Radio Soundportal</item>
		// <item>Radio 1Live</item>
		// <item>Radio Top 100 Station</item>
		// <item>Radio Rockstation</item>
		// <item>Radio SkyLive</item>
		// <item>Radio TranceRadio</item>
		// <item>Radio 7</item>
		// <item>Radio Sal�</item>
		// <item>Radio FFH</item>
		// <item>Radio rt1 S�dschwaben</item>
		// <item>Radio HR3</item>
		// <item>Radio Star FM</item>
		// <item>Radio Star FM From Hell</item>
		// <item>Radio Ibiza Sonica</item>
		// <item>Radio Amnesia</item>
		// <item>Radio Blue Marlin Ibiza</item>
		// <item>Radio Regenbogen</item>
		// <item>Radio Fantasy Dance FM</item>
		// <item>Radio Tirol (IT)</item>
		// <item>Delta Radio</item>
		// <item>Radio NDR 1</item>
		// <item>Radio NDR 2</item>
		// <item>Radio N-Joy</item>
		// <item>Radio Stephansdom</item>
		// <item>Celtic Music Radio</item>
		// <item>Radio Mittelalterklang</item>
		// <item>Radio Secrettube</item>
		// <item>Radio Gong N�rnberg</item>
		// <item>Radio Paloma</item>
		// <item>Radio eins rbb</item>
		// <item>Radio Dreyeckland</item>
		// <item>Radio Novaplanet</item>
		//
		//

		// <item></item>
		// <item></item>
		// <item>http://glb-stream12.streamserver.ch/2/drs2/mp3_128</item>
		// <item>http://glb-stream12.streamserver.ch/2/drs3/mp3_128</item>
		// <item>http://glb-stream12.streamserver.ch/2/drs4news/mp3_128</item>
		// <item>http://glb-stream12.streamserver.ch/2/drsmw/mp3_128</item>
		// <item>http://glb-stream12.streamserver.ch/2/drsvirus/mp3_128</item>
		// <item>http://stream1.radiohoch2.ch:80/web</item>
		// <item>http://energybern.ice.infomaniak.ch:80/energybern-high.mp3</item>
		// <item>http://energyzuerich.ice.infomaniak.ch:80/energyzuerich-high.mp3</item>
		// <item>http://energyswiss.ice.infomaniak.ch:80/energyswiss-high.mp3</item>
		// <item>http://broadcast.infomaniak.net/energycharts-high.mp3</item>
		// <item>http://broadcast.infomaniak.net:80/energylove-high.mp3</item>
		// <item>http://energy80s.ice.infomaniak.ch:80/energy80s-high.mp3</item>
		// <item>http://energy90s.ice.infomaniak.ch:80/energy90s-high.mp3</item>
		// <item>http://energyitaly.ice.infomaniak.ch:80/energyitaly-high.mp3</item>
		// <item>http://glb-stream12.streamserver.ch/2/rsc_de/mp3_128</item>
		// <item>http://glb-stream12.streamserver.ch/2/rsj/mp3_128</item>
		// <item>http://glb-stream12.streamserver.ch/2/rsp/mp3_128</item>
		// <item>http://stream.radio1.ch:8000/128k</item>
		// <item>http://mp3.radio.ch:80/radiozuerisee128k</item>
		// <item>http://media.radiopilatus.ch:80/pilatus128</item>
		// <item>http://87.237.169.123:8000/radiobeo.mp3</item>
		// <item>http://icecast.radiotop.ch:8000/radiotop</item>
		// <item>http://icecast.radiotop.ch:8000/toptwo</item>
		// <item>http://ch4.streamhosting.ch:8080/20min_ger</item>
		// <item>http://stream-02.neo1.ch:80/neo1</item>
		// <item>http://stream-02.neo1.ch:80/neo2</item>
		// <item>http://stream3.radiobasel.ch:8000/rb13</item>
		// <item>http://argovia.mp3.green.ch</item>
		// <item>http://shoutcast.argovia.ch:8095</item>
		// <item>http://shoutcast.argovia.ch:8090</item>
		// <item>http://bas-flu-stream-01.datacomm.ch:8000/105fm</item>
		// <item>http://bas-flu-stream-01.datacomm.ch:8000/105classics</item>
		// <item>http://radiofm1.ice.infomaniak.ch:80/radiofm1-128.mp3</item>
		// <item>http://fm1melody.ice.infomaniak.ch/fm1melody-128.mp3</item>
		// <item>http://mp3.webradio.antenne.de:80</item>
		// <item>http://mp3channels.webradio.antenne.de/chillout</item>
		// <item>http://mp3.webradio.rockantenne.de:80</item>
		// <item>http://mp3channels.webradio.antenne.de/classic-rock-live</item>
		// <item>http://gffstream.ic.llnwd.net/stream/gffstream_w10b</item>
		// <item>http://gffstream.ic.llnwd.net/stream/gffstream_w11b</item>
		// <item>http://gffstream.ic.llnwd.net/stream/gffstream_w12b</item>
		// <item>http://gffstream.ic.llnwd.net/stream/gffstream_w9a</item>
		// <item>http://swr.ic.llnwd.net/stream/swr_mp3_m_swr1bwb</item>
		// <item>http://swr.ic.llnwd.net/stream/swr_mp3_m_swr2b</item>
		// <item>http://swr.ic.llnwd.net/stream/swr_mp3_m_swr3b</item>
		// <item>http://swr.ic.llnwd.net/stream/swr_mp3_m_swr4bwb</item>
		// <item>http://swr.ic.llnwd.net/stream/swr_mp3_m_dasdinga</item>
		// <item>http://srv05.bigstreams.de/bigfm-mp3-96</item>
		// <item>http://toxicfm.ice.infomaniak.ch:80/toxicfm-128.mp3</item>
		// <item>http://mp3.radiox.ch/standard.mp3</item>
		// <item>http://82.197.165.150</item>
		// <item>http://webradio.radio-seefunk.de:8000</item>
		// <item>http://rs43.stream24.org:8310/;stream0.238134464714676.mp3</item>
		// <item>http://radiofreiburg.ice.infomaniak.ch:80/radiofreiburg-high.mp3</item>
		// <item>http://live.radiol.li:8000/live</item>
		// <item>http://broadcast.infomaniak.ch/rsr-couleur3-high.mp3</item>
		// <item>http://broadcast.infomaniak.net/rfj-high.mp3</item>
		// <item>http://broadcast.infomaniak.ch/rougefm-high.mp3</item>
		// <item>http://ice.somafm.com/suburbsofgoa</item>
		// <item>http://stream.stadtfilter.net:8000/</item>
		// <item>http://h1788467.stratoserver.net:8100</item>
		// <item>http://kaiseregg.ice.infomaniak.ch/stream128k.mp3</item>
		// <item>http://mp3stream.sunshine.ch:80</item>
		// <item>http://tropic.ice.infomaniak.ch:80/tropic-high.mp3</item>
		// <item>http://streamplus16.leonex.de:17130</item>
		// <item>http://ch3.streamhosting.ch:8080</item>
		// <item>http://server4.digital-webstream.de:13600</item>
		// <item>http://glb-stream12.streamserver.ch/1/retetre/mp3_128</item>
		// <item>http://105-stream-02.datacomm.ch:8000/rocknation</item>
		// <item>http://94.126.18.195:8000</item>
		// <item>http://rs3.radiostreamer.com:9330</item>
		// <item>http://mp3.lifechannel.ch/LC128</item>
		// <item>http://live-two.dmd2.ch:8080/radiogloria_lo</item>
		// <item>http://radiogelb-schwarz.ice.infomaniak.ch:80/radiogelbschwarz.mp3</item>
		// <item>http://195.49.27.25:8000</item>
		// <item>http://stream.fcz.ch:8000/fcz01</item>
		// <item>http://broadcast.infomaniak.ch/backstageradio-high.mp3</item>
		// <item>http://stream.rro.ch/shoutcast/radiostation2.stream/playlist.m3u8</item>
		// <item>http://212.40.13.22:11000</item>
		// <item>http://s7.pop-stream.de:7830</item>
		// <item>http://live-two.dmd2.ch:8080/rbp_hi</item>
		// <item>http://stream.laut.fm/sine-music</item>
		// <item>http://www.kingstonhot.de/shows/</item>
		// <item>http://central128.mp3.green.ch</item>
		// <item>http://sp7128.mp3.green.ch</item>
		// <item>http://sp3128.mp3.green.ch</item>
		// <item>http://eviva128.mp3.green.ch</item>
		// <item>http://grischa.mp3.green.ch</item>
		// <item>http://62.27.47.15/sunshinelive/livestream.mp3</item>
		// <item>http://mp3stream3.apasf.apa.at:8000</item>
		// <item>http://mp3stream7.apasf.apa.at:8000</item>
		// <item>http://mp3stream1.apasf.apa.at:8000</item>
		// <item>http://mp3stream2.apasf.apa.at:8000</item>
		// <item>http://194.232.200.145:8000</item>
		// <item>http://194.232.200.159:8000</item>
		// <item>http://194.232.200.147:8000</item>
		// <item>http://194.232.200.147:8000</item>
		// <item>http://live-three.dmd2.ch</item>
		// <item>http://96.31.83.87:8063</item>
		// <item>http://stream.audioasyl.net/audioasyl</item>
		// <item>http://rs1.stream24.org:8460/stream</item>
		// <item>http://rs3.stream24.org:8690</item>
		// <item>http://81.169.132.81:7000/dsl</item>
		// <item>http://212.83.60.57:2100</item>
		// <item>http://live.helsinki.at:8088/live128.mp3</item>
		// <item>http://netradio01.soundportal.at:8100/live160</item>
		// <item>http://1live.akacast.akamaistream.net/7/706/119434/v1/gnl.akacast.akamaistream.net/1live</item>
		// <item>http://87.230.101.11:8000</item>
		// <item>http://rs11.stream24.org:8130</item>
		// <item>http://s10.pop-stream.de:10242</item>
		// <item>http://stream3144.init7.net</item>
		// <item>http://stream.hoerradar.de:80/mp3-radio7-128</item>
		// <item>http://62.27.26.45/radiosalue/livestream64m.mp3</item>
		// <item>http://mp3.ffh.de/radioffh/hqlivestream.mp3</item>
		// <item>http://62.27.44.59/rt1suedschwaben/livestream.mp3?token=</item>
		// <item>http://gffstream.ic.llnwd.net/stream/gffstream_mp3_w69b</item>
		// <item>http://87.230.53.43:8004</item>
		// <item>http://87.230.53.43:7000</item>
		// <item>http://stream.wft.es:7005</item>
		// <item>http://stream.estudiosonico.com:7015</item>
		// <item>http://89.238.146.142:8635</item>
		// <item>http://217.151.151.91:80/live</item>
		// <item>http://dancefmwebradio.de:8000/dsl_stream.mp3</item>
		// <item>http://str2.creacast.com/radiotirol_a</item>
		// <item>http://stream.hoerradar.de/deltaradio128</item>
		// <item>http://ndrstream.ic.llnwd.net/stream/ndrstream_ndr1wellenord_hi_mp3</item>
		// <item>http://ndrstream.ic.llnwd.net/stream/ndrstream_ndr2_hi_mp3</item>
		// <item>http://ndrstream.ic.llnwd.net/stream/ndrstream_n-joy_hi_mp3</item>
		// <item>http://srvhost24.serverhosting.apa.net:8000/rsdstream128</item>
		// <item>http://173.192.224.123:8214</item>
		// <item>http://62.75.216.154:8050</item>
		// <item>http://secrettube.ch:6010</item>
		// <item>http://webradio.gong971.de:8000</item>
		// <item>http://he-srv1.radiopaloma.de:80</item>
		// <item>http://rbb.ic.llnwd.net/stream/rbb_radioeins_mp3_m_a</item>
		// <item>http://85.214.250.73:8000/rdl</item>
		// <item>http://broadcast.infomaniak.ch/radionova-high.mp3</item>
		//
		//
		//
		// <item>http://www.drs2.ch</item>
		// <item>http://www.drs3.ch</item>
		// <item>http://www.drs4news.ch</item>
		// <item>http://www.drsmusikwelle.ch</item>
		// <item>http://www.virus.ch</item>
		// <item>http://www.radiohoch2.ch</item>
		// <item>http://www.energy.ch/bern</item>
		// <item>http://www.energy.ch/zuerich</item>
		// <item>http://www.energy.ch/webradios</item>
		// <item>http://www.energy.ch/webradios</item>
		// <item>http://www.energy.ch/webradios</item>
		// <item>http://www.energy.ch/webradios</item>
		// <item>http://www.energy.ch/webradios</item>
		// <item>http://www.energy.ch/webradios</item>
		// <item>http://www.radioswissclassic.ch</item>
		// <item>http://www.radioswissjazz.ch</item>
		// <item>http://www.radioswisspop.ch</item>
		// <item>http://www.radio1.ch</item>
		// <item>http://www.radio.ch</item>
		// <item>http://www.radiopilatus.ch</item>
		// <item>http://www.radiobeo.ch</item>
		// <item>http://www.toponline.ch</item>
		// <item>http://www.toponline.ch/toptwo</item>
		// <item>http://www.20min.ch/radio/radio.tmpl</item>
		// <item>http://www.neo1.ch</item>
		// <item>http://www.neozwei.ch</item>
		// <item>http://www.radiobasel.ch</item>
		// <item>http://www.argovia.ch</item>
		// <item>http://www.argovia.ch</item>
		// <item>http://www.argovia.ch</item>
		// <item>http://www.105.ch</item>
		// <item>http://www.105classics.ch</item>
		// <item>http://www.radiofm1.ch</item>
		// <item>http://www.fm1melody.ch</item>
		// <item>http://www.antenne.de</item>
		// <item>http://www.antenne.de</item>
		// <item>http://www.rockantenne.de</item>
		// <item>http://www.antenne.de</item>
		// <item>http://www.bayern1.de</item>
		// <item>http://www.bayern2.de</item>
		// <item>http://www.bayern3.de</item>
		// <item>http://www.on3.de</item>
		// <item>http://www.swr1.de</item>
		// <item>http://www.swr2.de</item>
		// <item>http://www.swr3.de</item>
		// <item>http://www.swr4.de</item>
		// <item>http://www.dasding.de</item>
		// <item>http://www.bigfm.de</item>
		// <item>http://www.toxicfm.ch</item>
		// <item>http://www.radiox.ch</item>
		// <item>http://www.kanalk.ch</item>
		// <item>http://www.radio-seefunk.de</item>
		// <item>http://www.radioinside.ch</item>
		// <item>http://www.radiofr.ch/de</item>
		// <item>http://www.radio.li</item>
		// <item>http://www.couleur3.ch</item>
		// <item>http://www.rfj.ch</item>
		// <item>http://www.rougefm.com</item>
		// <item>http://www.somafm.com</item>
		// <item>http://www.stadtfilter.ch</item>
		// <item>http://www.lora.ch</item>
		// <item>http://www.kaiseregg.ch</item>
		// <item>http://www.sunshine.ch</item>
		// <item>http://www.radio-tropic.ch</item>
		// <item>http://www.djradio.ch</item>
		// <item>http://www.lounge-radio.com</item>
		// <item>http://www.swissgroove.ch</item>
		// <item>http://retetre.rtsi.ch</item>
		// <item>http://www.rocknation.ch</item>
		// <item>http://www.3fach.ch</item>
		// <item>http://www.countryradio.ch</item>
		// <item>http://www.lifechannel.ch</item>
		// <item>http://www.radiogloria.ch</item>
		// <item>http://www.radio-gelb-schwarz.ch</item>
		// <item>http://www.fcbliveradio.ch</item>
		// <item>http://www.fcz.ch/radio</item>
		// <item>http://www.backstageradio.ch</item>
		// <item>http://www.rro.ch</item>
		// <item>http://www.radiomontecarlo.ch</item>
		// <item>http://www.rasa.ch</item>
		// <item>http://www.radioblindpower.ch</item>
		// <item>http://www.sine-music.de</item>
		// <item>http://www.app.kingstonhot.de</item>
		// <item>http://www.radiocenral.ch</item>
		// <item>http://www.radiocenral.ch</item>
		// <item>http://www.radiocenral.ch</item>
		// <item>http://www.eviva.ch</item>
		// <item>http://www.radiogrischa.ch</item>
		// <item>http://www.sunshine-live.de</item>
		// <item>http://oe1.orf.at</item>
		// <item>http://oe3.orf.at</item>
		// <item>http://fm4.orf.at</item>
		// <item>http://tirol.orf.at</item>
		// <item>http://wien.orf.at</item>
		// <item>http://kaernten.orf.at</item>
		// <item>http://salzburg.orf.at</item>
		// <item>http://noe.orf.at</item>
		// <item>http://www.buureradio.ch</item>
		// <item>http://www.radioindustrie.ch</item>
		// <item>http://www.audioasyl.net</item>
		// <item>http://www.piratenradio.ch</item>
		// <item>http://www.radio21.ch</item>
		// <item>http://www.allgaeuhit.de</item>
		// <item>http://www.radio-wave.ch</item>
		// <item>http://www.helsinki.at</item>
		// <item>http://www.soundportal.at</item>
		// <item>http://www.einslive.de</item>
		// <item>http://www.top100station.de</item>
		// <item>http://www.rockstation.ch</item>
		// <item>http://www.skylive.ch</item>
		// <item>http://www.tranceradio.ch</item>
		// <item>http://www.radio7.de</item>
		// <item>http://www.radiosalue.de</item>
		// <item>http://www.ffh.de</item>
		// <item>http://www.rt1-suedschwaben.de</item>
		// <item>http://www.hr3.de</item>
		// <item>http://www.starfm.de/nuernberg</item>
		// <item>http://www.starfm.de/berlin</item>
		// <item>http://www.ibizasonica.com</item>
		// <item>http://www.amnesia.es</item>
		// <item>http://www.bluemarlinibiza.com</item>
		// <item>http://www.regenbogen.de</item>
		// <item>http://www.fantasy967.de</item>
		// <item>http://www.radiotirol.it</item>
		// <item>http://www.deltaradio.ch</item>
		// <item>http://www.ndr1.de</item>
		// <item>http://www.ndr2.de</item>
		// <item>http://www.n-joy.de</item>
		// <item>http://www.radiostephansdom.at</item>
		// <item>http://www.celticmusicradio.com</item>
		// <item>http://www.mittelalterklang.de</item>
		// <item>http://radio.secrettube.ch</item>
		// <item>http://www.gong971.de</item>
		// <item>http://www.radiopaloma.de</item>
		// <item>http://www.radioeins.de</item>
		// <item>http://www.rdl.de</item>
		// <item>http://www.novaplanet.com</item>
		//
		//
		//
		// <item></item>
		// <item>http://www.drs1.ch/import/webcam/drs1/webdrs1a.jpg</item>
		// <item>http://www.drs1.ch/import/webcam/drs1/webdrs1d.jpg</item>
		// <item>http://www.drs1.ch/import/webcam/drs1/webdrs1d.jpg</item>
		// <item></item>
		// <item>http://www.drs3.ch/import/webcam/drs3/webdrs3a.jpg</item>
		// <item></item>
		// <item>http://www.drsmusikwelle.ch/import/webcam/drsmw/webdrsmwb.jpg</item>
		// <item>http://www.drs.ch/import/webcam/virus/virus2.jpg?1304112781446</item>
		// <item>http://webcam.radiohoch2.ch/webcam_big.php</item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item>http://live.radiozurisee.divio.ch//media/external/data_upload/CAM1/SR1_CAM1.jpg</item>
		// <item></item>
		// <item>http://www.radiobeo.ch/webcam/current.jpg</item>
		// <item>http://www.toponline.ch/webcam/cam_640.jpg</item>
		// <item>http://www.toponline.ch/webcam/cam_640.jpg</item>
		// <item></item>
		// <item>http://www.neo1.ch/index.php?eID=tx_cms_showpic&amp;file=fileadmin%2Fuser_upload%2Fwebcam%2Fwebcam.jpg&amp;md5=94dde500b68cb331ee6493b0e7f2604282c29723&amp;parameters[0]=YTo0OntzOjU6IndpZHRoIjtzOjM6IjYwMCI7czo2OiJoZWlnaHQiO3M6MzoiNjAw&amp;parameters[1]=IjtzOjc6ImJvZHlUYWciO3M6NjoiPGJvZHk%2BIjtzOjQ6IndyYXAiO3M6MzU6Ijxh&amp;parameters[2]=IGhyZWY9ImphdmFzY3JpcHQ6Y2xvc2UoKTsiPnw8L2E%2BIjt9</item>
		// <item>http://www.neo1.ch/index.php?eID=tx_cms_showpic&amp;file=fileadmin%2Fuser_upload%2Fwebcam%2Fwebcam.jpg&amp;md5=94dde500b68cb331ee6493b0e7f2604282c29723&amp;parameters[0]=YTo0OntzOjU6IndpZHRoIjtzOjM6IjYwMCI7czo2OiJoZWlnaHQiO3M6MzoiNjAw&amp;parameters[1]=IjtzOjc6ImJvZHlUYWciO3M6NjoiPGJvZHk%2BIjtzOjQ6IndyYXAiO3M6MzU6Ijxh&amp;parameters[2]=IGhyZWY9ImphdmFzY3JpcHQ6Y2xvc2UoKTsiPnw8L2E%2BIjt9</item>
		// <item>http://radiobasel.ch/webcam/studio2.jpg</item>
		// <item>http://www.argovia.ch/live/</item>
		// <item>http://www.argovia.ch/live/</item>
		// <item>http://www.argovia.ch/live/</item>
		// <item></item>
		// <item></item>
		// <item>http://www.radiofm1.ch/index.php?article_id=323</item>
		// <item></item>
		// <item>http://www.antenne.de/antenne/incl/php/thumb.php?image=/webcam/studiocam-b.jpg&amp;y=360&amp;resize=1&amp;thumb_path=/webcam/thumb/440x360</item>
		// <item>http://www.antenne.de/antenne/incl/php/thumb.php?image=/webcam/studiocam-b.jpg&amp;y=360&amp;resize=1&amp;thumb_path=/webcam/thumb/440x360</item>
		// <item>http://www.antenne.de/antenne/incl/php/thumb.php?image=/webcam/studiocam-b.jpg&amp;y=360&amp;resize=1&amp;thumb_path=/webcam/thumb/440x360</item>
		// <item>http://www.antenne.de/antenne/incl/php/thumb.php?image=/webcam/studiocam-b.jpg&amp;y=360&amp;resize=1&amp;thumb_path=/webcam/thumb/440x360</item>
		// <item>http://www.br-online.de/content/import/webcams/bayern1/cam2_pult.jpg</item>
		// <item></item>
		// <item>http://www.br-online.de/content/import/webcams/bayern3/cam7_gast.jpg</item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item>http://www.swr3.de/studiocam</item>
		// <item></item>
		// <item>http://www.dasding.de/ext/webcam/webcam770.php?cam=2</item>
		// <item>http://static.bigfm.de/webcam/cam6/webcam06.jpg</item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item>http://www.radioinside.ch/~kamera/studio1.jpg</item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item>http://www.sunshine.ch/data/webcam/studio2.jpg</item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item>http://webcam.3fach.ch:8080/axis-cgi/mjpg/video.cgi</item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item>http://data.bkw-fmb.ch/opd/camPics/Stade-de-suisse.jpg</item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item>http://www.radiocentral.ch/data/WebCam/brunnen.jpg</item>
		// <item>http://www.radiocentral.ch/data/WebCam/brunnen.jpg</item>
		// <item>http://www.radiocentral.ch/data/WebCam/brunnen.jpg</item>
		// <item>http://www.radiocentral.ch/data/WebCam/brunnen.jpg</item>
		// <item>http://www.radiogrischa.ch/sites/default/files/webcams/studio_1.jpg</item>
		// <item></item>
		// <item></item>
		// <item>http://onapp1.orf.at/webcam/oe3/cam2_oe3.jpg</item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item>http://www.allgaeuhit.de/webcam.jpg</item>
		// <item>http://www.radiowave.ch/studio.jpg</item>
		// <item></item>
		// <item>http://soundportal.at/service/webcam</item>
		// <item>http://www.einslive.de/webcam/studio_a_1.jpg</item>
		// <item></item>
		// <item></item>
		// <item>http://www.skylive.ch/viewpage.php?page_id=6</item>
		// <item></item>
		// <item></item>
		// <item>http://www.salue.de/img2/studio.jpg</item>
		// <item>http://resource.ffh.de/webcams/ffh/cam1-fullsize.jpg</item>
		// <item></item>
		// <item>http://www.hrfoto.dunkel.de/webcams/hr3/studio2.jpg</item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item>http://www.regenbogen.de/content/html/shared/webcams/index.html</item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		// <item></item>
		//
		//
		//
		// <item>http://www.drs1.ch/www/de/drs1/kontakt/mail-ins-studio.html</item>
		// <item>http://www.drs1.ch/www/de/drs1/kontakt/mail-ins-studio.html</item>
		// <item>http://www.drs1.ch/www/de/drs1/kontakt/mail-ins-studio.html</item>
		// <item>http://www.drs1.ch/www/de/drs1/kontakt/mail-ins-studio.html</item>
		// <item>http://www.drs2.ch/www/de/drs2/kontakt/kundendienst.html</item>
		// <item>http://www.drs3.ch/www/de/drs3/kontakt/mail-ins-studio.html</item>
		// <item>http://www.drs4news.ch/www/de/drs4/kontakt/mail-ins-studio.html</item>
		// <item>http://www.drsmusikwelle.ch/www//de/drsmusikwelle/kontakt/mail-ins-studio.html</item>
		// <item>http://www.virus.ch/virus/kontakt</item>
		// <item>http://radiohoch2.ch/contact</item>
		// <item>http://www.energy.ch/bern/inside/kontakt</item>
		// <item>http://www.energy.ch/zurich/inside/kontakt</item>
		// <item>http://www.energy.ch/webradios</item>
		// <item>http://www.energy.ch/webradios</item>
		// <item>http://www.energy.ch/webradios</item>
		// <item>http://www.energy.ch/webradios</item>
		// <item>http://www.energy.ch/webradios</item>
		// <item>http://www.energy.ch/webradios</item>
		// <item>http://www.radioswissclassic.ch/de/feedback</item>
		// <item>http://www.radioswissjazz.ch/de/feedback</item>
		// <item>http://www.radioswisspop.ch/de/feedback</item>
		// <item>studio@radio1.ch</item>
		// <item>http://www.radio.ch/de/kontakt/mail-ins-studio/</item>
		// <item>http://mobile.radiopilatus.ch/kontakt.php</item>
		// <item>sendestudio@radiobeo.ch</item>
		// <item>studiofeedback@radiotop.ch</item>
		// <item>studiofeedback@radiotop.ch</item>
		// <item>feedback@20minuten.ch</item>
		// <item>http://www.neo1.ch/studiomail.html</item>
		// <item>http://www.neozwei.ch/index.php?id=314</item>
		// <item>http://radiobasel.ch/kontakt?cid=Studio</item>
		// <item>http://www.argovia.ch/miniwin-jukes.phpch</item>
		// <item>http://www.argovia.ch/miniwin-jukes.php</item>
		// <item>http://www.argovia.ch/miniwin-jukes.php</item>
		// <item>http://www.105.ch/channels/105-fm/?article=65</item>
		// <item>http://www.105.ch/channels/radio-105-classic/?article=65</item>
		// <item>http://www.radiofm1.ch/index.php?article_id=285</item>
		// <item>http://www.fm1melody.ch/index.php?article_id=20</item>
		// <item>http://www.antenne.de/antenne/radio/kontakt/index.php</item>
		// <item>http://www.antenne.de/antenne/radio/kontakt/index.php</item>
		// <item>http://www.rockantenne.de/mailinsstudio</item>
		// <item>http://www.antenne.de/antenne/radio/kontakt/index.php</item>
		// <item>http://www.br-online.de/bayern1/kontakt/kontakt-bayern1-mail-ID1203955361656.xml?_requestid=168772</item>
		// <item>http://www.br-online.de/bayern2/land-und-leute/bayern-2-kontakt-service-ID1198255138342.xml?_requestid=3320</item>
		// <item>http://www.br-online.de/bayern3/kontakt/kontakt-impressum-copyright-ID1195052108234.xml?_requestid=130461</item>
		// <item>http://on3.de/focus/11/on3-radio#/main/9/kontakt</item>
		// <item>http://www.swr.de/swr1/bw/programm/-/id=446250/nid=446250/did=1420380/cf=42/1gqznk3/index.html</item>
		// <item>http://www.swr.de/swr2/-/id=7576/vv=email/nid=7576/did=661414/wfs5qs/index.html</item>
		// <item>http://www.swr3.de/-/id=421906/did=216928/a91pr1/index.html</item>
		// <item>http://www.swr.de/swr4/bw/-/id=233374/vv=email/nid=233374/did=259762/1gqvrz9/index.html</item>
		// <item>http://www.dasding.de/ext/dingmessage/index.php</item>
		// <item>http://www.bigfm.de/content/html/shared/studiomail/index.html</item>
		// <item>studio@toxic.fm</item>
		// <item>http://www.radiox.ch/service/wuko</item>
		// <item>http://kanalk.ch/Infopool/Kontakt/tabid/166/Default.aspx</item>
		// <item>http://www.radio-seefunk.de/default.aspx?ID=11550</item>
		// <item>http://www.radioinside.ch/index.php/studiomail</item>
		// <item>http://www.radiofr.ch/de/kontakt.html</item>
		// <item>http://www.radio.li/hoererservice/mein-musikwunsch.html?type=3</item>
		// <item>http://www.rsr.ch/#/corporate/contact/?contact=couleur3</item>
		// <item>http://www.rfj.ch/rfj/Radio/Contact.html</item>
		// <item>http://www.rougefm.com/radio/contact.php</item>
		// <item>http://somafm.com/contact/</item>
		// <item>http://www.stadtfilter.ch/StadtfilterKontakt/Kontakt</item>
		// <item>http://lora.ch/ueberuns/kontakt</item>
		// <item>http://www.kaiseregg.ch/index.php?option=com_contact&amp;view=contact&amp;id=1&amp;Itemid=7</item>
		// <item>http://www.sunshine.ch/dynasite.cfm?dsmid=98592</item>
		// <item>http://radio-tropic.ch/contact</item>
		// <item>http://www.djradio.ch/kontakt</item>
		// <item>http://www.lounge-radio.com/index.php?option=com_contact&amp;Itemid=42</item>
		// <item>http://www.swissgroove.ch/index.php?content=main&amp;id=17</item>
		// <item>http://retetre.rtsi.ch/index.php?option=com_contact&amp;catid=12&amp;Itemid=30</item>
		// <item>info@rocknation.ch</item>
		// <item>http://www.3fach.ch/index.php?id=2</item>
		// <item>http://www.countryradio.ch/index.php?id=22</item>
		// <item>http://www.lifechannel.ch/fenster_zum_sonntag-feedback___kontakt.html</item>
		// <item>http://www.radiogloria.ch/kontakt.html</item>
		// <item>rgs@im-stadion.ch</item>
		// <item>http://www.fcbliveradio.ch/index.php?option=com_contact&amp;view=category&amp;catid=12&amp;Itemid=53</item>
		// <item>http://www.fcz.ch/tv/fcz_radio_kontakt.htm</item>
		// <item>http://www.facebook.com/apps/application.php?id=194482473899008</item>
		// <item>http://www.rro.ch/cms/?page=kontakt</item>
		// <item>info@radiomontecarlo.ch</item>
		// <item>http://www.rasa.ch/feedback.php</item>
		// <item>http://www.radioblindpower.ch/studio/studiomail.php</item>
		// <item>http://www.sine-music.de/?page_id=679</item>
		// <item>http://34073.speechbox.de</item>
		// <item>http://www.radiocentral.ch/dynasite.cfm?dsmid=77268</item>
		// <item>http://www.radiocentral.ch/dynasite.cfm?dsmid=77268</item>
		// <item>http://www.radiocentral.ch/dynasite.cfm?dsmid=77268</item>
		// <item>http://eviva.ch/dynasite.cfm?dsmid=78524</item>
		// <item>http://www.radiogrischa.ch/adresseanfahrt</item>
		// <item>http://www.sunshine-live.de/pop-ups/mail-ins-studio/</item>
		// <item>http://oe1.orf.at/kontakt</item>
		// <item>http://cluboe3.orf.at/song/index.php?round=24</item>
		// <item>http://our.orf.at/mailform/fm4_kontakt</item>
		// <item>publikumsservice.tirol@orf.at</item>
		// <item>899953@orf.at</item>
		// <item>service.kaernten@orf.at</item>
		// <item>publikumsservice.salzburg@orf.at</item>
		// <item>noe-publikum@orf.at</item>
		// <item>http://www.buureradio.ch/buureradio20/module/songs/searchform.asp</item>
		// <item>info@radioindustrie.ch</item>
		// <item>info@audioasyl.net</item>
		// <item>info@piratenradio.ch</item>
		// <item>http://radio21.ch/wp/?page_id=481</item>
		// <item>http://www.allgaeuhit.de/wugru.html</item>
		// <item>http://www.radio-wave.ch/cms/index.php?section=contact&amp;cmd=8</item>
		// <item>http://helsinki.at/info/kontakt</item>
		// <item>http://soundportal.at/service/musikwunsch/</item>
		// <item>http://www.einslive.de/team/kontakt/</item>
		// <item>http://mobile.top100station.de/index.php?mobilesite=20</item>
		// <item>http://www.rockstation.ch/pages/contact.html</item>
		// <item>http://skylive.ch/infusions/ls_tinychat_panel/ls_tinychat.php</item>
		// <item>http://technoradio.ch/contact.html</item>
		// <item>http://radio7.de/index.php?id=75</item>
		// <item>http://www.salue.de/inside/kontakt.phtml</item>
		// <item>http://www.ffh.de/ffh-welt/kontakt.html</item>
		// <item>http://www.rt1-suedschwaben.de/index.php?pageid=174&amp;puid=7</item>
		// <item>http://www.hr-online.de/website/radio/hr3/index.jsp?rubrik=3532</item>
		// <item>http://www.starfm.de/nuernberg/kontakt.php</item>
		// <item>http://www.starfm.de/berlin/kontakt.php</item>
		// <item>https://www.facebook.com/ibizasonicaradio</item>
		// <item>http://www.amnesia.es/webapp/contact</item>
		// <item>http://www.bluemarlinibiza.com/contact/contact</item>
		// <item>http://www.regenbogen.de/views/service-navigation/kontakt/e-mail.html</item>
		// <item>http://www.fantasy967.de/index.php?id=88</item>
		// <item>http://www.radiotirol.it/index.php?id=18</item>
		// <item>http://www.deltaradio.de/delta/kontakt/mail/index.html</item>
		// <item>http://www.ndr.de/wellenord/service/mail_ins_studio/index.html</item>
		// <item>http://www.ndr.de/ndr2/musik/musikwunsch/index.html</item>
		// <item>http://www.n-joy.de/kontakt377.html</item>
		// <item>http://www.radiostephansdom.at/kontakt</item>
		// <item>http://www.celticradio.net/php/service_mod.php?type=contact</item>
		// <item>administration@mittelalterklang.de</item>
		// <item>webmaster@secrettube.ch</item>
		// <item>http://www.gong971.de/gong971.de/kontakt/index.php</item>
		// <item>http://www.radiopaloma.de/kontakt.html</item>
		// <item>http://www.radioeins.de/kontakt/index.html</item>
		// <item>http://www.rdl.de/index.php?option=com_content&amp;view=article&amp;id=8825&amp;Itemid=466</item>
		// <item>http://www.novaplanet.com/content/contact</item>

		return stationList;
	}
}
