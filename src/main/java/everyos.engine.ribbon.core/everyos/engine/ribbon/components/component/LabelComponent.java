package everyos.engine.ribbon.components.component;

import everyos.engine.ribbon.core.graphics.InvalidationLevel;

public class LabelComponent extends ContainerComponent {
	
	private String text = "";

	public LabelComponent text(String text) {
		this.text = text;
		invalidate(InvalidationLevel.RENDER);
		return this;
	}
	
	public String getText() {
		return this.text;
	}
	
}
