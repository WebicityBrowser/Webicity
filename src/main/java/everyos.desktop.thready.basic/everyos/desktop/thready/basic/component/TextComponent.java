package everyos.desktop.thready.basic.component;

import everyos.desktop.thready.core.gui.InvalidationLevel;

public class TextComponent extends ComponentBase {

	private String text = "";
	
	public TextComponent text(String text) {
		this.text = text;
		invalidate(InvalidationLevel.RENDER);
		
		return this;
	}
	
	public String getText() {
		return this.text;
	}
	
}
