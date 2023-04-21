package com.github.webicitybrowser.webicity.core.renderer;

import java.util.Optional;

public interface RendererBackendRegistry {

	Optional<RendererBackendFactory> getBackendFactory(String contentType);

	void registerBackendFactory(String contentType, RendererBackendFactory factory);
	
}
