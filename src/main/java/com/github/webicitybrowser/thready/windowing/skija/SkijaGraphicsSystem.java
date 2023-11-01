package com.github.webicitybrowser.thready.windowing.skija;

import com.github.webicitybrowser.thready.gui.graphical.animation.InvalidationScheduler;
import com.github.webicitybrowser.thready.windowing.core.GraphicsSystem;
import com.github.webicitybrowser.thready.windowing.skija.imp.SkijaGraphicsSystemImp;

public interface SkijaGraphicsSystem extends GraphicsSystem {
	
	public InvalidationScheduler getInvalidationScheduler();
	
	public static SkijaGraphicsSystem createDefault(Runnable tickHandler) {
		return new SkijaGraphicsSystemImp(tickHandler);
	}
	
}
