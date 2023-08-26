package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.layout.flow.block.context.inline;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.FlowFluidRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubChildrenBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubContentBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubInlineBox;

public class FlowFluidRendererTest {
	
	@Test
	@DisplayName("Can render empty box")
	public void canRenderEmptyBox() {
		ChildrenBox box = new TestStubChildrenBox();
		GlobalRenderContext globalRenderContext = Mockito.mock(GlobalRenderContext.class);
		LocalRenderContext localRenderContext = LocalRenderContext.create(new AbsoluteSize(50, 50), new ContextSwitch[0]);
		InnerDisplayUnit result = FlowFluidRenderer.render(box, globalRenderContext, localRenderContext, null);
		Assertions.assertEquals(new AbsoluteSize(0, 0), result.preferredSize());
		Assertions.assertEquals(0, result.childLayoutResults().length);
	}

	@Test
	@DisplayName("Can render box with child inline content box")
	public void canRenderBoxWithChildSolidBox() {
		ChildrenBox box = new TestStubChildrenBox();
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), null);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = Mockito.mock(GlobalRenderContext.class);
		LocalRenderContext localRenderContext = LocalRenderContext.create(new AbsoluteSize(50, 50), new ContextSwitch[0]);
		InnerDisplayUnit result = FlowFluidRenderer.render(box, globalRenderContext, localRenderContext, null);
		Assertions.assertEquals(new AbsoluteSize(10, 10), result.preferredSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 10), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with two child inline content boxes")
	public void canRenderBoxWithTwoChildInlineContentBoxes() {
		ChildrenBox box = new TestStubChildrenBox();
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 10), null);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(20, 20), null);
		box.getChildrenTracker().addChild(childBox1);
		box.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = Mockito.mock(GlobalRenderContext.class);
		LocalRenderContext localRenderContext = LocalRenderContext.create(new AbsoluteSize(50, 50), new ContextSwitch[0]);
		InnerDisplayUnit result = FlowFluidRenderer.render(box, globalRenderContext, localRenderContext, null);
		Assertions.assertEquals(new AbsoluteSize(30, 20), result.preferredSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 10), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(10, 0), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(20, 20), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with two child inline content boxes that overflow")
	public void canRenderBoxWithTwoChildInlineContentBoxesThatOverflow() {
		ChildrenBox box = new TestStubChildrenBox();
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(50, 10), null);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(50, 20), null);
		box.getChildrenTracker().addChild(childBox1);
		box.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = Mockito.mock(GlobalRenderContext.class);
		LocalRenderContext localRenderContext = LocalRenderContext.create(new AbsoluteSize(50, 50), new ContextSwitch[0]);
		InnerDisplayUnit result = FlowFluidRenderer.render(box, globalRenderContext, localRenderContext, null);
		Assertions.assertEquals(new AbsoluteSize(50, 30), result.preferredSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(50, 10), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(0, 10), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(50, 20), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with very large child inline content box")
	public void canRenderBoxWithVeryLargeChildInlineContentBox() {
		ChildrenBox box = new TestStubChildrenBox();
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(100, 100), null);
		box.getChildrenTracker().addChild(childBox1);
		GlobalRenderContext globalRenderContext = Mockito.mock(GlobalRenderContext.class);
		LocalRenderContext localRenderContext = LocalRenderContext.create(new AbsoluteSize(50, 50), new ContextSwitch[0]);
		InnerDisplayUnit result = FlowFluidRenderer.render(box, globalRenderContext, localRenderContext, null);
		Assertions.assertEquals(new AbsoluteSize(100, 100), result.preferredSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(100, 100), childLayoutResult1.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with child inline content boxes inside inline children box")
	public void canRenderBoxWithChildInlineContentBoxesInsideInlineChildrenBox() {
		ChildrenBox box = new TestStubChildrenBox();
		ChildrenBox containerBox = new TestStubInlineBox();
		box.getChildrenTracker().addChild(containerBox);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 10), null);
		containerBox.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 10), null);
		containerBox.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = Mockito.mock(GlobalRenderContext.class);
		LocalRenderContext localRenderContext = LocalRenderContext.create(new AbsoluteSize(50, 50), new ContextSwitch[0]);
		InnerDisplayUnit result = FlowFluidRenderer.render(box, globalRenderContext, localRenderContext, null);
		Assertions.assertEquals(new AbsoluteSize(20, 10), result.preferredSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(20, 10), childLayoutResult1.relativeRect().size());
		BuildableRenderedUnit buildableUnit = (BuildableRenderedUnit) childLayoutResult1.unit();
		Assertions.assertEquals(2, buildableUnit.childLayoutResults().length);
		ChildLayoutResult childUnitEntry1 = buildableUnit.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childUnitEntry1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 10), childUnitEntry1.relativeRect().size());
		ChildLayoutResult childUnitEntry2 = buildableUnit.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(10, 0), childUnitEntry2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 10), childUnitEntry2.relativeRect().size());
	}

	@Test
	@DisplayName("Can split nested across lines")
	public void canSplitNestedAcrossLines() {
		ChildrenBox box = new TestStubChildrenBox();
		ChildrenBox containerBox = new TestStubInlineBox();
		box.getChildrenTracker().addChild(containerBox);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 10), null);
		containerBox.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(15, 10), null);
		containerBox.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = Mockito.mock(GlobalRenderContext.class);
		LocalRenderContext localRenderContext = LocalRenderContext.create(new AbsoluteSize(10, 10), new ContextSwitch[0]);
		InnerDisplayUnit result = FlowFluidRenderer.render(box, globalRenderContext, localRenderContext, null);
		Assertions.assertEquals(new AbsoluteSize(15, 20), result.preferredSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 10), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(0, 10), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(15, 10), childLayoutResult2.relativeRect().size());
	}

}
