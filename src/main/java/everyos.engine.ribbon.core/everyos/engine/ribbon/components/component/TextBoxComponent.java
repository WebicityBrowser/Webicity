package everyos.engine.ribbon.components.component;

import everyos.engine.ribbon.core.graphics.InvalidationLevel;

public class TextBoxComponent extends BlockComponent {
	private String text = "";

	public TextBoxComponent text(String text) {
		this.text = text;
		invalidate(InvalidationLevel.RENDER);
		return this;
	}
	public String getText() {
		return this.text;
	}
}
