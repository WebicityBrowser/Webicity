package com.github.webicitybrowser.webicity.core.ui;

import com.github.webicitybrowser.webicity.core.renderer.RendererHandle;
import com.github.webicitybrowser.webicity.event.EventListener;

public interface FrameEventListener extends EventListener {

	void onRendererChange(RendererHandle rendererHandle);
	
}
