package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.layout.table;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.render.RenderCacheImp;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderCache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.table.TableInnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestFontMetrics;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubChildrenBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestStubContentBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class TableInnerDisplayLayoutTest {

	private static final DirectivePool emptyDirectivePool = new BasicDirectivePool();
	private static final DirectivePool defaultChildDirectivePool = new BasicDirectivePool();
	private final Font2D testFont = createTestFont();

	private TableInnerDisplayLayout tableInnerDisplayLayout;

	@BeforeAll
	public static void setupAll() {
//		defaultChildDirectivePool.directive()
	}

	@BeforeEach
	public void setup() {
		tableInnerDisplayLayout = new TableInnerDisplayLayout();
	}

	@Test
	public void canRenderTable() {
		ChildrenBox box = new TestStubChildrenBox(emptyDirectivePool);
		Box childBox1 = new TestStubContentBox(false, new AbsoluteSize(10,20), defaultChildDirectivePool);
		Box childBox2 = new TestStubContentBox(false, new AbsoluteSize(10,20), defaultChildDirectivePool);
		box.getChildrenTracker().addChild(childBox1);
		box.getChildrenTracker().addChild(childBox2);
		LocalRenderContext localRenderContext = createLocalRenderContext();
		LayoutResult result = render(box, localRenderContext);
		System.out.println(result.childLayoutResults()[0].relativeRect());
	}

	private LayoutResult render(ChildrenBox box, LocalRenderContext localRenderContext) {
		GlobalRenderContext globalRenderContext = mockGlobalRenderContext();
		return tableInnerDisplayLayout.render(new LayoutManagerContext(
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
