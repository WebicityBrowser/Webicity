package com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.imp;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatTracker;

public class FloatTrackerImp implements FloatTracker {

	private static final Comparator<Rectangle> rectangleComparator = (r1, r2) -> {
		int result = Float.compare(r1.position().y(), r2.position().y());
		if (result == 0) {
			result = Float.compare(r1.position().x(), r2.position().x());
		}

		return result;
	};

	private final Set<Rectangle> leftFloats = new TreeSet<Rectangle>(rectangleComparator);
	private final Set<Rectangle> rightFloats = new TreeSet<Rectangle>(rectangleComparator);

	@Override
	public void addLeftFloat(Rectangle rect) {
		leftFloats.add(rect);
	}

	@Override
	public void addRightFloat(Rectangle rect) {
		rightFloats.add(rect);
	}

	@Override
	public float getLeftInlineOffset(float blockStart) {
		float highestOffset = 0;
		for (Rectangle rect : leftFloats) {
			if (blockStart >= rect.position().y() && blockStart < rect.position().y() + rect.size().height()) {
				highestOffset = Math.max(highestOffset, rect.position().x() + rect.size().width());
			}
		}

		return highestOffset;
	}

	@Override
	public float getRightInlineOffset(float blockStart, float inlineEnd) {
		if (inlineEnd == RelativeDimension.UNBOUNDED) return 0;
		float highestOffset = 0;
		for (Rectangle rect : rightFloats) {
			if (blockStart >= rect.position().y() && blockStart < rect.position().y() + rect.size().height()) {
				if (rect.position().x() == RelativeDimension.UNBOUNDED) continue;
				highestOffset = Math.max(highestOffset, inlineEnd - rect.position().x());
			}
		}

		return highestOffset;
	}

	@Override
	public float getClearedLeftBlockPosition(float blockStart) {
		return getFreePosition(blockStart, 0, leftFloats);
	}

	@Override
	public float getClearedRightBlockPosition(float blockStart) {
		return getFreePosition(blockStart, 0, rightFloats);
	}

	@Override
	public float getFitBlockPosition(float blockStart, float inlineEnd, AbsoluteSize itemSize) {
		// TODO: Simplify this method
		Rectangle[] leftRectangles = leftFloats.toArray(Rectangle[]::new);
		Rectangle[] rightRectangles = rightFloats.toArray(Rectangle[]::new);
		float currentY = blockStart;
		int leftPos = getInitialSearchPos(leftRectangles, currentY);
		int rightPos = getInitialSearchPos(rightRectangles, currentY);
		float successWindow = -1;
		while (true) {
			if (leftPos == -1 && rightPos == -1) {
				return successWindow == -1 ? currentY : successWindow;
			}

			float leftX = leftPos == -1 ? 0 : leftRectangles[leftPos].position().x() + leftRectangles[leftPos].size().width();
			float rightX = rightPos == -1 ? inlineEnd : rightRectangles[rightPos].position().x();
			if (rightX - leftX >= itemSize.width() && successWindow == -1) {
				successWindow = currentY;
			} else if (rightX - leftX < itemSize.width()) {
				successWindow = -1;
			}

			if (successWindow != -1 && currentY - successWindow >= itemSize.height()) {
				return successWindow;
			}

			float leftEnd = leftPos == -1 ? -1 : leftRectangles[leftPos].position().y() + leftRectangles[leftPos].size().height();
			float rightEnd = rightPos == -1 ? -1 : rightRectangles[rightPos].position().y() + rightRectangles[rightPos].size().height();
			if (leftPos != -1 && rightPos != -1) {
				if (leftEnd < rightEnd) {
					currentY = leftEnd;
					leftPos++;
				} else {
					currentY = rightEnd;
					rightPos++;
				}
			} else if (leftPos != -1) {
				currentY = leftEnd;
				leftPos++;
			} else {
				currentY = rightEnd;
				rightPos++;
			}

			if (leftPos >= leftRectangles.length) leftPos = -1;
			if (rightPos >= rightRectangles.length) rightPos = -1;
		}
	}

	private float getFreePosition(float blockStart, float minFreeSize, Set<Rectangle> floats) {
		float nextUncheckedY = -1;
		for (Rectangle rect : floats) {
			if (blockStart >= rect.position().y() && blockStart < rect.position().y() + rect.size().height()) {
				nextUncheckedY = Math.max(nextUncheckedY, rect.position().y() + rect.size().height());
			} else if (nextUncheckedY != -1 && rect.position().y() >= nextUncheckedY + minFreeSize) {
				return nextUncheckedY;
			} else if (nextUncheckedY != -1) {
				nextUncheckedY = Math.max(nextUncheckedY, rect.position().y() + rect.size().height());
			}
		}

		if (nextUncheckedY == -1) {
			return blockStart;
		}

		return nextUncheckedY;
	}
	
	private int getInitialSearchPos(Rectangle[] rectangles, float currentY) {
		for (int i = 0; i < rectangles.length; i++) {
			if (rectangles[i].position().y() + rectangles[i].size().height() > currentY) {
				return i;
			}
		}

		return -1;
	}

}
