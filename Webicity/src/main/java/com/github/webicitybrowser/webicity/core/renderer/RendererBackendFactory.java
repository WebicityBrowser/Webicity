package com.github.webicitybrowser.webicity.core.renderer;

import com.github.webicitybrowser.webicity.core.net.Connection;

public interface RendererBackendFactory {

	RendererBackend create(RendererContext context, Connection connection) throws Exception;
	
}
