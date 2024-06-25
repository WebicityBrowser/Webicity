package com.github.webicitybrowser.webicity.core.ui;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.renderer.RendererHandle;
import com.github.webicitybrowser.webicity.event.EventListener;

public interface FrameEventListener extends EventListener {

	default void onURLChange(URL url) {};
	
	default void onRendererChange(RendererHandle rendererHandle) {};
	
}
