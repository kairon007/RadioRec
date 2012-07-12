package com.rothconsulting.android.radiorec;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class WebsiteParser extends AsyncTask<String, Void, String> {

	private static final String TAG = "WebTool";

	/**
	 * @param songUrl
	 * @param imgUrl
	 * @param findString
	 * @param endStringOrTagAfterMatch
	 */
	@Override
	protected String doInBackground(String... params) {

		StringBuilder result = new StringBuilder();
		Scanner scanner = null;
		String songUrl = params[0];
		String imgUrl = params[1];
		String findString = params[2];
		String endStringOrTagAfterMatch = params[3];

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
			boolean ende = false;
			while (scanner.hasNextLine() && !ende) {
				boolean hasTreffer = false;
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
						result.append(line);
					}
					// if (line.indexOf("<img src=") >= 0) {
					// line = line.replace("<img src=\"", "<img src=\""
					// + imgUrl);
					// Log.d(TAG, "lineNr=" + lineNr + " / line=" + line);
					// result.append(line);
					// }
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
}
