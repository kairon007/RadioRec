package com.rothconsulting.android.radiorec;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.rothconsulting.android.common.Utils;

public class WebTool {

	private static final String TAG = "WebTool";

	/**
	 * 
	 * @param radioStation
	 * @return
	 */
	protected String getSongticker(String radioStation, String urlHomepage) {

		String result = new String("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><center>");

		String tmpResult = new String("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><body><center>");

		if (radioStation != null) {

			if (radioStation.equalsIgnoreCase(Stations.RADIO_32)) {

				result += new WebsiteParser().execute(urlHomepage, urlHomepage, "nowplaying", "</div>");
			} else if (radioStation.equalsIgnoreCase(Stations.RADIO_32_GOLDIES)) {
				result += new WebsiteParser().execute("http://www.radio32.ch/?rub=124", urlHomepage, "nowplaying", "</div>");
			} else if (radioStation.equalsIgnoreCase(Stations.RADIO_BERN_1)) {

				// Braucht noch eine style class
				result = new String("<html><head><style>.hidden {display:none;}</style></head><body><center>");

				result += new WebsiteParser().execute(urlHomepage, urlHomepage, "summary=\"Capital FM Airplay\">", "</table>");
			} else if (radioStation.equalsIgnoreCase("Radio 24")) {
				// result += getAktuellerSong(
				// "http://www.radio24.ch/player/index.html",
				// Constants.URL_HOMEPAGE_RADIO_24, "headerBottomRight",
				// "</script>");
			} else if (radioStation.equalsIgnoreCase(Stations.RADIO_24_ROCK)) {

				result += new WebsiteParser().execute("http://www.radio24.ch/player/index.html?channel=rock", "", "mainContainer", "</script>");
			} else if (radioStation.equalsIgnoreCase(Stations.RADIO_RABE)) {

				result += new WebsiteParser().execute("http://www.rabe.ch/nc/songticker.html", "", "playlist-latest-item", "</div>");

				// result = Constants.URL_SONGTICKER_RABE;
				// return result;
			} else if (radioStation.equalsIgnoreCase(Stations.RADIO_SRF1)) {
			} else if (radioStation.equalsIgnoreCase(Stations.RADIO_SRF2)) {
			} else if (radioStation.equalsIgnoreCase(Stations.RADIO_SRF3)) {

				String parseResult = new String();
				parseResult += new WebsiteParser()
						.execute(
								"http://www.drs.ch/lib/player/radio.php?audiourl=http%3A%2F%2Fstream.srg-ssr.ch%2Fdrs3%2Fmp3_128.m3u&stream=drs3&design=drs3&type=popup&type=popup&skin=srdrs",
								"", "<span class=\"active_with_icon\"", "</span>");

				// aus einer langen Zeile den Text rausholen
				int index1 = parseResult.indexOf("<span class=\"active_with_icon\"");
				if (index1 >= 0) {
					parseResult = parseResult.substring(index1 + 50, index1 + 200);
					Utils.log(TAG, "********* parseResult 1= " + parseResult);
				}
				int index2 = parseResult.indexOf("</span");
				if (index2 >= 0) {
					parseResult = parseResult.substring(0, index2);
					if (parseResult != null) {
						parseResult = parseResult.trim();
					}
					Utils.log(TAG, "********* parseResult 2=" + parseResult);
				}

				result += result + parseResult;

			} else if (radioStation.equalsIgnoreCase(Stations.RADIO_SRF4)) {
			} else if (radioStation.equalsIgnoreCase(Stations.RADIO_TOP)) {

				String findString = "<td>Current Song:</td>";

				result += new WebsiteParser().execute("http://icecast.radiotop.ch", null, findString, "</div>");
			}
		}

		if (!result.equalsIgnoreCase(tmpResult)) {
			result += "</center></body></html>";
		} else {
			result = "";
		}
		return result;

	}

	private static String getHeaders(HttpResponse response) {
		String ret = "headers:";
		for (Header h : response.getAllHeaders()) {
			ret += "\n" + h.getName() + ": " + h.getValue();
		}
		ret += "\nend of headers";

		Utils.log(TAG, ret);
		return ret;

	}

	/**
	 * Radio jugglerz.de hat immer Donnerstags eine Live Sendung. Ab Freitag kann man diese als mp3 h�ren.
	 * 
	 * @return Dateiname 'xxxx.mp3'
	 */
	protected String getJugglerzFileName(Context context) {
		String token = "";
		if (Utils.isNetworkAvailable(context, null, false)) {
			String url = "http://www.jugglerz.de";
			String findString = "<a href=\"http://www.jugglerz.de/shows/";
			String endString = ".mp3\"><img ";
			AsyncTask<String, Void, String> tokenAsyncTask = new WebsiteParser().execute(url, null, findString, endString);

			try {
				token = tokenAsyncTask.get();
				Utils.log(TAG, "++++++++++++ Token found =" + token);
			} catch (InterruptedException e) {
				Log.e(TAG, "InterruptedException :-( ", e);
			} catch (ExecutionException e) {
				Log.e(TAG, "ExecutionException :-( ", e);
			}
			if (!token.equals("") && token.length() > 10) {
				int index = token.indexOf(findString);
				token = token.substring(index + 38, index + 61);// 38+23
				token = token.trim();
			}
			Utils.log(TAG, "++++++++++++ Token parsed =" + token);
		}
		return token;
	}

	protected String getStreamUrlFromServer() {
		String streamUrl = "";
		String baseUrl = Constants.BASE_STREAM_URL + Constants.SELECTED_STATION_NAME_VALUE;
		// httpOptions.postData = new UrlEncodedFormEntity(postParameter, ENCODING);
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(baseUrl);
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity httpEntity = response.getEntity();

			if (httpEntity != null) {

			}

		} catch (ClientProtocolException e) {
			Log.e(TAG, "ClientProtocolException while getting: " + baseUrl, e);
		} catch (IOException e) {
			Log.e(TAG, "IOException while getting: " + baseUrl, e);
		} catch (Exception e) {
			Log.e(TAG, "Exception while getting: " + baseUrl, e);
		}

		return streamUrl;
	}
}
