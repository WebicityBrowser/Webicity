package everyos.engine.ribbon.components.accessibility.alt;

import everyos.engine.ribbon.core.accessibility.alt.AltLabel;

public class UnlocalizedAltLabel implements AltLabel {
	
	private final String content;

	public UnlocalizedAltLabel(String content) {
		this.content = content;
	}

	@Override
	public String getContent() {
		return this.content;
	}
	
}
