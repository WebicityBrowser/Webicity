package everyos.browser.webicitybrowser.gui.component;

import everyos.browser.webicitybrowser.gui.appearence.HorizontalBean;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.FontSizeDirective;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;

public class CircularText extends TextButton {

	public CircularText(String text) {
		super(text, new HorizontalBean());
	}

	public CircularText() {
		this("");
	}

}
