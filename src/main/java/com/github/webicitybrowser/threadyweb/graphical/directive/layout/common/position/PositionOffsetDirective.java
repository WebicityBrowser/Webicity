package com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public interface PositionOffsetDirective extends Directive {
	
	SizeCalculation getSizeCalculation();

	public static interface LeftPositionOffsetDirective extends PositionOffsetDirective {

		@Override
		default Class<? extends Directive> getPrimaryType() {
			return LeftPositionOffsetDirective.class;
		}

	}

	public static interface RightPositionOffsetDirective extends PositionOffsetDirective {

		@Override
		default Class<? extends Directive> getPrimaryType() {
			return RightPositionOffsetDirective.class;
		}

	}

	public static interface TopPositionOffsetDirective extends PositionOffsetDirective {

		@Override
		default Class<? extends Directive> getPrimaryType() {
			return TopPositionOffsetDirective.class;
		}

	}

	public static interface BottomPositionOffsetDirective extends PositionOffsetDirective {

		@Override
		default Class<? extends Directive> getPrimaryType() {
			return BottomPositionOffsetDirective.class;
		}

	}

	static LeftPositionOffsetDirective ofLeft(SizeCalculation positionCalculation) {
		return () -> positionCalculation;
	}

	static RightPositionOffsetDirective ofRight(SizeCalculation positionCalculation) {
		return () -> positionCalculation;
	}

	static TopPositionOffsetDirective ofTop(SizeCalculation positionCalculation) {
		return () -> positionCalculation;
	}

	static BottomPositionOffsetDirective ofBottom(SizeCalculation positionCalculation) {
		return () -> positionCalculation;
	}

}
