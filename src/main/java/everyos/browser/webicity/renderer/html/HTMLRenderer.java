package everyos.browser.webicity.renderer.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicity.renderer.html.dom.Document;
import everyos.browser.webicity.renderer.html.dom.MutationListener;
import everyos.browser.webicity.renderer.html.dom.Node;
import everyos.browser.webicity.renderer.html.parser.HTMLParser;
import everyos.browser.webicity.webribbon.WebComponentWrapper;
import everyos.browser.webicity.webribbon.component.WebComponent;
import everyos.engine.ribbon.shape.Location;

public class HTMLRenderer implements Renderer {
	@Override public void execute(WebicityFrame frame, InputStream stream) throws IOException {
		System.out.println("HTML");
		long time = System.currentTimeMillis();
		HTMLParser parser = new HTMLParser();
		parser.parse(new BufferedReader(new InputStreamReader(stream, "UTF-8")));
		System.out.println("TIME: "+(System.currentTimeMillis()-time));
		stream.close();
		
		Document doc = parser.getDocument();
		HTMLScreen screen = new HTMLScreen();
		doc.bind(screen);
		
		new WebComponentWrapper(frame)
			.attribute("ui", screen.getComponent())
			.attribute("size", new Location(1, 0, 1, 0));
	}
}

class HTMLScreen implements MutationListener {
	private WebComponent component;

	@Override public void onBind(Node el) {
		this.component = new WebComponent(el);
	}

	@Override public void onChildrenMutate(Node el, MutationType type) {
		
	}

	@Override public void onNodeMutate(Node el) {
		
	}

	@Override public void onUnbind(Node el) {}
	
	public WebComponent getComponent() {
		return component;
	}
}