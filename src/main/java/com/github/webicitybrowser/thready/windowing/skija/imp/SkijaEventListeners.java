package com.github.webicitybrowser.thready.windowing.skija.imp;

import com.github.webicitybrowser.thready.windowing.core.ScreenContent;

public final class SkijaEventListeners {

	private SkijaEventListeners() {}

	public static void setupEventListeners(long windowId, ScreenContent screenContent) {
		SkijaMouseEventListeners.setupEventListeners(windowId, screenContent);
	}
	
}
