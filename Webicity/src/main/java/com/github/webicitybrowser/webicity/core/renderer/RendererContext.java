package com.github.webicitybrowser.webicity.core.renderer;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.RenderingEngine;

public interface RendererContext {

	RenderingEngine getRenderingEngine();

	URL getCurrentDocumentURL();
	
	void crash(RendererCrashReason reason) throws RendererCrashException;
	
	void crash(Exception exception) throws RendererCrashException;
	
}
