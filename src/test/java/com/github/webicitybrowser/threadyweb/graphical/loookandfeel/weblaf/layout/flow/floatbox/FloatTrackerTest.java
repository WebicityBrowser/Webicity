package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.layout.flow.floatbox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.FloatTracker;

public class FloatTrackerTest {
	
	private FloatTracker floatTracker;

	@BeforeEach
	public void setup() {
		floatTracker = FloatTracker.create();
	}
	
	@Test
	@DisplayName("Can get clear left block position")
	public void canGetClearedLeftBlockPosition() {
		floatTracker.addLeftFloat(new Rectangle(new AbsolutePosition(0, 0), new AbsoluteSize(2, 5)));
		float clearedLeftBlockPosition = floatTracker.getClearedLeftBlockPosition(0);
		Assertions.assertEquals(5, clearedLeftBlockPosition);
	}

	@Test
	@DisplayName("Can get clear left block position with multiple floats")
	public void canGetClearedLeftBlockPositionWithMultipleFloats() {
		floatTracker.addLeftFloat(new Rectangle(new AbsolutePosition(0, 0), new AbsoluteSize(2, 5)));
		floatTracker.addLeftFloat(new Rectangle(new AbsolutePosition(2, 0), new AbsoluteSize(3, 5)));
		floatTracker.addLeftFloat(new Rectangle(new AbsolutePosition(0, 1), new AbsoluteSize(1, 5)));
		float clearedLeftBlockPosition = floatTracker.getClearedLeftBlockPosition(3);
		Assertions.assertEquals(6, clearedLeftBlockPosition);
	}

	@Test
	@DisplayName("Can get left inline offset")
	public void canGetLeftInlineOffset() {
		floatTracker.addLeftFloat(new Rectangle(new AbsolutePosition(0, 0), new AbsoluteSize(2, 5)));
		float leftInlineOffset = floatTracker.getLeftInlineOffset(0);
		Assertions.assertEquals(2, leftInlineOffset);
	}

	@Test
	@DisplayName("Can get left inline offset with multiple floats")
	public void canGetLeftInlineOffsetWithMultipleFloats() {
		floatTracker.addLeftFloat(new Rectangle(new AbsolutePosition(0, 0), new AbsoluteSize(2, 5)));
		floatTracker.addLeftFloat(new Rectangle(new AbsolutePosition(2, 0), new AbsoluteSize(3, 5)));
		floatTracker.addLeftFloat(new Rectangle(new AbsolutePosition(0, 1), new AbsoluteSize(1, 5)));
		float leftInlineOffset = floatTracker.getLeftInlineOffset(0);
		Assertions.assertEquals(5, leftInlineOffset);
	}

}
