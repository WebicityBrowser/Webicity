package everyos.browser.webicitybrowser.gui.component;

import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.renderer.guirenderer.directive.FontSizeDirective;

public class CircularText extends BlockComponent {
	private String text;

	public CircularText(Component parent) {
		super(parent);
		
		this
			.directive(FontSizeDirective.of(14));
	}

	public Component text(String text) {
		this.text = text;
		invalidate();
		return this;
	}
	
	public String getText() {
		return this.text;
	}
}
