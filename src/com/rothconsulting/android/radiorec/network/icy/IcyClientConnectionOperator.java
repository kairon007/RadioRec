package com.rothconsulting.android.radiorec.network.icy;

import org.apache.http.conn.OperatedClientConnection;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.DefaultClientConnectionOperator;

/**
 * User: tom Date: 2/6/14 Time: 10:12 AM To change this template use File | Settings | File Templates.
 */
public class IcyClientConnectionOperator extends DefaultClientConnectionOperator {
	public IcyClientConnectionOperator(SchemeRegistry schemes) {
		super(schemes);
	}

	@Override
	public OperatedClientConnection createConnection() {
		return new IcyClientConnection();
	}
}
