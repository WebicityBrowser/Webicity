package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.block;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.border.BorderWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.HeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MaxWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MinWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.WidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowTestUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled.StyledUnit;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubBlockBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubContentBox;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;

public class FlowBlockRendererTest {

	private final DirectivePool emptyDirectivePool = new BasicDirectivePool();
	
	@Test
	@DisplayName("Can render empty box")
	public void canRenderEmptyBox() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(0, 0), result.fitSize());
		Assertions.assertEquals(0, result.childLayoutResults().length);
	}

	@Test
	@DisplayName("Can render box with child box")
	public void canRenderBoxWithChildBox() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), null);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(50, 10), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with two child boxes")
	public void canRenderBoxWithTwoChildBoxes() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 10), null);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 10), null);
		box.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 20), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(50, 10), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(0, 10), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(50, 10), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with child box that overflows")
	public void canRenderBoxWithChildBoxThatOverflows() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(100, 10), null);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(100, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(100, 10), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Absolute length values are respected")
	public void absoluteLengthValuesAreRespected() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool styleDirectives = new BasicDirectivePool();
		styleDirectives.directive(WidthDirective.of(_1 -> 40));
		styleDirectives.directive(HeightDirective.of(_1 -> 30));
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), styleDirectives);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 30), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(40, 30), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can set a left margin")
	public void canSetALeftMargin() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool styleDirectives = new BasicDirectivePool();
		styleDirectives.directive(MarginDirective.ofLeft(_1 -> 10));
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), styleDirectives);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(10, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(40, 10), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can set a right margin")
	public void canSetARightMargin() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool styleDirectives = new BasicDirectivePool();
		styleDirectives.directive(MarginDirective.ofRight(_1 -> 10));
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), styleDirectives);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(40, 10), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can center a box")
	public void canCenterABox() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool styleDirectives = new BasicDirectivePool();
		styleDirectives.directive(WidthDirective.of(_1 -> 10));
		styleDirectives.directive(MarginDirective.ofLeft(SizeCalculation.SIZE_AUTO));
		styleDirectives.directive(MarginDirective.ofRight(SizeCalculation.SIZE_AUTO));
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), styleDirectives);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(20, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 10), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Top and bottom margins are collapsed")
	public void topAndBottomMarginsAreCollapsed() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool styleDirectives1 = new BasicDirectivePool();
		styleDirectives1.directive(MarginDirective.ofBottom(_1 -> 10));
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 10), styleDirectives1);
		box.getChildrenTracker().addChild(childBox1);
		DirectivePool styleDirectives2 = new BasicDirectivePool();
		styleDirectives2.directive(MarginDirective.ofTop(_1 -> 15));
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 10), styleDirectives2);
		box.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 35), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(50, 10), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(0, 25), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(50, 10), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Padding creates styled unit around content")
	public void paddingCreatesStyledUnitAroundContent() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool styleDirectives = new BasicDirectivePool();
		styleDirectives.directive(PaddingDirective.ofTop(_1 -> 10));
		styleDirectives.directive(PaddingDirective.ofLeft(_1 -> 5));
		styleDirectives.directive(PaddingDirective.ofBottom(_1 -> 15));
		styleDirectives.directive(PaddingDirective.ofRight(_1 -> 20));
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), styleDirectives);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 35), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertInstanceOf(StyledUnit.class, childLayoutResult.unit());
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(50, 35), childLayoutResult.relativeRect().size());
		StyledUnit styledUnit = (StyledUnit) childLayoutResult.unit();
		Assertions.assertEquals(new AbsoluteSize(25, 10), styledUnit.context().innerUnitSize());
		Assertions.assertEquals(new AbsolutePosition(5, 10), styledUnit.context().innerUnitPosition());
	}
	
	@Test
	@DisplayName("Box with padding and margin is properly positioned")
	public void boxWithPaddingAndMarginIsProperlyPositioned() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool styleDirectives = new BasicDirectivePool();
		styleDirectives.directive(WidthDirective.of(_1 -> 10));
		styleDirectives.directive(PaddingDirective.ofLeft(_1 -> 5));
		styleDirectives.directive(PaddingDirective.ofRight(_1 -> 5));
		styleDirectives.directive(MarginDirective.ofLeft(SizeCalculation.SIZE_AUTO));
		styleDirectives.directive(MarginDirective.ofRight(SizeCalculation.SIZE_AUTO));
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), styleDirectives);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertInstanceOf(StyledUnit.class, childLayoutResult.unit());
		Assertions.assertEquals(new AbsolutePosition(15, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(20, 10), childLayoutResult.relativeRect().size());
		StyledUnit styledUnit = (StyledUnit) childLayoutResult.unit();
		Assertions.assertEquals(new AbsoluteSize(10, 10), styledUnit.context().innerUnitSize());
		Assertions.assertEquals(new AbsolutePosition(5, 0), styledUnit.context().innerUnitPosition());
	}

	@Test
	@DisplayName("Box with max-width does not exceed max-width")
	public void boxWithMaxWidthDoesNotExceedMaxWidth() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool styleDirectives = new BasicDirectivePool();
		styleDirectives.directive(WidthDirective.of(_1 -> 10));
		styleDirectives.directive(MaxWidthDirective.of(_1 -> 5));
		TestStubContentBox childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), styleDirectives);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertInstanceOf(StyledUnit.class, childLayoutResult.unit());
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(5, 10), childLayoutResult.relativeRect().size());
		StyledUnit styledUnit = (StyledUnit) childLayoutResult.unit();
		Assertions.assertEquals(new AbsoluteSize(5, 10), styledUnit.context().innerUnitSize());
		Assertions.assertEquals(new AbsolutePosition(0, 0), styledUnit.context().innerUnitPosition());
		Assertions.assertEquals(5, childBox.getLastTargetSize().width());
	}

	@Test
	@DisplayName("Box with min-width does not underflow min-width")
	public void boxWithMinWidthDoesNotUnderflowMinWidth() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool styleDirectives = new BasicDirectivePool();
		styleDirectives.directive(WidthDirective.of(_1 -> 10));
		styleDirectives.directive(MinWidthDirective.of(_1 -> 15));
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), styleDirectives);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertInstanceOf(StyledUnit.class, childLayoutResult.unit());
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(15, 10), childLayoutResult.relativeRect().size());
		StyledUnit styledUnit = (StyledUnit) childLayoutResult.unit();
		Assertions.assertEquals(new AbsoluteSize(15, 10), styledUnit.context().innerUnitSize());
		Assertions.assertEquals(new AbsolutePosition(0, 0), styledUnit.context().innerUnitPosition());
	}

	@Test
	@DisplayName("Border widths create styled unit around content")
	public void borderWidthsCreateStyledUnitAroundContent() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool styleDirectives = new BasicDirectivePool();
		styleDirectives.directive(BorderWidthDirective.ofTop(_1 -> 10));
		styleDirectives.directive(BorderWidthDirective.ofLeft(_1 -> 5));
		styleDirectives.directive(BorderWidthDirective.ofBottom(_1 -> 15));
		styleDirectives.directive(BorderWidthDirective.ofRight(_1 -> 20));
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), styleDirectives);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		FlowRenderContext renderContext = FlowTestUtils.createRenderContext(box, globalRenderContext, FlowTestUtils.createLocalRenderContext());
		LayoutResult result = FlowBlockRenderer.render(renderContext);
		Assertions.assertEquals(new AbsoluteSize(50, 35), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertInstanceOf(StyledUnit.class, childLayoutResult.unit());
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(50, 35), childLayoutResult.relativeRect().size());
		StyledUnit styledUnit = (StyledUnit) childLayoutResult.unit();
		Assertions.assertEquals(new AbsoluteSize(25, 10), styledUnit.context().innerUnitSize());
		Assertions.assertEquals(new AbsolutePosition(5, 10), styledUnit.context().innerUnitPosition());
	}

}
