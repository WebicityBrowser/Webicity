package com.github.webicitybrowser.threadyweb.graphical.layout.flexbox;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;

public record FlexDimension(float main, float cross, FlexDirection flexDirection) {

	public AbsolutePosition toAbsolutePosition() {
		return switch (flexDirection) {
			case ROW, ROW_REVERSE -> new AbsolutePosition(main, cross);
			case COLUMN, COLUMN_REVERSE -> new AbsolutePosition(cross, main);
		};
	}

	public AbsoluteSize toAbsoluteSize() {
		return switch (flexDirection) {
			case ROW, ROW_REVERSE -> new AbsoluteSize(main, cross);
			case COLUMN, COLUMN_REVERSE -> new AbsoluteSize(cross, main);
		};
	}

	public static FlexDimension createFrom(AbsoluteSize size, FlexDirection flexDirection) {
		return switch (flexDirection) {
			case ROW, ROW_REVERSE -> new FlexDimension(size.width(), size.height(), flexDirection);
			case COLUMN, COLUMN_REVERSE -> new FlexDimension(size.height(), size.width(), flexDirection);
		};
	}

	public static FlexDimension createFrom(AbsolutePosition position, FlexDirection flexDirection) {
		return switch (flexDirection) {
			case ROW, ROW_REVERSE -> new FlexDimension(position.x(), position.y(), flexDirection);
			case COLUMN, COLUMN_REVERSE -> new FlexDimension(position.y(), position.x(), flexDirection);
		};
	}

}
