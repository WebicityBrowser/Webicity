package com.github.webicitybrowser.webicity.core.net.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.net.Protocol;
import com.github.webicitybrowser.webicity.core.net.ProtocolRegistry;

public class ProtocolRegistryImp implements ProtocolRegistry {
	
	private Map<String, Protocol> registeredProtocols = new HashMap<>();

	@Override
	public void registerProtocol(Protocol protocol) {
		for (String scheme: protocol.getSchemes()) {
			registeredProtocols.put(scheme, protocol);
		}
	}
	
	@Override
	public Optional<Protocol> getProtocolForURL(URL url) {
		return Optional.ofNullable(registeredProtocols.get(url.getScheme()));
	}

}
