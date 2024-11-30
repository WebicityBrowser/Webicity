package com.github.webicitybrowser.webicity.core.renderer;

import com.github.webicitybrowser.spec.fetch.FetchResponse;

public interface RendererBackendFactory {

	RendererBackend create(RendererContext context, FetchResponse response) throws Exception;
	
}
