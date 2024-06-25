package com.github.webicitybrowser.webicity.core.net;

import java.util.Optional;

import com.github.webicitybrowser.spec.url.URL;

public interface ProtocolRegistry {

	void registerProtocol(Protocol protocol);

	Optional<Protocol> getProtocolForURL(URL url);
	
}
