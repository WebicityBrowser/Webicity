package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.layout.flow.context.inline;

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
import com.github.webicitybrowser.threadyweb.graphical.directive.PaddingDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.HeightDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.WidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.FlowInlineRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.imp.BuildableRenderedUnitImp;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled.StyledUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextDisplay;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestFontMetrics;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubChildrenBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubContentBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubInlineBox;

public class FlowInlineRendererTest {

	private final DirectivePool emptyDirectivePool = new BasicDirectivePool();
	private final Font2D testFont = createTestFont();
	
	@Test
	@DisplayName("Can render empty box")
	public void canRenderEmptyBox() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(0, 0), result.fitSize());
		Assertions.assertEquals(0, result.childLayoutResults().length);
	}

	@Test
	@DisplayName("Can render box with child inline content box")
	public void canRenderBoxWithChildSolidBox() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 10), emptyDirectivePool);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
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
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
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
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
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
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
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
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
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
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(10, 10));
		LayoutResult result = FlowInlineRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
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
		TextBox textBox = createTextBox("Hello  World");
		box.getChildrenTracker().addChild(textBox);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(100, 100));
		LayoutResult result = FlowInlineRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult child = result.childLayoutResults()[0];
		// 11 characters, 8 pixels per character - "Hello World"
		Assertions.assertEquals(8 * 11, child.unit().fitSize().width());
	}

	@Test
	@DisplayName("Text spaces at start are removed (default settings)")
	public void textSpacesAtStartAreRemoved() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		TextBox textBox = createTextBox(" Hello World");
		box.getChildrenTracker().addChild(textBox);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(100, 100));
		LayoutResult result = FlowInlineRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult child = result.childLayoutResults()[0];
		Assertions.assertEquals(8 * 11, child.unit().fitSize().width());
	}

	@Test
	@DisplayName("Large text wraps")
	public void largeTextWraps() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		TextBox textBox = createTextBox("Hello World");
		box.getChildrenTracker().addChild(textBox);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(50, 100));
		LayoutResult result = FlowInlineRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult child1 = result.childLayoutResults()[0];
		ChildLayoutResult child2 = result.childLayoutResults()[1];
		Assertions.assertEquals(8 * 6, child1.unit().fitSize().width());
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
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
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
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = FlowInlineRenderer.render(createRenderContext(box, globalRenderContext, localRenderContext));
		Assertions.assertEquals(new AbsoluteSize(40, 10), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(40, 10), childLayoutResult.relativeRect().size());
		StyledUnit styledUnit = (StyledUnit) childLayoutResult.unit();
		Assertions.assertEquals(new AbsolutePosition(15, 0), styledUnit.context().innerUnitPosition());
		Assertions.assertEquals(new AbsoluteSize(10, 10), styledUnit.context().innerUnitSize());

	}

	private GlobalRenderContext mockGlobalRenderContext() {
		ResourceLoader resourceLoader = Mockito.mock(ResourceLoader.class);
		Mockito.when(resourceLoader.loadFont(Mockito.any())).thenReturn(testFont);

		GlobalRenderContext renderContext = Mockito.mock(GlobalRenderContext.class);
		Mockito.when(renderContext.viewportSize()).thenReturn(new AbsoluteSize(1000, 1000));
		Mockito.when(renderContext.resourceLoader()).thenReturn(resourceLoader);

		return renderContext;
	}

	private LocalRenderContext createLocalRenderContext(AbsoluteSize size) {
		return LocalRenderContext.create(
			size,
			testFont.getMetrics(),
			new ContextSwitch[0]);
	}

	private FlowRenderContext createRenderContext(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		return new FlowRenderContext(
			box, globalRenderContext, localRenderContext,
			directives -> new BuildableRenderedUnitImp(null, directives),
			context -> new StyledUnit(null, context));
	}

	private TextBox createTextBox(String text) {
		Font2D font = createTestFont();

		return new TextBox(new TextDisplay(), null, emptyDirectivePool, text, font);
	}

	private Font2D createTestFont() {
		Font2D font = Mockito.mock(Font2D.class);
		Mockito.when(font.getMetrics()).thenReturn(new TestFontMetrics());

		return font;
	}

}
