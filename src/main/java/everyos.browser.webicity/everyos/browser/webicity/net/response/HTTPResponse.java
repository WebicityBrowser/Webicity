package everyos.browser.webicity.net.response;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;

import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicity.renderer.html.HTMLRenderer;
import everyos.browser.webicity.renderer.plaintext.PlainTextRenderer;

public class HTTPResponse extends Response {
	private int status;
	private HashMap<String, String> headers;
	private InputStream inputStream;

	public HTTPResponse(Socket sock) throws IOException {
		this.status = 0;
		
		//Read until we get to the content
		InputStream stream = sock.getInputStream();
		ParseState state = ParseState.STATUS_HEADER;
		ParseState returnState = null;
		StringBuilder tmp_buf = null;
		StringBuilder tmp_buf_2 = null;
		
		this.headers = new HashMap<String, String>();
		
		try {
			int chi = -1;
			while (state!=null&&(chi=stream.read())!=-1) {
				char ch = (char) chi;
				switch(state) {
					case STATUS_HEADER:
						if (ch==' ') {
							tmp_buf = new StringBuilder(3);
							state = ParseState.STATUS_CODE;
						}
						break;
					case STATUS_CODE:
						if (ch==' ') {
							status = Integer.valueOf(tmp_buf.toString());
							state = ParseState.STATUS_TEXT;
						} else tmp_buf.append(ch);
						break;
					case STATUS_TEXT:
						if (ch=='\r') {
							state = ParseState.NEWLINE;
							returnState = ParseState.HEADER_NAME_OR_NEWLINE;
						}
						break;
					case NEWLINE:
						//assert: ch=='\n'
						state = returnState;
						break;
					case HEADER_NAME_OR_NEWLINE:
						if (ch=='\r') {
							returnState = null;
							state = ParseState.NEWLINE;
							break;
						}
						tmp_buf = new StringBuilder();
						state = ParseState.HEADER_NAME;
					case HEADER_NAME:
						if (ch==':') {
							tmp_buf_2 = new StringBuilder();
							state = ParseState.HEADER_VALUE_OR_SPACE;
						} else tmp_buf.append(ch);
						break;
					case HEADER_VALUE_OR_SPACE:
						if (ch==' ') break;
						state = ParseState.HEADER_VALUE;
					case HEADER_VALUE:
						if (ch=='\r') {
							headers.put(tmp_buf.toString().toLowerCase(), tmp_buf_2.toString());
							returnState = ParseState.HEADER_NAME_OR_NEWLINE;
							state = ParseState.NEWLINE;
						} else tmp_buf_2.append(ch);
					default:
						break;
				}
			}
		} catch (SocketTimeoutException e) {
			this.status = 408;
		}
		
		int len = 0; boolean uchunked = false;
		headers.forEach((n, v)->System.out.println(n+": "+v));
		
		if (headers.getOrDefault("transfer-encoding", "").equals("chunked")) {
			uchunked = true;
		} else if (headers.containsKey("content-length")) {
			len = Integer.valueOf(headers.get("content-length"));
		} else {
			sock.close();
			throw new IOException("Content Length is missing!");
		}
		
		int mlen = len; boolean chunked = uchunked;
		this.inputStream = new InputStream() {
			int flen = mlen;
			
			private int pos = 0;

			@Override public int read() throws IOException {
				if (pos==flen) {
					if (!chunked) return -1;
					StringBuilder size = new StringBuilder(4);
					int chi;
					while((chi=stream.read())!='\r') {
						//pos++;
						if (chi==-1) break;
						size.append((char) chi);
					}
					stream.read();
					if (size.toString().equals("")) return -1;
					if(Integer.parseInt(size.toString(), 16)==0) return -1;
					flen = pos+Integer.parseInt(size.toString(), 16);
				}
				pos++;
				int ch = stream.read();
				return ch;
			}
		};
	}

	@Override public Renderer getProbableRenderer() {
		String type = headers.getOrDefault("content-type", "text/plain");
		//this.inputStream.
		if (type.indexOf(';')!=-1) type = type.substring(0, type.indexOf(';'));
		switch(type) {//TODO: Move this to a registry
			case "text/html":
				return new HTMLRenderer();	
			default:
				return new PlainTextRenderer();
		}
	}

	@Override public int getStatus() {
		return status;
	}

	@Override public InputStream getConnection() throws UnsupportedEncodingException {
		return this.inputStream;
	}
	
	private static enum ParseState {
		STATUS_HEADER, STATUS_CODE, STATUS_TEXT, NEWLINE, HEADER_NAME_OR_NEWLINE, HEADER_NAME, HEADER_VALUE, HEADER_VALUE_OR_SPACE
	}
}
