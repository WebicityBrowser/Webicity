package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.layout.flow.floatbox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.FloatDirective;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block.FlowBlockRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled.StyledUnit;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubBlockBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubChildrenBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubContentBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.layout.flow.FlowTestUtils;
import com.github.webicitybrowser.threadyweb.graphical.value.FloatDirection;

public class FloatLayoutTest {

	private final DirectivePool emptyDirectivePool = new BasicDirectivePool();
	private final UIDisplay<?, ?, ?> elementDisplay = new ElementDisplay();
	
	@Test
	@DisplayName("Can render block context with left float before other content")
	public void canRenderBlockContextWithLeftFloatBeforeOtherContent() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool floatDirectivePool = new BasicDirectivePool();
		floatDirectivePool.directive(FloatDirective.of(FloatDirection.LEFT));
		Box floatBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), floatDirectivePool);
		box.getChildrenTracker().addChild(floatBox);
		ChildrenBox inlineBox = new TestStubChildrenBox(emptyDirectivePool, elementDisplay);
		Box textBox = FlowTestUtils.createTextBox("Hi", emptyDirectivePool);
		inlineBox.getChildrenTracker().addChild(textBox);
		box.getChildrenTracker().addChild(inlineBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		FlowRenderContext renderContext = FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext);
		LayoutResult result = FlowBlockRenderer.render(renderContext);
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult floatResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsoluteSize(10, 10), floatResult.relativeRect().size());
		Assertions.assertEquals(new AbsolutePosition(0, 0), floatResult.relativeRect().position());
		ChildLayoutResult inlineResult = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsoluteSize(50, 14), inlineResult.relativeRect().size());
		ChildLayoutResult[] layoutResults = ((ElementUnit) ((StyledUnit) inlineResult.unit()).context().innerUnit())
			.layoutResults().childLayoutResults();
		Assertions.assertEquals(1, layoutResults.length);
		Assertions.assertEquals(new AbsoluteSize(16, 14), layoutResults[0].relativeRect().size());
		Assertions.assertEquals(new AbsolutePosition(10, 0), layoutResults[0].relativeRect().position());
	}

	@Test
	@DisplayName("Can render block context with right float before other content")
	public void canRenderBlockContextWithRightFloatBeforeOtherContent() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool floatDirectivePool = new BasicDirectivePool();
		floatDirectivePool.directive(FloatDirective.of(FloatDirection.RIGHT));
		Box floatBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), floatDirectivePool);
		box.getChildrenTracker().addChild(floatBox);
		ChildrenBox inlineBox = new TestStubChildrenBox(emptyDirectivePool, elementDisplay);
		Box textBox = FlowTestUtils.createTextBox("Hi", emptyDirectivePool);
		inlineBox.getChildrenTracker().addChild(textBox);
		box.getChildrenTracker().addChild(inlineBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		FlowRenderContext renderContext = FlowTestUtils.createRenderContext(box, globalRenderContext, localRenderContext);
		LayoutResult result = FlowBlockRenderer.render(renderContext);
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult floatResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsoluteSize(10, 10), floatResult.relativeRect().size());
		Assertions.assertEquals(new AbsolutePosition(40, 0), floatResult.relativeRect().position());
		ChildLayoutResult inlineResult = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsoluteSize(50, 14), inlineResult.relativeRect().size());
		ChildLayoutResult[] layoutResults = ((ElementUnit) ((StyledUnit) inlineResult.unit()).context().innerUnit())
			.layoutResults().childLayoutResults();
		Assertions.assertEquals(1, layoutResults.length);
		Assertions.assertEquals(new AbsoluteSize(16, 14), layoutResults[0].relativeRect().size());
		Assertions.assertEquals(new AbsolutePosition(0, 0), layoutResults[0].relativeRect().position());
	}

}
