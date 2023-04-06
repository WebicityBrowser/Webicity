package com.github.webicitybrowser.webicitybrowser;

import com.github.webicitybrowser.thready.windowing.skija.SkijaWindowingSystem;

public class Main {

	public static void main(String[] args) {
		
		SkijaWindowingSystem renderingSystem = SkijaWindowingSystem.createDefault();
		renderingSystem.createWindow(window -> {
			
		});
		
	}
	
}
