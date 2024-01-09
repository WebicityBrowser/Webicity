package com.github.webicitybrowser.webicity.core.renderer.imp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import com.github.webicitybrowser.spec.fetch.FetchProtocolRegistry;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.net.Protocol;
import com.github.webicitybrowser.webicity.core.net.ProtocolContext;
import com.github.webicitybrowser.webicity.core.net.ProtocolRegistry;

public class FetchProtocolRegistryImp implements FetchProtocolRegistry {

	private final ProtocolRegistry protocolRegistry;

	public FetchProtocolRegistryImp(ProtocolRegistry protocolRegistry) {
		this.protocolRegistry = protocolRegistry;
	}

	@Override
	public Optional<InputStream> openConnection(URL url) throws IOException {
		Optional<Protocol> protocol = protocolRegistry.getProtocolForURL(url);
		if (protocol.isEmpty()) return Optional.empty();
		return Optional.of(protocol.get()
			.openConnection(url, new ProtocolContext("GET", redirect -> true))
			.getInputStream());
	}

}
