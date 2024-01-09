package com.github.webicitybrowser.webicitybrowser;

import com.github.webicitybrowser.webicity.core.RenderingEngine;

public interface BrowserInstance {

	RenderingEngine getRenderingEngine();

	void tick();
	
}
