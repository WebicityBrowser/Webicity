package com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.imp;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatTracker;

public class OffsetFloatTrackerImp implements FloatTracker {

	private final FloatTracker delegate;
	private final AbsolutePosition offset;

	public OffsetFloatTrackerImp(FloatTracker delegate, AbsolutePosition offset) {
		this.delegate = delegate;
		this.offset = offset;
	}

	@Override
	public void addLeftFloat(Rectangle rect) {
		Rectangle offsetRect = new Rectangle(
			AbsoluteDimensionsMath.sum(rect.position(), offset, AbsolutePosition::new),
			rect.size()
		);

		delegate.addLeftFloat(offsetRect);
	}

	@Override
	public void addRightFloat(Rectangle rect) {
		Rectangle offsetRect = new Rectangle(
			AbsoluteDimensionsMath.sum(rect.position(), offset, AbsolutePosition::new),
			rect.size()
		);

		delegate.addRightFloat(offsetRect);
	}

	@Override
	public float getClearedLeftBlockPosition(float blockStart) {
		return delegate.getClearedLeftBlockPosition(blockStart + offset.y()) - offset.y();
	}

	@Override
	public float getClearedRightBlockPosition(float blockStart) {
		return delegate.getClearedRightBlockPosition(blockStart + offset.y()) - offset.y();
	}

	@Override
	public float getLeftInlineOffset(float blockStart) {
		return Math.max(0, delegate.getLeftInlineOffset(blockStart + offset.y()) - offset.x());
	}

	@Override
	public float getRightInlineOffset(float blockStart, float inlineEnd) {
		return Math.max(0, delegate.getRightInlineOffset(blockStart + offset.y(), inlineEnd + offset.x()) - offset.x());
	}

	@Override
	public float getFitBlockPosition(float blockStart, float inlineEnd, AbsoluteSize itemSize) {
		return delegate.getFitBlockPosition(blockStart + offset.y(), inlineEnd + offset.x(), itemSize) - offset.y();
	}

	public static FloatTracker offset(FloatTracker delegate, AbsolutePosition offset) {
		if (delegate instanceof OffsetFloatTrackerImp) {
			OffsetFloatTrackerImp offsetDelegate = (OffsetFloatTrackerImp) delegate;
			return new OffsetFloatTrackerImp(offsetDelegate.delegate, new AbsolutePosition(
				offsetDelegate.offset.x() + offset.x(),
				offsetDelegate.offset.y() + offset.y()));
		}

		return new OffsetFloatTrackerImp(delegate, offset);
	}
	
}
