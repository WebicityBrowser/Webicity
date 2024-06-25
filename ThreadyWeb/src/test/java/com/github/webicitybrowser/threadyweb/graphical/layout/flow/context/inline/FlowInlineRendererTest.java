package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.HeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.WidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flow.LineHeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LetterSpacingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LineBreakDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.TextAlignDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.TextAlignDirective.TextAlign;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowTestUtils;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.FloatTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.br.BreakBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled.StyledUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubChildrenBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubContentBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubInlineBox;

public class FlowInlineRendererTest {

	private final DirectivePool emptyDirectivePool = new BasicDirectivePool();
	
	@Test
	@DisplayName("Can render empty box")
	public void canRenderEmptyBox() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(0, 0), result.fitSize());
		Assertions.assertEquals(0, result.childLayoutResults().length);
	}

	@Test
	@DisplayName("Can render box with child inline content box")
	public void canRenderBoxWithChildSolidBox() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), emptyDirectivePool);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(10, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 10), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with two child inline content boxes")
	public void canRenderBoxWithTwoChildInlineContentBoxes() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 10), emptyDirectivePool);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(20, 20), emptyDirectivePool);
		box.getChildrenTracker().addChild(childBox1);
		box.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(30, 20), result.fitSize());
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
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(50, 10), emptyDirectivePool);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(50, 20), emptyDirectivePool);
		box.getChildrenTracker().addChild(childBox1);
		box.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 30), result.fitSize());
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
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(100, 100), emptyDirectivePool);
		box.getChildrenTracker().addChild(childBox1);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(100, 100), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(100, 100), childLayoutResult1.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with child inline content boxes inside inline children box")
	public void canRenderBoxWithChildInlineContentBoxesInsideInlineChildrenBox() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		ChildrenBox containerBox = new TestStubInlineBox();
		box.getChildrenTracker().addChild(containerBox);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 10), emptyDirectivePool);
		containerBox.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 10), emptyDirectivePool);
		containerBox.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(20, 10), result.fitSize());
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
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		ChildrenBox containerBox = new TestStubInlineBox();
		box.getChildrenTracker().addChild(containerBox);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 10), emptyDirectivePool);
		containerBox.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(15, 10), emptyDirectivePool);
		containerBox.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(10, 10));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(15, 20), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 10), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(0, 10), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(15, 10), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Text spaces are collapsed (default settings)")
	public void textSpacesAreCollapsed() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		TextBox textBox = FlowTestUtils.createTextBox("Hello  World");
		box.getChildrenTracker().addChild(textBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(100, 100));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult child = result.childLayoutResults()[0];
		// 11 characters, 8 pixels per character - "Hello World"
		Assertions.assertEquals(8 * 11, child.unit().fitSize().width());
	}

	@Test
	@DisplayName("Text spaces at start are removed (default settings)")
	public void textSpacesAtStartAreRemoved() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		TextBox textBox = FlowTestUtils.createTextBox(" Hello World");
		box.getChildrenTracker().addChild(textBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(100, 100));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult child = result.childLayoutResults()[0];
		Assertions.assertEquals(8 * 11, child.unit().fitSize().width());
	}

	@Test
	@DisplayName("Large text wraps")
	public void largeTextWraps() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		DirectivePool textBoxDirectives = new BasicDirectivePool();
		textBoxDirectives.directive(LineBreakDirective.of(LineBreakDirective.LineBreak.ANYWHERE));
		TextBox textBox = FlowTestUtils.createTextBox("Hello World", textBoxDirectives);
		box.getChildrenTracker().addChild(textBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(50, 100));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult child1 = result.childLayoutResults()[0];
		ChildLayoutResult child2 = result.childLayoutResults()[1];
		Assertions.assertEquals(8 * 6, child1.unit().fitSize().width());
		Assertions.assertEquals(8 * 5, child2.unit().fitSize().width());
	}

	@Test
	@DisplayName("Can render box with line breaks between words")
	public void canRenderBoxWithLineBreaksBetweenWords() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		TextBox textBox = FlowTestUtils.createTextBox("Hello World");
		box.getChildrenTracker().addChild(textBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		FlowRootContextSwitch contextSwitch = new FlowRootContextSwitch(new AbsolutePosition(0, 0), FloatContext.create(FloatTracker.create()));
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(
			new AbsoluteSize(70, 100), new ContextSwitch[] { contextSwitch });
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult child1 = result.childLayoutResults()[0];
		ChildLayoutResult child2 = result.childLayoutResults()[1];
		Assertions.assertEquals(8 * 5, child1.unit().fitSize().width());
		Assertions.assertEquals(8 * 5, child2.unit().fitSize().width());
	}

	@Test
	@DisplayName("Absolute length values are respected")
	public void absoluteLengthValuesAreRespected() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		DirectivePool directives = new BasicDirectivePool();
		directives.directive(WidthDirective.of(_1 -> 40));
		directives.directive(HeightDirective.of(_1 -> 30));
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), directives);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(40, 30), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(40, 30), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can add horizontal padding")
	public void canAddHorizontalPadding() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		DirectivePool directives = new BasicDirectivePool();
		directives.directive(PaddingDirective.ofLeft(_1 -> 15));
		directives.directive(PaddingDirective.ofRight(_1 -> 15));
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), directives);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(40, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(40, 10), childLayoutResult.relativeRect().size());
		StyledUnit styledUnit = (StyledUnit) childLayoutResult.unit();
		Assertions.assertEquals(new AbsolutePosition(15, 0), styledUnit.context().innerUnitPosition());
		Assertions.assertEquals(new AbsoluteSize(10, 10), styledUnit.context().innerUnitSize());
	}

	@Test
	@DisplayName("Letter spacing is respected")
	public void letterSpacingIsRespected() {
		DirectivePool directives = new BasicDirectivePool();
		directives.directive(LetterSpacingDirective.of(_1 -> 1));
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		TextBox textBox = FlowTestUtils.createTextBox("Hello World", directives);
		box.getChildrenTracker().addChild(textBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(100, 100));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult child = result.childLayoutResults()[0];
		Assertions.assertEquals(8 * 11 + 1 * 10, child.unit().fitSize().width());
	}

	@Test
	@DisplayName("Preceding left padding is respected")
	public void precedingLeftPaddingIsRespected() {
		FloatTracker floatTracker = FloatTracker.create();
		floatTracker.addLeftFloat(new Rectangle(new AbsolutePosition(0, 0), new AbsoluteSize(10, 10)));
		AbsolutePosition childPosition = new AbsolutePosition(0, 0);
		FloatContext floatContext = FloatContext.create(floatTracker);
		FlowRootContextSwitch contextSwitch = new FlowRootContextSwitch(childPosition, floatContext);
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		TextBox textBox = FlowTestUtils.createTextBox("Hello World");
		box.getChildrenTracker().addChild(textBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(
			new AbsoluteSize(100, 100), new ContextSwitch[] { contextSwitch });
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult child = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(10, 0), child.relativeRect().position());
	}

	@Test
	@DisplayName("Preceding right padding is respected")
	public void precedingRightPaddingIsRespected() {
		FloatTracker floatTracker = FloatTracker.create();
		floatTracker.addRightFloat(new Rectangle(new AbsolutePosition(48, 0), new AbsoluteSize(10, 1)));
		AbsolutePosition childPosition = new AbsolutePosition(0, 0);
		FloatContext floatContext = FloatContext.create(floatTracker);
		FlowRootContextSwitch contextSwitch = new FlowRootContextSwitch(childPosition, floatContext);
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		TextBox textBox = FlowTestUtils.createTextBox("Hello World");
		box.getChildrenTracker().addChild(textBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(
			new AbsoluteSize(58, 100), new ContextSwitch[] { contextSwitch });
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult child = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), child.relativeRect().position());
		Assertions.assertEquals(5 * 8, child.unit().fitSize().width());
	}

	@Test
	@DisplayName("Line height is respected")
	public void lineHeightIsRespected() {
		DirectivePool directives = new BasicDirectivePool();
		directives.directive(LineHeightDirective.of(_1 -> 13));
		ChildrenBox box = new TestStubChildrenBox(directives);
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), emptyDirectivePool);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(10, 13), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 10), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can manually break line")
	public void canBreakLine() {
		DirectivePool directives = new BasicDirectivePool();
		ChildrenBox box = new TestStubChildrenBox(directives);
		TextBox textBox1 = FlowTestUtils.createTextBox("Hello");
		box.getChildrenTracker().addChild(textBox1);
		for (int i = 0; i < 2; i++) {
			BreakBox breakBox = Mockito.mock(BreakBox.class);
			Mockito.when(breakBox.getAdjustedBoxTree()).thenReturn(List.of(breakBox));
			Mockito.when(breakBox.isFluid()).thenReturn(true);
			Mockito.when(breakBox.styleDirectives()).thenReturn(emptyDirectivePool);
			box.getChildrenTracker().addChild(breakBox);
		}
		TextBox textBox2 = FlowTestUtils.createTextBox("World");
		box.getChildrenTracker().addChild(textBox2);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(100, 100));
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(8 * 5, 14), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(0, 28), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(8 * 5, 14), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Can align line right")
	public void canAlignLineRight() {
		DirectivePool directives = new BasicDirectivePool();
		directives.directive(TextAlignDirective.of(TextAlign.RIGHT));
		ChildrenBox box = new TestStubChildrenBox(directives);
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), emptyDirectivePool);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext(new AbsoluteSize(50, 50), new ContextSwitch[0]);
		LayoutResult result = FlowInlineRenderer.render(FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(10, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(40, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 10), childLayoutResult.relativeRect().size());
	}

}
