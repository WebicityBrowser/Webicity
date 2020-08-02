package everyos.browser.webicitybrowser.gui.component;

import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;

public class URLBar extends BlockComponent {
	public URLBar(Component parent) {
		super(parent);
		
		this
			.attribute("font-size", 14);
	}
}
