package everyos.browser.webicity.net.protocol.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import everyos.browser.webicity.net.URL;
import everyos.browser.webicity.net.protocol.http.http11.HTTP11Parser;
import everyos.browser.webicity.net.protocol.http.http11.HTTP11Request;
import everyos.browser.webicity.net.protocol.http.http11.HTTP11Response;
import tlschannel.ClientTlsChannel;

public class HTTPSocket {
	private SocketChannel channel;
	private InputStream inputStream;
	private OutputStream outputStream;

	public HTTPSocket(SocketChannel channel, InputStream inputStream, OutputStream outputStream) {
		this.channel = channel;
		this.inputStream = inputStream;
		this.outputStream = outputStream;
	}

	public static HTTPSocket openSocket(URL url, boolean useTLS) throws IOException {
		int port = url.getPort();
		if (port==-1) port=url.getProtocol().equals("https")?443:80;
		
		SocketChannel sockChannel = SocketChannel.open();
		sockChannel.connect(new InetSocketAddress(url.getHost(), port));
		sockChannel.finishConnect();
		
		ByteChannel finalChannel = sockChannel;
		
		if (port==443) {
			try {
				SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
				if (useTLS) {
					sslContext.init(null, null, new SecureRandom());
				} else {
					TrustManager tm = new X509TrustManager() {
			            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
						public X509Certificate[] getAcceptedIssuers() { return null; }
			        };
					sslContext.init(null, new TrustManager[] {tm}, new SecureRandom());
				}
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
		
		OutputStream outputStream = new ByteChannelOutputStream(finalChannel);
		InputStream inputStream = new ByteChannelInputStream(finalChannel, 8192, 10000);
		
		return new HTTPSocket(sockChannel, inputStream, outputStream);
	}

	public void sendRequest(HTTP11Request request) throws IOException {
		outputStream.write(request.toString().getBytes());
		outputStream.flush();
		outputStream.close();
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setBlocking(boolean b) throws IOException {
		channel.configureBlocking(b);
	}

	public HTTP11Response getResponse() throws IOException {
		return HTTP11Parser.parseFrom(inputStream);
	}
}
