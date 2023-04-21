package com.github.webicitybrowser.webicity.core.renderer.imp;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.github.webicitybrowser.webicity.core.renderer.RendererBackendFactory;
import com.github.webicitybrowser.webicity.core.renderer.RendererBackendRegistry;

public class RendererBackendRegistryImp implements RendererBackendRegistry {
	
	private final Map<String, RendererBackendFactory> contentMappings = new HashMap<>();

	@Override
	public Optional<RendererBackendFactory> getBackendFactory(String contentType) {
		return Optional.ofNullable(contentMappings.get(contentType));
	}
	
	@Override
	public void registerBackendFactory(String contentType, RendererBackendFactory factory) {
		contentMappings.put(contentType, factory);
	}

}
