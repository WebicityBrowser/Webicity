package com.github.webicitybrowser.threadyweb.graphical.layout.adjusted;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.FlowingLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionOffsetDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.position.PositionTypeDirective;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowTestUtils;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubBlockBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubContentBox;
import com.github.webicitybrowser.threadyweb.graphical.value.PositionType;

public class AdjustedLayoutManagerTest {

	private final DirectivePool emptyDirectivePool = new BasicDirectivePool();

	private SolidLayoutManager layoutManager;

	@BeforeEach
	public void setup() {
		layoutManager = new AdjustedLayoutManager(new FlowingLayoutManager());
	}

	@Test
	@DisplayName("Can render empty box")
	public void canRenderEmptyBox() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = layoutManager.render(new LayoutManagerContext(
			box, box.getChildrenTracker().getChildren(), globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(0, 0), result.fitSize());
		Assertions.assertEquals(0, result.childLayoutResults().length);
	}

	@Test
	@DisplayName("Can render box with static child box")
	public void canRenderBoxWithChildBox() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), emptyDirectivePool);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = layoutManager.render(new LayoutManagerContext(
			box, box.getChildrenTracker().getChildren(), globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childResult.relativeRect().position());
	}

	@Test
	@DisplayName("Can render box with relative child box")
	public void canRenderBoxWithRelativeChildBox() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool childDirectivePool = new BasicDirectivePool();
		childDirectivePool.directive(PositionTypeDirective.of(PositionType.RELATIVE));
		childDirectivePool.directive(PositionOffsetDirective.ofLeft(_1 -> 5));
		Box childBox = new TestStubContentBox(true, new AbsoluteSize(10, 10), childDirectivePool);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = FlowTestUtils.mockGlobalRenderContext();
		LocalRenderContext localRenderContext = FlowTestUtils.createLocalRenderContext();
		LayoutResult result = layoutManager.render(new LayoutManagerContext(
			box, box.getChildrenTracker().getChildren(), globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(50, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(5, 0), childResult.relativeRect().position());
	}

}
