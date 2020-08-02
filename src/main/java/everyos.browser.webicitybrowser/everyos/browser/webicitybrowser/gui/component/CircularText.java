package everyos.browser.webicitybrowser.gui.component;

import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;

public class CircularText extends BlockComponent {
	private String text;

	public CircularText(Component parent) {
		super(parent);
		
		this
			.attribute("font-size", 14);
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
