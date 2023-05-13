package com.github.webicitybrowser.webicitybrowser.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

import com.github.webicitybrowser.spec.http.HTTPTransport;
import com.github.webicitybrowser.spec.http.HTTPTransportFactory;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicitybrowser.net.stream.ByteChannelInputStream;
import com.github.webicitybrowser.webicitybrowser.net.stream.ByteChannelOutputStream;

public final class SocketChannelHTTPTransportFactory implements HTTPTransportFactory {

	@Override
	public HTTPTransport createTransport(URL url) throws IOException {
		int port = url.getPort() != -1 ? url.getPort() : 80;
		SocketChannel socketChannel = openSocketChannel(url, port);
		
		InputStream inputStream = new ByteChannelInputStream(socketChannel, 8192);
		OutputStream outputStream = new ByteChannelOutputStream(socketChannel);
		
		return new HTTPTransport(inputStream, outputStream);
	}
	
	private SocketChannel openSocketChannel(URL url, int port) throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress(url.getHost(), port));
		socketChannel.configureBlocking(true);
		socketChannel.finishConnect();
		
		return socketChannel;
	}
	
}
