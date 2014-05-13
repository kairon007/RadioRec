package com.rothconsulting.android.radiorec.network.icy;

import org.apache.http.HttpResponseFactory;
import org.apache.http.impl.conn.DefaultClientConnection;
import org.apache.http.impl.conn.DefaultResponseParser;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.params.HttpParams;

/**
 * User: Tom Date: 2/6/14 Time: 10:10 AM
 */
public class IcyClientConnection extends DefaultClientConnection {
	@Override
	protected HttpMessageParser createResponseParser(SessionInputBuffer buffer, HttpResponseFactory responseFactory, HttpParams params) {
		return new DefaultResponseParser(buffer, new IcyLineParser(), responseFactory, params);
	}
}
