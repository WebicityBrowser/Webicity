package com.github.webicitybrowser.thready.windowing.skija;

import com.github.webicitybrowser.thready.windowing.core.GraphicsSystem;
import com.github.webicitybrowser.thready.windowing.skija.imp.SkijaGraphicsSystemImp;

public interface SkijaGraphicsSystem extends GraphicsSystem {
	
	public static SkijaGraphicsSystem createDefault() {
		return new SkijaGraphicsSystemImp();
	}
	
}
