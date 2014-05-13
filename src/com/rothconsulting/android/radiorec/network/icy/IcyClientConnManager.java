package com.rothconsulting.android.radiorec.network.icy;

import org.apache.http.conn.ClientConnectionOperator;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.HttpParams;

/**
 * User: tom Date: 2/6/14 Time: 10:14 AM
 */
public class IcyClientConnManager extends SingleClientConnManager {

	public IcyClientConnManager(HttpParams params, SchemeRegistry schreg) {
		super(params, schreg);
	}

	@Override
	protected ClientConnectionOperator createConnectionOperator(SchemeRegistry schreg) {
		return new IcyClientConnectionOperator(schreg);
	}
}
