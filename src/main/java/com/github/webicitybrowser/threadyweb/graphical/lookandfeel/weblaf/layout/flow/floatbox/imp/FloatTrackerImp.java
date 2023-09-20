package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.imp;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.FloatTracker;

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
	public float getFitBlockPosition(float blockStart, float inlineSize, float blockSize) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getFitBlockPosition'");
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
	
}
