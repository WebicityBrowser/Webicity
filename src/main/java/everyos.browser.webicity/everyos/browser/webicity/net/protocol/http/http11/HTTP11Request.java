package everyos.browser.webicity.net.protocol.http.http11;

import java.util.HashMap;
import java.util.Map;

import everyos.browser.webicity.net.URL;

public class HTTP11Request {
	// TODO: https://wicg.github.io/ua-client-hints/
	private final Map<String, String> headers;
	
	private String type = "GET";
	private String target = "/";
	
	public HTTP11Request() {
		this.headers = new HashMap<>();
		
		//headers.put("Transfer-Encoding", "identity");
		headers.put("Upgrade-Insecure-Requests", "1");
		
		headers.put("Sec-Fetch-Dest", "document");
		headers.put("Sec-Fetch-Mode", "navigate");
		headers.put("Sec-Fetch-Site", "cross-site");
		headers.put("Sec-Fetch-User", "?1");
	}

	public void setRequestType(REQUEST_TYPE type) {
		this.type = type.toString();
	};
	
	public void setRequestTarget(URL url) {
		String host = url.getHost();
		int port = url.getPort(); 
		if (port==-1) {
			headers.put("Host", host);
		} else {
			headers.put("Host", host+':'+port);
		}
		
		this.target = url.getPath();
		if (this.target.isEmpty()) {
			this.target = "/";
		}
		/*if (url.getFragment()!=null) {
			target+='#'+url.getFragment();
		}*/
		if (url.getQuery()!=null) {
			target+='?'+url.getQuery();
		}
	}
	
	//TODO: Support range and TE
	
	private void addAccept(String field, String type, double preference, String... parameters) {
		String accept = headers.getOrDefault(field, "");
		if (!accept.isEmpty()) {
			accept+=", ";
		}
		accept+=type;
		if (preference!=-1) {
			if (preference == 0) {
				accept+=";q=0";
			} else {
				accept+=";q="+String.valueOf(preference).replace("0.", ".");
			}
		}
		for (String parameter: parameters) {
			accept+=";"+parameter;
		}
		headers.put(field, accept);
	}
	
	public void accept(String type, double preference, String... parameters) {
		addAccept("Accept", type, preference, parameters);
	};
	
	public void acceptCharset(String type, double preference) {
		addAccept("Accept-Charset", type, preference);
	};
	
	public void acceptEncoding(String type, double preference) {
		addAccept("Accept-Encoding", type, preference);
	};
	
	public void acceptLanugage(String type, double preference) {
		//addAccept("Accept-Language", type, preference);
	};
	
	public void setAuthorization(String type, String credentials) {
		headers.put("Authorization", type+' '+credentials);
	}
	
	public void setReferrer(URL url, boolean isSecure) {
		String built = "";
		if (url.getProtocol() != null) {
			if (url.getHost().equals("file") || url.getHost().equals("data")) {
				return;
			}
			
			built += url.getProtocol() + "://";
		}
		
		if (url.getHost() != null) {
			built += url.getHost()+"/";
		}
		
		if (url.getPath() != null) {
			built += url.getPath();
		}
		headers.put("Referrer", built);
	}
	
	public void setUserAgent(String ua) {
		headers.put("User-Agent", ua);
	}
	
	@Override
	public String toString() {
		StringBuilder req = new StringBuilder(type+' '+target+" HTTP/1.1");
		headers.forEach((k, v)->{
			req.append("\r\n"+k+": "+v);
		});
		
		System.out.println(req.toString());
		
		return req.toString()+"\r\n\r\n";
	}
	
	public static enum REQUEST_TYPE {
		GET, HEAD, POST, PUT, DELETE, CONNECT, OPTIONS, TRACE
	}
}
