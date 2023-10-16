package com.github.webicitybrowser.webicity.core.ui;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.renderer.RendererHandle;

public interface Frame {

	RendererHandle getCurrentRenderer();

	String getName();

	URL getURL();

	void navigate(URL url);
	
	boolean redirect(URL url);

	void reload();

	void back();

	void forward();

	void tick();

	void addEventListener(FrameEventListener listener, boolean sync);
	
}
