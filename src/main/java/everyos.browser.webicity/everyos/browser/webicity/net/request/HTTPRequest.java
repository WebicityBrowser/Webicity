package everyos.browser.webicity.net.request;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;
import java.security.SecureRandom;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;

import everyos.browser.webicity.net.URL;
import everyos.browser.webicity.net.response.HTTPResponse;
import everyos.browser.webicity.net.response.Response;
import tlschannel.ClientTlsChannel;

public class HTTPRequest implements Request {
	protected URL url;
	protected String method;
	protected HashMap<String, String> headers;

	public HTTPRequest(URL url) {
		this.url = url;
		this.method = "GET";
		this.headers = new HashMap<>();
		
		headers.put("User-Agent", "Webicity");
		headers.put("Host", url.getHost());
	}

	public static HTTPRequest create(URL url) {
		return new HTTPRequest(url);
	}

	@Override public URL getURL() {
		return url;
	}

	@Override public Response send() throws UnknownHostException, IOException {
		int port = url.getPort();
		if (port==-1) port=url.getProtocol().equals("https")?443:80;
		
		SocketChannel sockChannel = SocketChannel.open();
		sockChannel.connect(new InetSocketAddress(url.getHost(), port));
		sockChannel.finishConnect();
		
		ByteChannel finalChannel = sockChannel;
		
		if (port==443) {
			try {
				/*TrustManager tm = new X509TrustManager() {
		            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

		            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		            	
		            }

		            public X509Certificate[] getAcceptedIssuers() {
		                return null;
		            }
		        };*/
				
				SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
				//sslContext.init(null, new TrustManager[] {tm}, new SecureRandom());
				sslContext.init(null, null, new SecureRandom()); //TOD: "No ssl" mode
				SSLEngine engine = sslContext.createSSLEngine();
				SSLParameters params = engine.getSSLParameters();
				params.setServerNames(Collections.singletonList(new SNIHostName(url.getHost())));
				engine.setSSLParameters(params);
				engine.setUseClientMode(true);
				
				finalChannel = ClientTlsChannel
					.newBuilder(sockChannel, engine)
					.build();
			} catch (Exception e) {
				throw new IOException(e);
			}
		}
		
		OutputStream stream = new ByteChannelOutputStream(finalChannel);
		
		stream.write((method+" "+url.getPath()+" HTTP/1.1\r\n").getBytes());
		Iterator<String> it = headers.keySet().iterator();
		while (it.hasNext()) {
			String name = it.next();
			stream.write((name+": "+headers.get(name)+"\r\n").getBytes());
		};
		stream.write(("\r\n").getBytes());
		stream.flush();
		
		stream.close();
		
		//sock.setSoTimeout(10000);
		
		return new HTTPResponse(sockChannel, finalChannel);
	}
}
