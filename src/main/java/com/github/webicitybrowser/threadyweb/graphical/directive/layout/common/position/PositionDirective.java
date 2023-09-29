package com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface PositionDirective extends Directive {
	
	SizeCalculation getSizeCalculation();

	public static interface LeftPositionDirective extends PositionDirective {

		@Override
		default Class<? extends Directive> getPrimaryType() {
			return LeftPositionDirective.class;
		}

	}

	public static interface RightPositionDirective extends PositionDirective {

		@Override
		default Class<? extends Directive> getPrimaryType() {
			return RightPositionDirective.class;
		}

	}

	public static interface TopPositionDirective extends PositionDirective {

		@Override
		default Class<? extends Directive> getPrimaryType() {
			return TopPositionDirective.class;
		}

	}

	public static interface BottomPositionDirective extends PositionDirective {

		@Override
		default Class<? extends Directive> getPrimaryType() {
			return BottomPositionDirective.class;
		}

	}

	static LeftPositionDirective ofLeft(SizeCalculation positionCalculation) {
		return () -> positionCalculation;
	}

	static RightPositionDirective ofRight(SizeCalculation positionCalculation) {
		return () -> positionCalculation;
	}

	static TopPositionDirective ofTop(SizeCalculation positionCalculation) {
		return () -> positionCalculation;
	}

	static BottomPositionDirective ofBottom(SizeCalculation positionCalculation) {
		return () -> positionCalculation;
	}

}
