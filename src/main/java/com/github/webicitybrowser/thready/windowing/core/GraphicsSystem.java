package com.github.webicitybrowser.thready.windowing.core;

import java.util.function.Consumer;

import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;

public interface GraphicsSystem {

	void createWindow(Consumer<Window> window);
	
	ResourceLoader getResourceLoader();
	
}
