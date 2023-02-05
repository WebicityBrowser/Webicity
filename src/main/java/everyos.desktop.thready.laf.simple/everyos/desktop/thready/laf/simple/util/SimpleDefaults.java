package everyos.desktop.thready.laf.simple.util;

import everyos.desktop.thready.core.graphics.text.FontDecoration;
import everyos.desktop.thready.core.graphics.text.FontInfo;
import everyos.desktop.thready.core.graphics.text.FontWeight;
import everyos.desktop.thready.core.graphics.text.NamedFont;

public final class SimpleDefaults {

	public static FontInfo FONT = new FontInfo(
		new NamedFont("Times New Roman"),
		16, FontWeight.NORMAL, new FontDecoration[0]);
	
	private SimpleDefaults() {}
	
}
