package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.stage.box;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.CloneBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.box.InlineBoxChildrenTracker;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubInlineBox;

public class InlineBoxChildrenTrackerTest {
	
	private CloneBox parent;
	private InlineBoxChildrenTracker tracker;

	@BeforeEach
	public void setup() {
		parent = new TestStubInlineBox();
		tracker = (InlineBoxChildrenTracker) parent.getChildrenTracker();
	}

	@Test
	@DisplayName("Can add child to tracker")
	public void canAddChildToTracker() {
		Box box = Mockito.mock(Box.class);
		Mockito.when(box.isFluid()).thenReturn(true);
		Mockito.when(box.getAdjustedBoxTree()).thenReturn(List.of(box));

		tracker.addChild(box);
		Assertions.assertEquals(1, tracker.getChildren().size());
		Assertions.assertEquals(box, tracker.getChildren().get(0));
	}

	@Test
	@DisplayName("Added children are adjusted")
	public void addedChildrenAreAdjusted() {
		Box box = Mockito.mock(Box.class);
		Mockito.when(box.isFluid()).thenReturn(true);
		Box adjustedBox = Mockito.mock(Box.class);
		Mockito.when(adjustedBox.isFluid()).thenReturn(true);
		Mockito.when(box.getAdjustedBoxTree()).thenReturn(List.of(adjustedBox));

		tracker.addChild(box);
		Assertions.assertEquals(1, tracker.getChildren().size());
		Assertions.assertEquals(adjustedBox, tracker.getChildren().get(0));
	}

	@Test
	@DisplayName("Can add multiple children to tracker")
	public void canAddMultipleChildrenToTracker() {
		Box box1 = Mockito.mock(Box.class);
		Mockito.when(box1.isFluid()).thenReturn(true);
		Mockito.when(box1.getAdjustedBoxTree()).thenReturn(List.of(box1));

		Box box2 = Mockito.mock(Box.class);
		Mockito.when(box2.isFluid()).thenReturn(true);
		Mockito.when(box2.getAdjustedBoxTree()).thenReturn(List.of(box2));

		tracker.addChild(box1);
		tracker.addChild(box2);
		Assertions.assertEquals(2, tracker.getChildren().size());
		Assertions.assertEquals(box1, tracker.getChildren().get(0));
		Assertions.assertEquals(box2, tracker.getChildren().get(1));
	}
	
	@Test
	@DisplayName("Can get adjusted box tree with only fluids")
	public void canGetAdjustedBoxTreeWithOnlyFluids() {
		Box box1 = Mockito.mock(Box.class);
		Mockito.when(box1.isFluid()).thenReturn(true);
		Mockito.when(box1.getAdjustedBoxTree()).thenReturn(List.of(box1));

		Box box2 = Mockito.mock(Box.class);
		Mockito.when(box2.isFluid()).thenReturn(true);
		Mockito.when(box2.getAdjustedBoxTree()).thenReturn(List.of(box2));

		tracker.addChild(box1);
		tracker.addChild(box2);

		List<Box> adjustedBoxTree = tracker.getAdjustedBoxTree();
		Assertions.assertEquals(1, adjustedBoxTree.size());
		Assertions.assertEquals(parent, adjustedBoxTree.get(0));
	}

	@Test
	@DisplayName("Can get adjusted box tree with solids")
	public void canGetAdjustedBoxTreeWithSolids() {
		Box box1 = Mockito.mock(Box.class);
		Mockito.when(box1.isFluid()).thenReturn(false);
		Mockito.when(box1.getAdjustedBoxTree()).thenReturn(List.of(box1));

		Box box2 = Mockito.mock(Box.class);
		Mockito.when(box2.isFluid()).thenReturn(false);
		Mockito.when(box2.getAdjustedBoxTree()).thenReturn(List.of(box2));

		tracker.addChild(box1);
		tracker.addChild(box2);

		List<Box> adjustedBoxTree = tracker.getAdjustedBoxTree();
		Assertions.assertEquals(2, adjustedBoxTree.size());
		Assertions.assertEquals(box1, adjustedBoxTree.get(0));
		Assertions.assertEquals(box2, adjustedBoxTree.get(1));
	}

	@Test
	@DisplayName("Can get adjusted box tree with a fluid and a solid")
	public void canGetAdjustedBoxTreeWithAFluidAndASolid() {
		Box box1 = Mockito.mock(Box.class);
		Mockito.when(box1.isFluid()).thenReturn(true);
		Mockito.when(box1.getAdjustedBoxTree()).thenReturn(List.of(box1));

		Box box2 = Mockito.mock(Box.class);
		Mockito.when(box2.isFluid()).thenReturn(false);
		Mockito.when(box2.getAdjustedBoxTree()).thenReturn(List.of(box2));

		tracker.addChild(box1);
		tracker.addChild(box2);

		List<Box> adjustedBoxTree = tracker.getAdjustedBoxTree();
		Assertions.assertEquals(2, adjustedBoxTree.size());
		Assertions.assertEquals(box2, adjustedBoxTree.get(1));

		Box adjustedChild1 = adjustedBoxTree.get(0);
		Assertions.assertInstanceOf(CloneBox.class, adjustedChild1);
		Assertions.assertEquals(1, ((CloneBox) adjustedChild1).getChildrenTracker().getChildren().size());
	}

}
