package com.rothconsulting.android.radiorec.network.icy;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

/**
 * User: tom Date: 2/6/14 Time: 10:26 AM
 */
public class IcyGetRequest {
	private final HttpClient _httpClient;
	private final String _url;

	public IcyGetRequest(String url) {
		_url = url;
		Scheme http = new Scheme("http", PlainSocketFactory.getSocketFactory(), 80);
		SchemeRegistry sr = new SchemeRegistry();
		sr.register(http);

		HttpParams params = new BasicHttpParams();
		_httpClient = new DefaultHttpClient(new IcyClientConnManager(params, sr), params);
	}

	public HttpResponse get() throws IOException {
		HttpGet request = new HttpGet(_url);
		return _httpClient.execute(request);
	}
}
