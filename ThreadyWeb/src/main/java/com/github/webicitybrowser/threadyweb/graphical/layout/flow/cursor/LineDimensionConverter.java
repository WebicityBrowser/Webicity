package com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension.LineDirection;

public final class LineDimensionConverter {
	
	private LineDimensionConverter() {}

	public static AbsoluteSize convertToAbsoluteSize(LineDimension lineDimension) {
		return switch (lineDimension.direction()) {
			case LTR, RTL -> new AbsoluteSize(lineDimension.run(), lineDimension.depth());
			case TTB, BTT -> new AbsoluteSize(lineDimension.depth(), lineDimension.run());
		};
	}

	public static AbsolutePosition convertToAbsolutePosition(LineDimension lineDimension, AbsoluteSize parentSize, AbsoluteSize objectSize) {
		return switch (lineDimension.direction()) {
			case LTR -> new AbsolutePosition(lineDimension.run(), lineDimension.depth());
			case RTL -> new AbsolutePosition(parentSize.width() - lineDimension.run() - objectSize.width(), lineDimension.depth());
			case TTB -> new AbsolutePosition(lineDimension.depth(), lineDimension.run());
			case BTT -> new AbsolutePosition(lineDimension.depth(), parentSize.height() - lineDimension.run() - objectSize.height());
		};
	}

	public static LineDimension convertToLineDimension(AbsoluteSize dimensions, LineDirection lineDirection) {
		return switch (lineDirection) {
			case LTR, RTL -> new LineDimension(dimensions.width(), dimensions.height(), lineDirection);
			case TTB, BTT -> new LineDimension(dimensions.height(), dimensions.width(), lineDirection);
		};
	}

	public static LineDimension convertToLineDimension(AbsolutePosition position, LineDirection lineDirection) {
		return switch (lineDirection) {
			case LTR -> new LineDimension(position.x(), position.y(), lineDirection);
			case RTL -> new LineDimension(position.x(), position.y(), lineDirection);
			case TTB -> new LineDimension(position.y(), position.x(), lineDirection);
			case BTT -> new LineDimension(position.y(), position.x(), lineDirection);
		};
	}

}
