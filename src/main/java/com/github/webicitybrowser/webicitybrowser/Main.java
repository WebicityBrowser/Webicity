package com.github.webicitybrowser.webicitybrowser;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.thready.windowing.skija.SkijaWindowingSystem;

public class Main {

	public static void main(String[] args) {
		
		SkijaWindowingSystem renderingSystem = SkijaWindowingSystem.createDefault();
		renderingSystem.createWindow(window -> {
			window
				.getScreen()
				.setScreenContent(new ScreenContent() {

					@Override
					public boolean redrawRequested() {
						return false;
					}

					@Override
					public void redraw(Canvas2D canvas, AbsoluteSize size) {
						canvas.drawRect(0, 0, 50, size.getHeight());
					}
					
				});
		});
		
	}
	
}
