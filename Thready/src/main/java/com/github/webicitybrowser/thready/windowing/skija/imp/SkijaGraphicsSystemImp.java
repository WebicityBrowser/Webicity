package com.github.webicitybrowser.thready.windowing.skija.imp;

import java.util.function.Consumer;

import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.drawing.skija.imp.SkijaResourceLoaderImp;
import com.github.webicitybrowser.thready.gui.graphical.animation.InvalidationScheduler;
import com.github.webicitybrowser.thready.windowing.core.Window;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;
import com.github.webicitybrowser.thready.windowing.skija.SkijaWindowingThread;

public class SkijaGraphicsSystemImp implements SkijaGraphicsSystem {
	
	private final SkijaWindowingThread windowingThread = new SkijaWindowingThreadImp(this);
	private final ResourceLoader resourceLoader = new SkijaResourceLoaderImp();
	private final InvalidationScheduler invalidationScheduler = windowingThread.getInvalidationScheduler();
	private final Runnable tickHandler;
	
	public SkijaGraphicsSystemImp(Runnable tickHandler) {
		this.tickHandler = tickHandler;
	}

	@Override
	public void createWindow(Consumer<Window> callback) {
		windowingThread.createWindow(callback);
	}

	@Override
	public ResourceLoader getResourceLoader() {
		return this.resourceLoader;
	}

	@Override
	public InvalidationScheduler getInvalidationScheduler() {
		return this.invalidationScheduler;
	}

	@Override
	public void startRenderLoop() {
		windowingThread.startRenderLoop(tickHandler);
	}
	
}
