package com.github.webicitybrowser.thready.windowing.core;

import java.util.function.Consumer;

public interface WindowingSystem {

	void createWindow(Consumer<Window> window);
	
}
