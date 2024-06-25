package com.github.webicitybrowser.threadyweb.graphical.layout.flow;

import org.mockito.Mockito;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.render.RenderCacheImp;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderCache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.imp.FloatContextImp;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.floatbox.imp.FloatTrackerImp;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.imp.BuildableRenderedUnitImp;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled.StyledUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextDisplay;
import com.github.webicitybrowser.threadyweb.graphical.loookandfeel.test.TestFontMetrics;

public class FlowTestUtils {

	private static final Font2D testFont = createTestFont();
	private static final DirectivePool emptyDirectivePool = new BasicDirectivePool();
	
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

	public static FlowRenderContext createRenderContext(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		FlowRootContextSwitch flowRootContextSwitch = new FlowRootContextSwitch(
			AbsolutePosition.ZERO_POSITION,
			new FloatContextImp(new FloatTrackerImp()));
		for (ContextSwitch contextSwitch : localRenderContext.contextSwitches()) {
			if (contextSwitch instanceof FlowRootContextSwitch) {
				flowRootContextSwitch = (FlowRootContextSwitch) contextSwitch;
			}
		}

		LayoutManagerContext layoutManagerContext = new LayoutManagerContext(
			box, box.getChildrenTracker().getChildren(),
			globalRenderContext, localRenderContext);
		
		return new FlowRenderContext(
			layoutManagerContext,
			directives -> new BuildableRenderedUnitImp(null, directives),
			context -> new StyledUnit(null, context),
			flowRootContextSwitch);
	}

	public static TextBox createTextBox(String text, DirectivePool directives) {
		Font2D font = createTestFont();

		if (directives == null) directives = emptyDirectivePool;
		return new TextBox(new TextDisplay(), null, directives, text, font);
	}

	public static TextBox createTextBox(String text) {
		return createTextBox(text, null);
	}

	private static Font2D createTestFont() {
		Font2D font = Mockito.mock(Font2D.class);
		Mockito.when(font.getMetrics()).thenReturn(new TestFontMetrics());

		return font;
	}

}
