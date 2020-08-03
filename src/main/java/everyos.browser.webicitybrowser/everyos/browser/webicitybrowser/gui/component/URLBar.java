package everyos.browser.webicitybrowser.gui.component;

import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.renderer.guirenderer.directive.FontSizeDirective;

public class URLBar extends BlockComponent {
	private String text = "";
	
	public URLBar(Component parent) {
		super(parent);
		
		this
			.directive(FontSizeDirective.of(14));
	}

	public URLBar text(String text) {
		this.text = text;
		invalidate();
		return this;
	}
	public String getText() {
		return this.text;
	}
}
