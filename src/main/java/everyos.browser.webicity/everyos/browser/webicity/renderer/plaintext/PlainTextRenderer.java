package everyos.browser.webicity.renderer.plaintext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.renderer.Renderer;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.component.TextBoxComponent;
import everyos.engine.ribbon.renderer.guirenderer.directive.PositionDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;

public class PlainTextRenderer implements Renderer {
	//TODO: WhatWG actually specifies how this should be done
	@Override public void execute(WebicityFrame frame, InputStream stream) throws IOException {
		//TODO: Specialized rendering context, to prevent lag on browser UI
		Component text = new TextBoxComponent(frame.innerFrame)
			.attribute("font", "Courier")
			.directive(PositionDirective.of(new Location(0, 5, 0, 0)))
			.directive(SizeDirective.of(new Location(1, -10, 1, 0)));
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		StringBuilder builder = new StringBuilder();
		int chi = -1;
		//TODO: We should have a thread to render while processing	
		while (!frame.tasks.hasQuit()&&(chi=reader.read())!=-1) builder.appendCodePoint(chi);
		stream.close();
		
		frame.engine.createThread(()->text.attribute("text", builder.toString()));
	}
}
