package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatTracker;
import com.github.webicitybrowser.threadyweb.graphical.value.ClearDirection;

public class FlowBlockPositionTracker {
	
	private AbsolutePosition position = AbsolutePosition.ZERO_POSITION;
	private float yMargin = 0;
	private float fitWidth = 0;

	public AbsolutePosition nextBoxPosition(AbsoluteSize boxSize, float[] margins) {
		yMargin = Math.max(yMargin, margins[2]);
		float newYPosition = position.y() + yMargin + boxSize.height();
		float boxYPosition = newYPosition - boxSize.height();
		float boxXPosition = position.x() + margins[0];
		position = new AbsolutePosition(0, newYPosition);

		float lineWidth = boxXPosition + boxSize.width() + margins[1];
		fitWidth = Math.max(fitWidth, lineWidth);

		yMargin = margins[3];

		return new AbsolutePosition(boxXPosition, boxYPosition);
	}

	public void clearBoxPosition(ClearDirection floatClear, FloatTracker floatTracker) {
		if (floatClear == ClearDirection.NONE) return;

		float oldBoxYPosition = position.y();
		float boxYPosition = determineClearPosition(floatClear, floatTracker, oldBoxYPosition);
		if (boxYPosition > oldBoxYPosition + yMargin) {
			position = new AbsolutePosition(position.x(), boxYPosition);
			yMargin = 0;
		}
	}

	public AbsoluteSize fitSize() {
		return new AbsoluteSize(fitWidth, position.y() + yMargin);
	}

	public AbsolutePosition getPosition() {
		return position;
	}

	private float determineClearPosition(ClearDirection floatClear, FloatTracker floatTracker, float boxYPosition) {
		boolean clearLeft = floatClear == ClearDirection.LEFT || floatClear == ClearDirection.BOTH;
		boolean clearRight = floatClear == ClearDirection.RIGHT || floatClear == ClearDirection.BOTH;

		float leftClear = clearLeft ? floatTracker.getClearedLeftBlockPosition(boxYPosition) : 0;
		float rightClear = clearRight ? floatTracker.getClearedRightBlockPosition(boxYPosition) : 0;
		float totalClear = Math.max(leftClear, rightClear);
		
		return totalClear;
	}

}
