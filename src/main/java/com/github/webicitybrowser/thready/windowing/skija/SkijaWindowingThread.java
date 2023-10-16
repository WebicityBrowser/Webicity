package com.github.webicitybrowser.thready.windowing.skija;

import java.util.function.Consumer;

import com.github.webicitybrowser.thready.gui.graphical.animation.InvalidationScheduler;
import com.github.webicitybrowser.thready.windowing.core.Window;

public interface SkijaWindowingThread {

	void createWindow(Consumer<Window> callback);

	InvalidationScheduler getInvalidationScheduler();

	void startRenderLoop(Runnable tickHandler);

}
