package com.github.webicitybrowser.thready.windowing.skija;

import com.github.webicitybrowser.thready.renderer.skija.imp.SkijaWindowingSystemImp;
import com.github.webicitybrowser.thready.windowing.core.WindowingSystem;

public interface SkijaWindowingSystem extends WindowingSystem {
	
	public static SkijaWindowingSystem createDefault() {
		return new SkijaWindowingSystemImp();
	}
	
}
