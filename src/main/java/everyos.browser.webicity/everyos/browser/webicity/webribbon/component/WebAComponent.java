package everyos.browser.webicity.webribbon.component;

import java.util.HashMap;

import everyos.browser.webicity.dom.Node;
import everyos.browser.webicity.webribbon.misc.DrawData;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;

public class WebAComponent extends WebComponent { //TODO: Code will be moved to WebUI
	public WebAComponent(Node node) {
		super(node);
	}
	
	public void calculateCascade() {
		attributes = new HashMap<>();
		attributes.put("display", "inline");
		attributes.put("color", Color.BLUE);
	};
	
	public void paint(GUIRenderer r, DrawData d) {
		//System.out.println("A");
		r.setColor(Color.BLUE);
		paintChildren(r, d);
	}
}
