package everyos.browser.spec.jnet.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
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

import everyos.browser.spec.jnet.URL;
import everyos.browser.spec.jnet.http.http11.HTTP11Parser;
import everyos.browser.spec.jnet.http.http11.HTTP11Request;
import everyos.browser.spec.jnet.http.http11.HTTP11Response;
import everyos.browser.spec.jnet.http.stream.ByteChannelInputStream;
import everyos.browser.spec.jnet.http.stream.ByteChannelOutputStream;
import tlschannel.ClientTlsChannel;

public class HTTPSocket {
	
	private static final int DEFAULT_HTTPS_PORT = 443;
	private static final int DEFAULT_HTTP_PORT = 80;
	
	private final SocketChannel channel;
	private final InputStream inputStream;
	private final OutputStream outputStream;

	public HTTPSocket(SocketChannel channel, InputStream inputStream, OutputStream outputStream) {
		this.channel = channel;
		this.inputStream = inputStream;
		this.outputStream = outputStream;
	}
	
	public void sendRequest(HTTP11Request request) throws IOException {
		outputStream.write(request.toString().getBytes());
		outputStream.flush();
		outputStream.close();
	}

	public InputStream getInputStream() {
		return inputStream;
	}
	
	public HTTP11Response getResponse() throws IOException {
		channel.configureBlocking(false);
		
		return HTTP11Parser.parseFrom(inputStream);
	}
	
	//

	public static HTTPSocket openHTTPSocket(URL url, boolean useTLS) throws IOException {
		int port = getPortOfURL(url);
		SocketChannel socketChannel = establishSocketChannel(url, port);
		ByteChannel socketChannelWithSecurities = urlIsSecure(url) ?
			wrapSocketChannelWithSecurities(socketChannel, url, useTLS) :
			socketChannel;
		
		OutputStream outputStream = new ByteChannelOutputStream(socketChannelWithSecurities);
		InputStream inputStream = new ByteChannelInputStream(socketChannelWithSecurities, 8192, 10000);
		
		return new HTTPSocket(socketChannel, inputStream, outputStream);
	}
	
	private static SocketChannel establishSocketChannel(URL url, int port) throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress(url.getHost(), port));
		socketChannel.finishConnect();
		
		return socketChannel;
	}

	private static ByteChannel wrapSocketChannelWithSecurities(ByteChannel socketChannel, URL url, boolean useTLS) throws IOException {
		try {
			SSLContext sslContext = useTLS ?
				createSecureSSLContext() :
				createInsecureSSLContext();
			
			SSLEngine engine = initSSLEngine(sslContext, url);
			
			return ClientTlsChannel
				.newBuilder(socketChannel, engine)
				.build();
		} catch (KeyManagementException | NoSuchAlgorithmException e) {
			throw new IOException(e);
		}
	}
	
	private static int getPortOfURL(URL url) {
		return url.getPort() != -1 ?
			url.getPort() :
			url.getProtocol().equals("https") ?
				DEFAULT_HTTPS_PORT :
				DEFAULT_HTTP_PORT;
	}
	
	private static boolean urlIsSecure(URL url) {
		return getPortOfURL(url) == 443;
	}

	private static SSLContext createSecureSSLContext() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
		sslContext.init(null, null, new SecureRandom());
		
		return sslContext;
	}

	private static SSLContext createInsecureSSLContext() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
		TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public X509Certificate[] getAcceptedIssuers() { return null; }
        };
		sslContext.init(null, new TrustManager[] {tm}, null);
		
		return sslContext;
	}
	
	private static SSLEngine initSSLEngine(SSLContext sslContext, URL url) {
		SSLEngine engine = sslContext.createSSLEngine();
		SSLParameters params = engine.getSSLParameters();
		params.setServerNames(Collections.singletonList(new SNIHostName(url.getHost())));
		engine.setSSLParameters(params);
		engine.setUseClientMode(true);
		
		return engine;
	}

}
