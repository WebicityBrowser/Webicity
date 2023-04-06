package com.github.webicitybrowser.thready.windowing.skija;

import java.util.function.Consumer;

import com.github.webicitybrowser.thready.windowing.core.Window;

public interface SkijaWindowingThread {

	void createWindow(Consumer<Window> callback);

}
