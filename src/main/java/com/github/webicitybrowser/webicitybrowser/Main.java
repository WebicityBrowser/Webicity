package com.github.webicitybrowser.webicitybrowser;

import java.io.IOException;
import java.io.StringReader;

import com.github.webicitybrowser.thready.windowing.core.GraphicsSystem;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.thready.windowing.skija.SkijaGraphicsSystem;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererBackend;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.core.ThreadyRendererFrontend;
import com.github.webicitybrowser.webicity.renderer.frontend.thready.html.style.ThreadyHTMLRendererFrontent;

public class Main {

	public static void main(String[] args) {
		String html = "<!doctype html><html><head></head><body><a>ATime<a>by</a><div>Once</div>there</a></body></html>";
		HTMLRendererBackend backend = createBackend(html);
		ThreadyRendererFrontend frontend = new ThreadyHTMLRendererFrontent(backend);
		
		createGUIFor(frontend.getContent());
	}

	private static HTMLRendererBackend createBackend(String html) {
		try {
			return new HTMLRendererBackend(new StringReader(html));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static void createGUIFor(ScreenContent content) {
		GraphicsSystem graphicsSystem = SkijaGraphicsSystem.createDefault();
		
		graphicsSystem.createWindow(window -> {
			window
				.getScreen()
				.setScreenContent(content);
		});
	}
	
}
