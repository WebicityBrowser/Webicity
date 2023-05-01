package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;

public class FrameUnit implements Unit {

	private final ResourceLoader resourceLoader;
	private final ScreenContent screenContent;
	private final AbsoluteSize preferredSize;

	public FrameUnit(ResourceLoader resourceLoader, ScreenContent screenContent, AbsoluteSize preferredSize) {
		this.resourceLoader = resourceLoader;
		this.screenContent = screenContent;
		this.preferredSize = preferredSize;
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return this.preferredSize;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new FrameUnitPainter(documentRect, resourceLoader, screenContent);
	}

}
