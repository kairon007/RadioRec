package com.rothconsulting.android.radiorec.network.icy;

import org.apache.http.Header;
import org.apache.http.ParseException;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.ParserCursor;
import org.apache.http.util.CharArrayBuffer;

/**
 * User: tom Date: 2/6/14 Time: 11:28 AM
 */
public class IcyLineParser extends BasicLineParser {
	@Override
	public Header parseHeader(CharArrayBuffer buffer) throws ParseException {
		return super.parseHeader(meltBuffer(buffer));
	}

	@Override
	public ProtocolVersion parseProtocolVersion(CharArrayBuffer buffer, ParserCursor cursor) throws ParseException {
		CharArrayBuffer meltedBuffer = meltBuffer(buffer);
		cursor = meltCursor(meltedBuffer, buffer, cursor);

		return super.parseProtocolVersion(meltedBuffer, cursor);
	}

	@Override
	public boolean hasProtocolVersion(CharArrayBuffer buffer, ParserCursor cursor) {
		CharArrayBuffer meltedBuffer = meltBuffer(buffer);
		cursor = meltCursor(meltedBuffer, buffer, cursor);
		return super.hasProtocolVersion(meltedBuffer, cursor);
	}

	@Override
	public StatusLine parseStatusLine(CharArrayBuffer buffer, ParserCursor cursor) throws ParseException {
		CharArrayBuffer meltedBuffer = meltBuffer(buffer);
		cursor = meltCursor(meltedBuffer, buffer, cursor);

		return super.parseStatusLine(meltedBuffer, cursor);
	}

	private static CharArrayBuffer meltBuffer(CharArrayBuffer buffer) {
		if (buffer.length() >= 4 && buffer.charAt(0) == 'I' && buffer.charAt(1) == 'C' && buffer.charAt(2) == 'Y' && buffer.charAt(3) == ' '
				&& Character.isDigit(buffer.charAt(4))) {
			CharArrayBuffer fixedBuffer = new CharArrayBuffer(buffer.capacity() + 5);
			fixedBuffer.append("HTTP/1.0 ");
			fixedBuffer.append(buffer, 4, buffer.length() - 4);

			buffer = fixedBuffer;
		}

		return buffer;
	}

	private static ParserCursor meltCursor(CharArrayBuffer meltedBuffer, CharArrayBuffer buffer, ParserCursor cursor) {
		if (meltedBuffer.length() != buffer.length()) {
			// Create a new cursor if the new buffer size has changed
			return new ParserCursor(0, meltedBuffer.length());
		}
		return cursor;
	}
}
