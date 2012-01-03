package com.rothconsulting.android.radiorec;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.util.Log;

public class WebTool {

	private static final String TAG = "WebTool";

	/**
	 * 
	 * @param radioStation
	 * @return
	 */
	protected String getSongticker(String radioStation, String urlHomepage) {

		String result = new String(
				"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><center>");

		String tmpResult = new String(
				"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><center>");

		if (radioStation != null) {

			if (radioStation.equalsIgnoreCase(Constants.RADIO_32)) {
				result += getStringFromWebsite(urlHomepage, urlHomepage,
						"nowplaying", "</div>");
			} else if (radioStation
					.equalsIgnoreCase(Constants.RADIO_32_GOLDIES)) {
				result += getStringFromWebsite(
						"http://www.radio32.ch/?rub=124", urlHomepage,
						"nowplaying", "</div>");
			} else if (radioStation
					.equalsIgnoreCase(Constants.RADIO_CAPITAL_FM)) {

				// Braucht noch eine style class
				result = new String(
						"<html><head><style>.hidden {display:none;}</style></head><body><center>");

				result += getStringFromWebsite(urlHomepage, urlHomepage,
						"summary=\"Capital FM Airplay\">", "</table>");
			} else if (radioStation.equalsIgnoreCase("Radio 24")) {
				// result += getAktuellerSong(
				// "http://www.radio24.ch/player/index.html",
				// Constants.URL_HOMEPAGE_RADIO_24, "headerBottomRight",
				// "</script>");
			} else if (radioStation.equalsIgnoreCase(Constants.RADIO_24_ROCK)) {

				result += getStringFromWebsite(
						"http://www.radio24.ch/player/index.html?channel=rock",
						"", "mainContainer", "</script>");
			} else if (radioStation.equalsIgnoreCase(Constants.RADIO_RABE)) {

				result += getStringFromWebsite(
						"http://www.rabe.ch/nc/songticker.html", "",
						"playlist-latest-item", "</div>");

				// result = Constants.URL_SONGTICKER_RABE;
				// return result;
			} else if (radioStation.equalsIgnoreCase(Constants.RADIO_DRS1)) {
			} else if (radioStation.equalsIgnoreCase(Constants.RADIO_DRS2)) {
			} else if (radioStation.equalsIgnoreCase(Constants.RADIO_DRS3)) {

				String parseResult = new String();
				parseResult += getStringFromWebsite(
						"http://www.drs.ch/lib/player/radio.php?audiourl=http%3A%2F%2Fstream.srg-ssr.ch%2Fdrs3%2Fmp3_128.m3u&stream=drs3&design=drs3&type=popup&type=popup&skin=srdrs",
						"", "<span class=\"active_with_icon\"", "</span>");

				// aus einer langen Zeile den Text rausholen
				int index1 = parseResult
						.indexOf("<span class=\"active_with_icon\"");
				if (index1 >= 0) {
					parseResult = parseResult.substring(index1 + 50,
							index1 + 200);
					Log.d(TAG, "********* parseResult 1= " + parseResult);
				}
				int index2 = parseResult.indexOf("</span");
				if (index2 >= 0) {
					parseResult = parseResult.substring(0, index2);
					if (parseResult != null) {
						parseResult = parseResult.trim();
					}
					Log.d(TAG, "********* parseResult 2=" + parseResult);
				}

				result += result + parseResult;

			} else if (radioStation.equalsIgnoreCase(Constants.RADIO_DRS4)) {
			} else if (radioStation.equalsIgnoreCase(Constants.RADIO_TOP)) {

				String findString = "<td>Current Song:</td>";

				result += getStringFromWebsite("http://icecast.radiotop.ch",
						null, findString, "</div>");
			}
		}

		if (!result.equalsIgnoreCase(tmpResult)) {
			result += "</center></body></html>";
		} else {
			result = "";
		}
		return result;

	}

	/**
	 * 
	 * @param songUrl
	 * @param imgUrl
	 * @param findString
	 * @param endTagAfterMatch
	 * @return
	 */
	private String getStringFromWebsite(String songUrl, String imgUrl,
			String findString, String endStringOrTagAfterMatch) {

		StringBuilder result = new StringBuilder();
		Scanner scanner = null;

		if (imgUrl == null) {
			imgUrl = songUrl;
		}

		try {
			// Webseite einlesen
			HttpResponse response = null;
			HttpUriRequest httpUriRequest = null;
			Log.d(TAG, "songUrl=" + songUrl);
			HttpClient httpClient = new DefaultHttpClient();
			httpUriRequest = new HttpGet(songUrl);
			String userAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:2.0) Gecko/20100101 Firefox/4.0";
			httpUriRequest.setHeader("User-Agent", userAgent);
			response = httpClient.execute(httpUriRequest);
			// getHeaders(response);
			DataInputStream inputStream = new DataInputStream(response
					.getEntity().getContent());

			// Webseite parsen
			scanner = new Scanner(inputStream);
			int lineNr = 0;
			boolean hasTreffer = false;
			boolean ende = false;
			while (scanner.hasNextLine() && !ende) {
				lineNr++;
				String line = scanner.nextLine();
				Log.d(TAG, "lineNr=" + lineNr + " / line=" + line);

				int index = line.indexOf(findString);
				if (index >= 0) {
					hasTreffer = true;
					Log.d(TAG, "TREFFER!!! lineNr=" + lineNr + " / line="
							+ line);
				}
				if (hasTreffer) {
					if (line.indexOf(endStringOrTagAfterMatch) >= 0) {
						ende = true;
					}
					if (line.indexOf("<img src=") >= 0) {
						line = line.replace("<img src=\"", "<img src=\""
								+ imgUrl);
						Log.d(TAG, "lineNr=" + lineNr + " / line=" + line);
					}
					result.append(line);
				}

			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (scanner != null) {
				scanner.close();
				Log.d(TAG, "scanner closed!");
			}
		}

		Log.d(TAG, "Result from parse=" + result.toString());
		return result.toString();
	}

	private String getHeaders(HttpResponse response) {
		String ret = "headers:";
		for (Header h : response.getAllHeaders()) {
			ret += "\n" + h.getName() + ": " + h.getValue();
		}
		ret += "\nend of headers";

		Log.d(TAG, ret);
		return ret;

	}

	protected String getRT1token(Context context) {
		Utils utils = new Utils();
		String token = "";
		if (utils.isNetworkAvailable(context)) {
			String url = "http://edge.download.newmedia.nacamar.net/sltokens/flashplayer/stream-mp3-player.php?stream=hitradiort1/livestream.mp3";
			String findString = "var token = \"";
			String endString = "\";";
			token = getStringFromWebsite(url, null, findString, endString);
			if (token != null) {
				token = token.trim();
				token = token.substring(13, token.length() - 2);
			}
			Log.d(TAG, "** Token=" + token);
		}
		return token;
	}
}
