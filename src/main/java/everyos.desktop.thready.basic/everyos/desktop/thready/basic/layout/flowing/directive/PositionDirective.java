package everyos.desktop.thready.basic.layout.flowing.directive;

import everyos.desktop.thready.core.gui.directive.Directive;
import everyos.desktop.thready.core.positioning.RelativePosition;

public interface PositionDirective extends Directive {

	RelativePosition getPosition();

	public static Directive of(RelativePosition position) {
		return new PositionDirective() {
			
			@Override
			public Class<? extends Directive> getDirectiveClass() {
				return PositionDirective.class;
			}
			
			@Override
			public RelativePosition getPosition() {
				return position;
			}
			
		};
	}
	
}
