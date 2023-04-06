package com.github.webicitybrowser.webicitybrowser;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.imp.Paint2DBuilder;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.drawing.core.text.FontWeight;
import com.github.webicitybrowser.thready.drawing.core.text.source.NamedFontSource;
import com.github.webicitybrowser.thready.windowing.core.GraphicsSystem;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;

public class Main {

	public static void main(String[] args) {
		GraphicsSystem graphicsSystem = SkijaGraphicsSystem.createDefault();
		
		FontSettings settings = new FontSettings(12, FontWeight.NORMAL, new FontDecoration[] {});
		Font2D font = graphicsSystem.getResourceLoader().loadFont(new NamedFontSource("Times New Roman"), settings);
		
		graphicsSystem.createWindow(window -> {
			window
				.getScreen()
				.setScreenContent(new ScreenContent() {

					@Override
					public boolean redrawRequested() {
						return false;
					}

					@Override
					public void redraw(Canvas2D canvas, AbsoluteSize size) {
						Paint2D paint = new Paint2DBuilder()
							.setColor(Colors.WHITE)
							.build();
						canvas.setPaint(paint);
						canvas.drawRect(0, 0, size.getWidth(), size.getHeight());
						
						paint = new Paint2DBuilder()
								.setColor(Colors.BLACK)
								.setLoadedFont(font)
								.build();
						canvas.setPaint(paint);
						canvas.drawText(0, 0, "Hello, World!");
					}
					
				});
		});
		
	}
	
}
