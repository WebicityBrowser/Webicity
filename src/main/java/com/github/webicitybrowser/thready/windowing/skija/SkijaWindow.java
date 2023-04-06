package com.github.webicitybrowser.thready.windowing.skija;

import com.github.webicitybrowser.thready.windowing.core.DesktopWindow;

public interface SkijaWindow extends DesktopWindow {

	SkijaScreen getScreen();

	boolean closed();

	void tick();
	
}
