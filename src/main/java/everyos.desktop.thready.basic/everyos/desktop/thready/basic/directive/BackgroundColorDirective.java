package everyos.desktop.thready.basic.directive;

import everyos.desktop.thready.core.graphics.color.RawColor;
import everyos.desktop.thready.core.gui.directive.Directive;

public interface BackgroundColorDirective extends Directive {

	RawColor getColor();

	public static Directive of(RawColor color) {
		return new BackgroundColorDirective() {
			
			@Override
			public Class<? extends Directive> getDirectiveClass() {
				return BackgroundColorDirective.class;
			}
			
			@Override
			public RawColor getColor() {
				return color;
			}
			
		};
	}
	
}
