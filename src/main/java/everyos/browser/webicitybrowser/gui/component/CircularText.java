package everyos.browser.webicitybrowser.gui.component;

import everyos.engine.ribbon.graphics.component.BlockComponent;
import everyos.engine.ribbon.graphics.component.Component;

public class CircularText extends BlockComponent {
	public CircularText(Component parent) {
		super(parent);
		
		this
			.attribute("font-size", 14);
	}
}
