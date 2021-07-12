package everyos.browser.webicity.renderer.plaintext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.renderer.Renderer;

public class PlainTextRenderer implements Renderer {
	//TODO: WhatWG actually specifies how this should be done
	@Override 
	public void execute(WebicityFrame frame, InputStream stream) throws IOException {
		//TODO: Specialized rendering context, to prevent lag on browser UI
		/*TextBoxComponent text = new TextBoxComponent(frame.innerFrame)
			//.attribute("font", "Courier")
			.directive(PositionDirective.of(new Location(0, 5, 0, 0)))
			.directive(SizeDirective.of(new Location(1, -10, 1, 0)))
			.casted(TextBoxComponent.class);*/
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuilder builder = new StringBuilder();
		
		while (!frame.getTasks().isShutdown()) {
			final int read = reader.read();
			if (read==-1) break;
			
			builder.append(read);
		}
		stream.close();
		
		
	}

	@Override
	public String getTitle() {
		return null;
	}

	@Override
	public void addReadyHook(Runnable hook) {
		// TODO Auto-generated method stub
		
	}
}
