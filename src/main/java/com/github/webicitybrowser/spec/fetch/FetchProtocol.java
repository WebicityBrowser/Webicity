package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.webicity.core.net.ProtocolRegistry;

public interface FetchProtocol {

	ProtocolRegistry registry();

	public static FetchProtocol createFetchProtocolRegistry(ProtocolRegistry registry) {
		return new FetchProtocol() {
			@Override
			public ProtocolRegistry registry() {
				return registry;
			}
		};
	}

}
