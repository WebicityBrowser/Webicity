package com.github.webicitybrowser.thready.windowing.skija;

import com.github.webicitybrowser.thready.windowing.core.WindowingSystem;
import com.github.webicitybrowser.thready.windowing.skija.imp.SkijaWindowingSystemImp;

public interface SkijaWindowingSystem extends WindowingSystem {
	
	public static SkijaWindowingSystem createDefault() {
		return new SkijaWindowingSystemImp();
	}
	
}
