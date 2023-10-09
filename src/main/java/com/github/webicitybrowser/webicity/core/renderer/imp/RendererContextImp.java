package com.github.webicitybrowser.webicity.core.renderer.imp;

import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.webicity.core.AssetLoader;
import com.github.webicitybrowser.webicity.core.renderer.ExceptionRendererCrashReason;
import com.github.webicitybrowser.webicity.core.renderer.RendererContext;
import com.github.webicitybrowser.webicity.core.renderer.RendererCrashException;
import com.github.webicitybrowser.webicity.core.renderer.RendererCrashReason;

public class RendererContextImp implements RendererContext {

	private final AssetLoader assetLoader;
	private final FetchEngine fetchEngine;

	public RendererContextImp(AssetLoader assetLoader, FetchEngine fetchEngine) {
		this.assetLoader = assetLoader;
		this.fetchEngine = fetchEngine;
	}

	@Override
	public AssetLoader getAssetLoader() {
		return this.assetLoader;
	}

	@Override
	public FetchEngine getFetchEngine() {
		return fetchEngine;
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
