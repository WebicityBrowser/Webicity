package everyos.browser.webicity.net.protocol.http.http11;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

public class HTTP11Parser {
	public static HTTP11Response parseFrom(InputStream stream) throws IOException {
		int status = 0;
		Map<String, String> headers = new HashMap<>();
		
		//Read until we get to the content
		ParseState state = ParseState.STATUS_HEADER;
		ParseState returnState = null;
		StringBuilder tmp_buf = null;
		StringBuilder tmp_buf_2 = null;
		
		try {
			int chi;
			while (state != null && (chi = stream.read()) != -1) {
				char ch = (char) chi;
				switch(state) {
					case STATUS_HEADER:
						if (ch == ' ') {
							state = ParseState.STATUS_CODE;
						}
						break;
						
					case STATUS_CODE:
						if (ch == ' ') {
							state = ParseState.STATUS_TEXT;
						} else {
							status = status * 10 + Character.digit(ch, 10);
						}
						break;
						
					case STATUS_TEXT:
						if (ch == '\r') {
							state = ParseState.NEWLINE;
							returnState = ParseState.HEADER_NAME_OR_NEWLINE;
						}
						break;
						
					case NEWLINE:
						assert(ch == '\n');
						state = returnState;
						break;
						
					case HEADER_NAME_OR_NEWLINE:
						if (ch == '\r') {
							returnState = null;
							state = ParseState.NEWLINE;
							break;
						}
						tmp_buf = new StringBuilder();
						state = ParseState.HEADER_NAME;
						
					case HEADER_NAME:
						if (ch == ':') {
							tmp_buf_2 = new StringBuilder();
							state = ParseState.HEADER_VALUE;
						} else {
							tmp_buf.append(ch);
						}
						break;
						
					case HEADER_VALUE:
						if (ch == '\r') {
							headers.put(tmp_buf.toString().toLowerCase(), tmp_buf_2.toString());
							returnState = ParseState.HEADER_NAME_OR_NEWLINE;
							state = ParseState.NEWLINE;
						} else if (ch != ' ' || !tmp_buf_2.isEmpty()) {
							tmp_buf_2.append(ch);
						}
						
					default:
						break;
				}
			}
		} catch (SocketTimeoutException e) {
			status = 408;
		}
		
		return new HTTP11Response(status, headers, stream);
	}

	private static enum ParseState {
		STATUS_HEADER, STATUS_CODE, STATUS_TEXT, NEWLINE, HEADER_NAME_OR_NEWLINE, HEADER_NAME, HEADER_VALUE
	}
}
