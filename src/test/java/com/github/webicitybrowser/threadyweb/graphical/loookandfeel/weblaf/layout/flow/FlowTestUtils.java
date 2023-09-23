package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.layout.flow;

import org.mockito.Mockito;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.basics.pool.BasicDirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.base.imp.RenderCacheImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderCache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.imp.FloatContextImp;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.imp.FloatTrackerImp;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.imp.BuildableRenderedUnitImp;
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

		return renderContext;
	}

	public static LocalRenderContext createLocalRenderContext() {
		return createLocalRenderContext(new AbsoluteSize(50, 50));
	}

	public static LocalRenderContext createLocalRenderContext(AbsoluteSize size) {
		return LocalRenderContext.create(size, testFont.getMetrics(), new ContextSwitch[0]);
	}

	public static LocalRenderContext createLocalRenderContext(AbsoluteSize size, ContextSwitch[] switches) {
		return LocalRenderContext.create(size, testFont.getMetrics(), switches);
	}

	public static FlowRenderContext createRenderContext(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		FlowRootContextSwitch flowRootContextSwitch = new FlowRootContextSwitch(
			new AbsolutePosition(0, 0),
			new FloatContextImp(new FloatTrackerImp()));
		for (ContextSwitch contextSwitch : localRenderContext.getContextSwitches()) {
			if (contextSwitch instanceof FlowRootContextSwitch) {
				flowRootContextSwitch = (FlowRootContextSwitch) contextSwitch;
			}
		}
		
		return new FlowRenderContext(
			box, globalRenderContext, localRenderContext,
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
