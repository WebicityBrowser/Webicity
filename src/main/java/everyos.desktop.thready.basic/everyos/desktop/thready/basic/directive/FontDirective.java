package everyos.desktop.thready.basic.directive;

import everyos.desktop.thready.core.graphics.text.FontInfo;
import everyos.desktop.thready.core.gui.directive.Directive;

public interface FontDirective extends Directive {

	FontInfo getFont();
	
	public static FontDirective of(FontInfo font) {
		return new FontDirective() {
			@Override
			public Class<? extends Directive> getDirectiveClass() {
				return FontDirective.class;
			}
			
			@Override
			public FontInfo getFont() {
				return font;
			}
		};
	}
	
}
