package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.layout.flexbox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
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
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox.FlexInnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestFontMetrics;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubBlockBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubContentBox;

public class FlexInnerDisplayLayoutTest {
	
	private final DirectivePool emptyDirectivePool = new BasicDirectivePool();
	private final Font2D testFont = createTestFont();

	private FlexInnerDisplayLayout flexInnerDisplayLayout;

	@BeforeEach
	public void setup() {
		flexInnerDisplayLayout = new FlexInnerDisplayLayout(context -> context.innerUnit());
	}

	@Test
	@DisplayName("Can render empty box in sized flex container")
	public void canRenderEmptyBoxInSizedFlexContainer() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = flexInnerDisplayLayout.render(box, globalRenderContext, localRenderContext);
		Assertions.assertEquals(new AbsoluteSize(0, 0), result.fitSize());
		Assertions.assertEquals(0, result.childLayoutResults().length);
	}

	@Test
	@DisplayName("Can render box with one child in sized flex container")
	public void canRenderBoxWithOneChildInSizedFlexContainer() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 50), null);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = flexInnerDisplayLayout.render(box, globalRenderContext, localRenderContext);
		Assertions.assertEquals(new AbsoluteSize(50, 50), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(50, 50), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with two children in sized flex container")
	public void canRenderBoxWithTwoChildrenInSizedFlexContainer() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 50), null);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 50), null);
		box.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = flexInnerDisplayLayout.render(box, globalRenderContext, localRenderContext);
		Assertions.assertEquals(new AbsoluteSize(50, 50), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(25, 50), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(25, 0), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(25, 50), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with two children in sized flex container with column flex direction")
	public void canRenderBoxWithTwoChildrenInSizedFlexContainerWithColumnFlexDirection() {
		DirectivePool directivePool = new BasicDirectivePool();
		directivePool.directive(FlexDirectionDirective.of(FlexDirection.COLUMN));
		ChildrenBox box = new TestStubBlockBox(directivePool);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 50), null);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 50), null);
		box.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = flexInnerDisplayLayout.render(box, globalRenderContext, localRenderContext);
		Assertions.assertEquals(new AbsoluteSize(50, 50), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(50, 25), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(0, 25), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(50, 25), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with two children in sized flex container with different fit main sizes")
	public void canRenderBoxWithTwoChildrenInSizedFlexContainerWithDifferentFitMainSizes() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(20, 50), null);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(30, 50), null);
		box.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = flexInnerDisplayLayout.render(box, globalRenderContext, localRenderContext);
		Assertions.assertEquals(new AbsoluteSize(50, 50), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(20, 50), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(20, 0), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(30, 50), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with two children in sized flex container with small fit main sizes")
	public void canRenderBoxWithTwoChildrenInSizedFlexContainerWithSmallFitMainSizes() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(2, 50), null);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(8, 50), null);
		box.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = flexInnerDisplayLayout.render(box, globalRenderContext, localRenderContext);
		Assertions.assertEquals(new AbsoluteSize(50, 50), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(22, 50), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(22, 0), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(28, 50), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with two children in sized flex container with large fit main sizes")
	public void canRenderBoxWithTwoChildrenInSizedFlexContainerWithLargeFitMainSizes() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(200, 50), null);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(300, 50), null);
		box.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = flexInnerDisplayLayout.render(box, globalRenderContext, localRenderContext);
		Assertions.assertEquals(new AbsoluteSize(50, 50), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(20, 50), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(20, 0), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(30, 50), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with one child in unsized main axis flex container")
	public void canRenderBoxWithOneChildInUnsizedMainAxisFlexContainer() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 50), null);
		box.getChildrenTracker().addChild(childBox);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(RelativeDimension.UNBOUNDED, 50));
		LayoutResult result = flexInnerDisplayLayout.render(box, globalRenderContext, localRenderContext);
		Assertions.assertEquals(new AbsoluteSize(10, 50), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 50), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with two children in unsized main axis flex container")
	public void canRenderBoxWithTwoChildrenInUnsizedMainAxisFlexContainer() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 50), null);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 50), null);
		box.getChildrenTracker().addChild(childBox2);
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(RelativeDimension.UNBOUNDED, 50));
		LayoutResult result = flexInnerDisplayLayout.render(box, globalRenderContext, localRenderContext);
		Assertions.assertEquals(new AbsoluteSize(20, 50), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 50), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(10, 0), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 50), childLayoutResult2.relativeRect().size());
	}

	private GlobalRenderContext mockGlobalRenderContext() {
		ResourceLoader resourceLoader = Mockito.mock(ResourceLoader.class);
		Mockito.when(resourceLoader.loadFont(Mockito.any())).thenReturn(testFont);

		GlobalRenderContext renderContext = Mockito.mock(GlobalRenderContext.class);
		Mockito.when(renderContext.viewportSize()).thenReturn(new AbsoluteSize(1000, 5000));
		Mockito.when(renderContext.resourceLoader()).thenReturn(resourceLoader);

		return renderContext;
	}

	private LocalRenderContext createLocalRenderContext() {
		return createLocalRenderContext(new AbsoluteSize(50, 50));
	}

	private LocalRenderContext createLocalRenderContext(AbsoluteSize sizeOverride) {
		return LocalRenderContext.create(sizeOverride, testFont.getMetrics(), new ContextSwitch[0]);
	}

	private Font2D createTestFont() {
		Font2D font = Mockito.mock(Font2D.class);
		Mockito.when(font.getMetrics()).thenReturn(new TestFontMetrics());

		return font;
	}

}
