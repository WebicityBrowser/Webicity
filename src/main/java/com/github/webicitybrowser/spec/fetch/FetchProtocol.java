package com.github.webicitybrowser.spec.fetch;

import com.github.webicitybrowser.webicity.core.net.ProtocolContext;
import com.github.webicitybrowser.webicity.core.net.ProtocolRegistry;

public interface FetchProtocol {

	ProtocolRegistry registry();

	ProtocolContext context();

	static FetchProtocol createFetchProtocolRegistry(ProtocolRegistry registry, ProtocolContext context) {
		return new FetchProtocol() {
			@Override
			public ProtocolRegistry registry() {
				return registry;
			}

			@Override
			public ProtocolContext context() {
				return context;
			}
		};
	}

}
