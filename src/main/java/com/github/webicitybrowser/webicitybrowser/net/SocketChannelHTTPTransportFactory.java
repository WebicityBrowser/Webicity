package com.github.webicitybrowser.webicitybrowser.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ByteChannel;
import java.nio.channels.SocketChannel;
import java.security.KeyManagementException;
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

import com.github.webicitybrowser.spec.http.HTTPTransport;
import com.github.webicitybrowser.spec.http.HTTPTransportFactory;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicitybrowser.net.stream.ByteChannelInputStream;
import com.github.webicitybrowser.webicitybrowser.net.stream.ByteChannelOutputStream;

import tlschannel.ClientTlsChannel;

public final class SocketChannelHTTPTransportFactory implements HTTPTransportFactory {

	@Override
	public HTTPTransport createTransport(URL url) throws IOException {
		int port = getURLPort(url);
		SocketChannel socketChannel = openSocketChannel(url, port);
		ByteChannel socketByteChannel = isHTTPS(url) ?
			createSecureByteChannel(socketChannel, url, true) :
			socketChannel;
		
		InputStream inputStream = new ByteChannelInputStream(socketByteChannel, 8192);
		OutputStream outputStream = new ByteChannelOutputStream(socketByteChannel);
		
		return new HTTPTransport(inputStream, outputStream);
	}
	
	private int getURLPort(URL url) {
		return url.getPort() != -1 ?
			url.getPort() :
			isHTTPS(url) ? 443 : 80;
	}

	private SocketChannel openSocketChannel(URL url, int port) throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress(url.getHost(), port));
		socketChannel.configureBlocking(true);
		socketChannel.finishConnect();
		
		return socketChannel;
	}
	
	private boolean isHTTPS(URL url) {
		return url.getScheme().equals("https");
	}
	
	private ByteChannel createSecureByteChannel(ByteChannel socketChannel, URL url, boolean useTLS) throws IOException {
		try {
			SSLContext sslContext = createSSLContext(useTLS);
			
			SSLEngine engine = createSSLEngine(sslContext, url);
			
			return ClientTlsChannel
				.newBuilder(socketChannel, engine)
				.build();
		} catch (Exception e) {
			throw new IOException(e);
		}
 	}

	private SSLContext createSSLContext(boolean useTLS) throws Exception {
		SSLContext sslContext = SSLContext.getInstance("TLSv1.3");
		if (useTLS) {
			sslContext.init(null, null, new SecureRandom());
		} else {
			initDummySSLContext(sslContext);
		}
		
		return sslContext;
	}

	private SSLEngine createSSLEngine(SSLContext sslContext, URL url) {
		SSLEngine engine = sslContext.createSSLEngine();
		SSLParameters params = engine.getSSLParameters();
		params.setServerNames(Collections.singletonList(new SNIHostName(url.getHost())));
		engine.setSSLParameters(params);
		engine.setUseClientMode(true);
		
		return engine;
	}

	private void initDummySSLContext(SSLContext sslContext) throws KeyManagementException {
		TrustManager tm = new X509TrustManager() {
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			public X509Certificate[] getAcceptedIssuers() { return null; }
        };
		sslContext.init(null, new TrustManager[] {tm}, null);
	}
	
}
