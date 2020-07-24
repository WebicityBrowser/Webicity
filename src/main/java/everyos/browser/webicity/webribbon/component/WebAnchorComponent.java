package everyos.browser.webicity.webribbon.component;

import java.util.HashMap;

import everyos.browser.webicity.renderer.html.dom.Node;
import everyos.browser.webicity.webribbon.misc.DrawData;
import everyos.engine.ribbon.graphics.Color;
import everyos.engine.ribbon.graphics.Renderer;

public class WebAnchorComponent extends WebComponent { //TODO: Code will be moved to WebUI
	public WebAnchorComponent(Node node) {
		super(node);
	}
	
	public void calculateCascade() {
		attributes = new HashMap<>();
		attributes.put("display", "inline");
		attributes.put("color", Color.BLUE);
	};
	
	public void paint(Renderer r, DrawData d) {
		//System.out.println("A");
		r.setColor(Color.BLUE);
		paintChildren(r, d);
	}
}
