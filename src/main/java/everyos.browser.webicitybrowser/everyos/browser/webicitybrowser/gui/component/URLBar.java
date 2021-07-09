package everyos.browser.webicitybrowser.gui.component;

import java.util.function.Consumer;

import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.directive.FontSizeDirective;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;

public class URLBar extends BlockComponent {
	private String text = "";
	private Consumer<String> action;
	
	public URLBar(Component parent) {
		super();
		
		this
			.directive(FontSizeDirective.of(14));
	}

	public URLBar text(String text) {
		this.text = text;
		invalidate(InvalidationLevel.PAINT);
		return this;
	}
	public String getText() {
		return this.text;
	}

	public void setAction(Consumer<String> action) {
		this.action = action;
	}
	public Consumer<String> getAction() {
		return this.action;
	}
}
