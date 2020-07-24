package everyos.browser.webicity.webribbon.component;

import everyos.browser.webicity.renderer.html.dom.Node;
import everyos.browser.webicity.webribbon.misc.DrawData;
import everyos.browser.webicity.webribbon.shape.SizePosGroup;
import everyos.engine.ribbon.graphics.Renderer;

public class WebBreakComponent extends WebComponent { //TODO: Code will be moved to WebUI
	public WebBreakComponent(Node node) {
		super(node);
	}
	
	public void render(Renderer r, SizePosGroup sizepos) {
		calculateCascade();
		renderBefore(r, sizepos);
		sizepos.nextLine();
		renderAfter(r, sizepos);
	}
	
	public void paint(Renderer r, DrawData d) {
		
	}
}
