package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.component;

import java.util.HashMap;
import java.util.Map;

import com.github.webicitybrowser.threadyweb.context.WebComponentContext;
import com.github.webicitybrowser.threadyweb.context.image.ImageEngine;
import com.github.webicitybrowser.webicity.core.renderer.RendererContext;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.component.image.ImageEngineImp;

public class WebComponentContextImp implements WebComponentContext {

	private final Map<Class<?>, Object> contextMap = new HashMap<>();

	public WebComponentContextImp(RendererContext rendererContext) {
		contextMap.put(ImageEngine.class, new ImageEngineImp(rendererContext));
	}

	@Override
	public <T> T getContext(Class<T> contextCls) {
		T context = contextCls.cast(contextMap.get(contextCls));
		if (context == null) {
			throw new IllegalArgumentException("No context of type " + contextCls + " found");
		}

		return context;
	}
	
}
