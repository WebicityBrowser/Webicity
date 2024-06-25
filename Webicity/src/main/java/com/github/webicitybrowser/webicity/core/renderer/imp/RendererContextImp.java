package com.github.webicitybrowser.webicity.core.renderer.imp;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.RenderingEngine;
import com.github.webicitybrowser.webicity.core.renderer.ExceptionRendererCrashReason;
import com.github.webicitybrowser.webicity.core.renderer.RendererContext;
import com.github.webicitybrowser.webicity.core.renderer.RendererCrashException;
import com.github.webicitybrowser.webicity.core.renderer.RendererCrashReason;

public class RendererContextImp implements RendererContext {

	private final RenderingEngine renderingEngine;
	private final URL documentURL;

	public RendererContextImp(RenderingEngine renderingEngine, URL docURL) {
		this.renderingEngine = renderingEngine;
		this.documentURL = docURL;
	}

	@Override
	public RenderingEngine getRenderingEngine() {
		return this.renderingEngine;
	}

	@Override
	public URL getCurrentDocumentURL() {
		return documentURL;
	}

	@Override
	public void crash(RendererCrashReason reason) throws RendererCrashException {
		throw new RendererCrashException(reason);
	}

	@Override
	public void crash(Exception exception) throws RendererCrashException {
		crash(new ExceptionRendererCrashReason(exception));
	}

}
