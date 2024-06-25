package com.github.webicitybrowser.threadyweb.graphical.layout.flexbox;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.render.RenderCacheImp;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderCache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.MarginDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.MaxWidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.common.size.WidthDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexGrowDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexJustifyContentDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexJustifyContentDirective.FlexJustifyContent;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexShrinkDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexWrapDirective.FlexWrap;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestFontMetrics;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubBlockBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubContentBox;

public class FlexInnerDisplayLayoutTest {
	
	private static final DirectivePool emptyDirectivePool = new BasicDirectivePool();
	private static final DirectivePool defaultChildDirectivePool = new BasicDirectivePool();
	private final Font2D testFont = createTestFont();

	private FlexInnerDisplayLayout flexInnerDisplayLayout;

	@BeforeAll
	public static void setupAll() {
		defaultChildDirectivePool.directive(FlexGrowDirective.of(1));
		defaultChildDirectivePool.directive(FlexShrinkDirective.of(1));
	}

	@BeforeEach
	public void setup() {
		flexInnerDisplayLayout = new FlexInnerDisplayLayout(context -> context.innerUnit());
	}

	@Test
	@DisplayName("Can render empty box in sized flex container")
	public void canRenderEmptyBoxInSizedFlexContainer() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = render(box, localRenderContext);;
		Assertions.assertEquals(new AbsoluteSize(0, 0), result.fitSize());
		Assertions.assertEquals(0, result.childLayoutResults().length);
	}

	@Test
	@DisplayName("Can render box with one child in sized flex container")
	public void canRenderBoxWithOneChildInSizedFlexContainer() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox);
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = render(box, localRenderContext);;
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
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox2);
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = render(box, localRenderContext);;
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
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox2);
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = render(box, localRenderContext);;
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
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(20, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(30, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox2);
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = render(box, localRenderContext);;
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
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(2, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(8, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox2);
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = render(box, localRenderContext);;
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
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(200, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(300, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox2);
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = render(box, localRenderContext);;
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
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox);
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(RelativeDimension.UNBOUNDED, 50));
		LayoutResult result = render(box, localRenderContext);;
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
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox2);
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(RelativeDimension.UNBOUNDED, 50));
		LayoutResult result = render(box, localRenderContext);;
		Assertions.assertEquals(new AbsoluteSize(20, 50), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 50), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(10, 0), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 50), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with two children in wrapped sized flex container")
	public void canRenderBoxWithTwoChildrenInWrappedSizedFlexContainer() {
		DirectivePool directivePool = new BasicDirectivePool();
		directivePool.directive(FlexWrapDirective.of(FlexWrap.WRAP));
		ChildrenBox box = new TestStubBlockBox(directivePool);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 50), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox2);
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(15, 50));
		LayoutResult result = render(box, localRenderContext);;
		Assertions.assertEquals(new AbsoluteSize(15, 100), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(15, 50), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(0, 50), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(15, 50), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Extra cross size is distributed to wrapped sized flex container")
	public void extraCrossSizeIsDistributedToWrappedSizedFlexContainer() {
		DirectivePool directivePool = new BasicDirectivePool();
		directivePool.directive(FlexWrapDirective.of(FlexWrap.WRAP));
		ChildrenBox box = new TestStubBlockBox(directivePool);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 10), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox1);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 10), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox2);
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(15, 100));
		LayoutResult result = render(box, localRenderContext);;
		Assertions.assertEquals(new AbsoluteSize(15, 100), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(15, 50), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(0, 50), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(15, 50), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with two children with different flex grow in sized flex container")
	public void canRenderBoxWithTwoChildrenWithDifferentFlexGrowInSizedFlexContainer() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool directivePool1 = new BasicDirectivePool();
		directivePool1.directive(FlexGrowDirective.of(1));
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 50), directivePool1);
		box.getChildrenTracker().addChild(childBox1);
		DirectivePool directivePool2 = new BasicDirectivePool();
		directivePool2.directive(FlexGrowDirective.of(2));
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 50), directivePool2);
		box.getChildrenTracker().addChild(childBox2);
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = render(box, localRenderContext);;
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
	@DisplayName("Can render box with two children with different flex shrink in sized flex container")
	public void canRenderBoxWithTwoChildrenWithDifferentFlexShrinkInSizedFlexContainer() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool directivePool1 = new BasicDirectivePool();
		directivePool1.directive(FlexShrinkDirective.of(2));
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 50), directivePool1);
		box.getChildrenTracker().addChild(childBox1);
		DirectivePool directivePool2 = new BasicDirectivePool();
		directivePool2.directive(FlexShrinkDirective.of(3));
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(20, 50), directivePool2);
		box.getChildrenTracker().addChild(childBox2);
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(5, 50));
		LayoutResult result = render(box, localRenderContext);;
		Assertions.assertEquals(new AbsoluteSize(5, 50), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(3.75f, 50), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(3.75f, 0), childLayoutResult2.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(1.25f, 50), childLayoutResult2.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with one child in sized flex container with centered justify content")
	public void canRenderBoxWithOneChildInSizedFlexContainerWithCenteredJustifyContent() {
		DirectivePool directivePool = new BasicDirectivePool();
		directivePool.directive(FlexJustifyContentDirective.of(FlexJustifyContent.CENTER));
		ChildrenBox box = new TestStubBlockBox(directivePool);
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 50), emptyDirectivePool);
		box.getChildrenTracker().addChild(childBox);
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = render(box, localRenderContext);;
		Assertions.assertEquals(new AbsoluteSize(50, 50), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(20, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(10, 50), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with one child with margin in sized flex container")
	public void canRenderBoxWithOneChildWithMarginInSizedFlexContainer() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool directivePool = new BasicDirectivePool();
		directivePool.directive(MarginDirective.ofLeft(_1 -> 5));
		directivePool.directive(FlexGrowDirective.of(1));
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 50), directivePool);
		box.getChildrenTracker().addChild(childBox);
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = render(box, localRenderContext);;
		Assertions.assertEquals(new AbsoluteSize(50, 50), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(5, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(45, 50), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with one child with set width in sized flex container")
	public void canRenderBoxWithOneChildWithSetWidthInSizedFlexContainer() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool directivePool = new BasicDirectivePool();
		directivePool.directive(WidthDirective.of(_1 -> 20));
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 50), directivePool);
		box.getChildrenTracker().addChild(childBox);
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = render(box, localRenderContext);;
		Assertions.assertEquals(new AbsoluteSize(50, 50), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(20, 50), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with one child with max size in sized flex container")
	public void canRenderBoxWithOneChildWithMaxSizeInSizedFlexContainer() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool directivePool = new BasicDirectivePool();
		directivePool.directive(MaxWidthDirective.of(_1 -> 5));
		Box childBox = new TestStubContentBox(false, new AbsoluteSize(10, 50), directivePool);
		box.getChildrenTracker().addChild(childBox);
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = render(box, localRenderContext);;
		Assertions.assertEquals(new AbsoluteSize(50, 50), result.fitSize());
		Assertions.assertEquals(1, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(5, 50), childLayoutResult.relativeRect().size());
	}

	@Test
	@DisplayName("Can render box with two children with flex grow and max size in sized flex container")
	public void canRenderBoxWithTwoChildrenWithFlexGrowAndMaxSizeInSizedFlexContainer() {
		ChildrenBox box = new TestStubBlockBox(emptyDirectivePool);
		DirectivePool directivePool1 = new BasicDirectivePool();
		directivePool1.directive(FlexGrowDirective.of(1));
		directivePool1.directive(MaxWidthDirective.of(_1 -> 20));
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10, 50), directivePool1);
		box.getChildrenTracker().addChild(childBox1);
		DirectivePool directivePool2 = new BasicDirectivePool();
		directivePool2.directive(FlexGrowDirective.of(1));
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10, 50), directivePool2);
		box.getChildrenTracker().addChild(childBox2);
		LocalRenderContext localRenderContext = createLocalRenderContext(new AbsoluteSize(50, 50));
		LayoutResult result = render(box, localRenderContext);;
		Assertions.assertEquals(new AbsoluteSize(50, 50), result.fitSize());
		Assertions.assertEquals(2, result.childLayoutResults().length);
		ChildLayoutResult childLayoutResult1 = result.childLayoutResults()[0];
		Assertions.assertEquals(new AbsolutePosition(0, 0), childLayoutResult1.relativeRect().position());
		Assertions.assertEquals(new AbsoluteSize(20, 50), childLayoutResult1.relativeRect().size());
		ChildLayoutResult childLayoutResult2 = result.childLayoutResults()[1];
		Assertions.assertEquals(new AbsolutePosition(20, 0), childLayoutResult2.relativeRect().position());
		// Floating point rounding error
		Assertions.assertEquals(30, childLayoutResult2.relativeRect().size().width(), 0.1f);
		Assertions.assertEquals(50, childLayoutResult2.relativeRect().size().height(), 0.1f);
	}

	private LayoutResult render(ChildrenBox box, LocalRenderContext localRenderContext) {
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		return flexInnerDisplayLayout.render(new LayoutManagerContext(
			box, box.getChildrenTracker().getChildren(),
			globalRenderContext, localRenderContext
		));
	}

	private GlobalRenderContext mockGlobalRenderContext() {
		ResourceLoader resourceLoader = Mockito.mock(ResourceLoader.class);
		Mockito.when(resourceLoader.loadFont(Mockito.any())).thenReturn(testFont);
		RenderCache renderCache = new RenderCacheImp();

		GlobalRenderContext renderContext = Mockito.mock(GlobalRenderContext.class);
		Mockito.when(renderContext.viewportSize()).thenReturn(new AbsoluteSize(1000, 5000));
		Mockito.when(renderContext.resourceLoader()).thenReturn(resourceLoader);
		Mockito.when(renderContext.renderCache()).thenReturn(renderCache);
		Mockito.when(renderContext.rootFontMetrics()).thenReturn(new TestFontMetrics());

		return renderContext;
	}

	private LocalRenderContext createLocalRenderContext() {
		return createLocalRenderContext(new AbsoluteSize(50, 50));
	}

	private LocalRenderContext createLocalRenderContext(AbsoluteSize sizeOverride) {
		return LocalRenderContext.create(sizeOverride, new ContextSwitch[0]);
	}

	private Font2D createTestFont() {
		Font2D font = Mockito.mock(Font2D.class);
		Mockito.when(font.getMetrics()).thenReturn(new TestFontMetrics());

		return font;
	}

}
