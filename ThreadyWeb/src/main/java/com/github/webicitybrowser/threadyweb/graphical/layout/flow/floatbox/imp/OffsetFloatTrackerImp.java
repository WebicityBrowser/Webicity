package com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.imp;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatTracker;

public class OffsetFloatTrackerImp implements FloatTracker {

	private final FloatTracker delegate;
	private final float blockOffset;

	public OffsetFloatTrackerImp(FloatTracker delegate, float offset) {
		this.delegate = delegate;
		this.blockOffset = offset;
	}

	@Override
	public void addLeftFloat(Rectangle rect) {
		Rectangle offsetRect = new Rectangle(
			new AbsolutePosition(rect.position().x(), rect.position().y() + blockOffset),
			rect.size()
		);

		delegate.addLeftFloat(offsetRect);
	}

	@Override
	public void addRightFloat(Rectangle rect) {
		Rectangle offsetRect = new Rectangle(
			new AbsolutePosition(rect.position().x(), rect.position().y() + blockOffset),
			rect.size()
		);

		delegate.addRightFloat(offsetRect);
	}

	@Override
	public float getClearedLeftBlockPosition(float blockStart) {
		return delegate.getClearedLeftBlockPosition(blockStart + blockOffset) - blockOffset;
	}

	@Override
	public float getClearedRightBlockPosition(float blockStart) {
		return delegate.getClearedRightBlockPosition(blockStart + blockOffset) - blockOffset;
	}

	@Override
	public float getLeftInlineOffset(float blockStart) {
		return delegate.getLeftInlineOffset(blockStart + blockOffset);
	}

	@Override
	public float getRightInlineOffset(float blockStart, float inlineEnd) {
		return delegate.getRightInlineOffset(blockStart + blockOffset, inlineEnd);
	}

	@Override
	public float getFitBlockPosition(float blockStart, float inlineEnd, AbsoluteSize itemSize) {
		return delegate.getFitBlockPosition(blockStart + blockOffset, inlineEnd, itemSize) - blockOffset;
	}

	public static FloatTracker offset(FloatTracker delegate, float blockOffset) {
		if (delegate instanceof OffsetFloatTrackerImp) {
			OffsetFloatTrackerImp offsetDelegate = (OffsetFloatTrackerImp) delegate;
			return new OffsetFloatTrackerImp(offsetDelegate.delegate, blockOffset + offsetDelegate.blockOffset);
		}

		return new OffsetFloatTrackerImp(delegate, blockOffset);
	}
	
}
