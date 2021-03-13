package everyos.browser.webicitybrowser.gui.renderer;

import everyos.browser.webicity.renderer.html.HTMLRenderer;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.gui.WebComponentWrapper;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;

public class HTMLRendererGUI {
	private HTMLRenderer renderer;
	private Component displayPane;

	public HTMLRendererGUI(HTMLRenderer renderer) {
		this.renderer = renderer;
	}
	
	public void start() {
		this.displayPane = new BlockComponent(null);
		renderer.addReadyHook(()->{
			WebComponentWrapper innerPane = new WebComponentWrapper(null);
			innerPane.directive(SizeDirective.of(new Location(1, 0, 1, 0)));
			innerPane.ui(new WebComponent(renderer, renderer.getDocument()));
			displayPane.children(new Component[] {innerPane});
		});
	}
	
	public void cleanup() {
		
	}

	public Component getDisplayPane() {
		// TODO Auto-generated method stub
		return displayPane;
	}
}
