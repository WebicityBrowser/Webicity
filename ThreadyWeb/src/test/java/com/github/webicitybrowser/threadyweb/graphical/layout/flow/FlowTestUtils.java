package com.github.webicitybrowser.threadyweb.graphical.layout.flow;

import org.mockito.Mockito;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.render.RenderCacheImp;
import com.github.webicitybrowser.thready.gui.graphical.base.layout.StaticTreeTracker;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderCache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.directive.derived.DerivedFontDirective;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.imp.BuildableRenderedUnitImp;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled.StyledUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestFontMetrics;

public class FlowTestUtils {

	private static final Font2D testFont = createTestFont();
	private static final DirectivePool baseDirectivePool = createBasicDirectivePool();
	
	public static GlobalRenderContext mockGlobalRenderContext() {
		ResourceLoader resourceLoader = Mockito.mock(ResourceLoader.class);
		Mockito.when(resourceLoader.loadFont(Mockito.any())).thenReturn(testFont);
		RenderCache renderCache = new RenderCacheImp();

		GlobalRenderContext renderContext = Mockito.mock(GlobalRenderContext.class);
		Mockito.when(renderContext.viewportSize()).thenReturn(new AbsoluteSize(1000, 1000));
		Mockito.when(renderContext.resourceLoader()).thenReturn(resourceLoader);
		Mockito.when(renderContext.renderCache()).thenReturn(renderCache);
		Mockito.when(renderContext.rootFontMetrics()).thenReturn(new TestFontMetrics());

		return renderContext;
	}

	public static LocalRenderContext createLocalRenderContext() {
		return createLocalRenderContext(new AbsoluteSize(50, 50));
	}

	public static LocalRenderContext createLocalRenderContext(AbsoluteSize size) {
		return LocalRenderContext.create(size, new ContextSwitch[0]);
	}

	public static LocalRenderContext createLocalRenderContext(AbsoluteSize size, ContextSwitch[] switches) {
		return LocalRenderContext.create(size, switches);
	}

	public static FlowConfig createFlowConfig() {
		return new FlowConfig(
			box -> new BuildableRenderedUnitImp(null, box),
			context -> new StyledUnit(context, null)
		);
	}

	public static LayoutRenderContext createRenderContext(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		return new LayoutRenderContext(
			globalRenderContext, localRenderContext,
			new StaticTreeTracker(box, box.getChildrenTracker().getChildren()));
	}

	public static TextBox createTextBox(String text, DirectivePool directives) {
		Font2D font = createTestFont();

		if (directives == null) directives = baseDirectivePool;
		TextBox textBox = Mockito.mock(TextBox.class);
		Mockito.when(textBox.getFont(Mockito.any(), Mockito.any())).thenReturn(font);
		Mockito.when(textBox.styleDirectives()).thenReturn(directives);
		Mockito.when(textBox.text()).thenReturn(text);

		return textBox;
	}

	public static TextBox createTextBox(String text) {
		return createTextBox(text, null);
	}

	public static DirectivePool createBasicDirectivePool() {
		return new BasicDirectivePool()
			.directive(DerivedFontDirective.of(testFont));
	}

	public static Font2D createTestFont() {
		Font2D font = Mockito.mock(Font2D.class);
		Mockito.when(font.getMetrics()).thenReturn(new TestFontMetrics());

		return font;
	}

}
