package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.layout.flow.context.block;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.directive.HeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.WidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.block.FlowBlockRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.imp.BuildableRenderedUnitImp;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestFontMetrics;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubBlockBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubContentBox;

public class FlowBlockRendererTest {

	private final DirectivePool emptyDirectivePool = new BasicDirectivePool();
	private final Font2D testFont = createTestFont();
	
	@Test
	@DisplayName("Can render empty box")
	public void canRenderEmptyBox() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(0, 0), result.fitSize());
		Assertions.assertEquals(0, result.childLayoutResults().length);
	}

	@Test
	@DisplayName("Can render box with child box")
	public void canRenderBoxWithChildBox() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), null);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
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
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
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
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
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
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = FlowBlockRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(40, 30), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(40, 30), childLayoutResult.relativeRect().size());
	}

	private GlobalRenderContext mockGlobalRenderContext() {
		ResourceLoader resourceLoader = Mockito.mock(ResourceLoader.class);
		Mockito.when(resourceLoader.loadFont(Mockito.any())).thenReturn(testFont);

		GlobalRenderContext renderContext = Mockito.mock(GlobalRenderContext.class);
		Mockito.when(renderContext.viewportSize()).thenReturn(new AbsoluteSize(1000, 1000));
		Mockito.when(renderContext.resourceLoader()).thenReturn(resourceLoader);

		return renderContext;
	}

	private LocalRenderContext createLocalRenderContext() {
		return LocalRenderContext.create(
			new AbsoluteSize(50, 50),
			testFont.getMetrics(),
			new ContextSwitch[0]);
	}

	private FlowRenderContext createRenderContext(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		return new FlowRenderContext(
			box, globalRenderContext, localRenderContext,
			(directives) -> new BuildableRenderedUnitImp(null, directives));
	}

	private Font2D createTestFont() {
		Font2D font = Mockito.mock(Font2D.class);
		Mockito.when(font.getMetrics()).thenReturn(new TestFontMetrics());

		return font;
	}


}
