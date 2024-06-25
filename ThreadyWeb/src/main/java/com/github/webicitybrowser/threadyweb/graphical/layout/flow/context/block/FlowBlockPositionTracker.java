package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public class FlowBlockPositionTracker {
	
	private AbsolutePosition position = AbsolutePosition.ZERO_POSITION;
	private float yMargin = 0;
	private float fitWidth = 0;

	public AbsolutePosition addBox(AbsoluteSize boxSize, float[] margins) {
		yMargin = Math.max(yMargin, margins[2]);
		float newYPosition = position.y() + yMargin + boxSize.height();
		float boxYPosition = newYPosition - boxSize.height();
		float boxXPosition = position.x() + margins[0];
		position = new AbsolutePosition(position.x(), newYPosition);

		float lineWidth = boxXPosition + boxSize.width() + margins[1];
		fitWidth = Math.max(fitWidth, lineWidth);

		yMargin = margins[3];

		return new AbsolutePosition(boxXPosition, boxYPosition);
	}

	public AbsoluteSize fitSize() {
		return new AbsoluteSize(fitWidth, position.y() + yMargin);
	}

	public AbsolutePosition getPosition() {
		return position;
	}

}
