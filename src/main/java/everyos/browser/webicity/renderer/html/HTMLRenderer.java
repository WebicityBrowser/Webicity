package everyos.browser.webicity.renderer.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.dom.Document;
import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicity.renderer.html.parser.HTMLParser;
import everyos.browser.webicity.webribbon.WebComponentWrapper;
import everyos.engine.ribbon.shape.Location;

public class HTMLRenderer implements Renderer {
	@Override public void execute(WebicityFrame frame, InputStream stream) throws IOException {
		System.out.println("HTML");
		long time = System.currentTimeMillis();
		HTMLParser parser = new HTMLParser();
		parser.parse(new BufferedReader(new InputStreamReader(stream)));
		System.out.println("TIME: "+(System.currentTimeMillis()-time));
		stream.close();
		
		Document doc = parser.getDocument();
		new WebComponentWrapper(frame)
			.attribute("ui", doc.component())
			.attribute("size", new Location(1, 0, 1, 0));
	}
}
