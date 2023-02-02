package everyos.desktop.thready.basic.directive;

import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.gui.directive.Directive;

public interface ForegroundColorDirective extends Directive {

	ColorFormat getColor();

	public static Directive of(ColorFormat color) {
		return new ForegroundColorDirective() {
			
			@Override
			public Class<? extends Directive> getDirectiveClass() {
				return ForegroundColorDirective.class;
			}
			
			@Override
			public ColorFormat getColor() {
				return color;
			}
			
		};
	}
	
}
