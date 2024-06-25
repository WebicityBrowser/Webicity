package com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox;

import com.github.webicitybrowser.thready.gui.directive.core.Directive;

public interface FlexDirectionDirective extends Directive {
	
	FlexDirection getFlexDirection();

	@Override
	default Class<? extends Directive> getPrimaryType() {
		return FlexDirectionDirective.class;
	}

	static FlexDirectionDirective of(FlexDirection flexDirection) {
		return () -> flexDirection;
	}

	enum FlexDirection {
		ROW, ROW_REVERSE, COLUMN, COLUMN_REVERSE;

        public boolean isHorizontal() {
            return this == ROW || this == ROW_REVERSE;
        }

		public boolean isVertical() {
			return this == COLUMN || this == COLUMN_REVERSE;
		}
	}

}
