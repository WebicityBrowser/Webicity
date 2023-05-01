package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;

public class FrameComponentRenderer implements Renderer {

	private final ScreenContent screenContent;

	public FrameComponentRenderer(ScreenContent screenContent) {
		this.screenContent = screenContent;
	}

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		return new FrameUnit(renderContext.getResourceLoader(), screenContent, precomputedSize);
	}

}
