package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;

public record FrameUnit(AbsoluteSize preferredSize, FrameBox box, ResourceLoader resourceLoader, ScreenContent screenContent) implements RenderedUnit {

	@Override
	public UIDisplay<?, ?, ?> display() {
		return box.display();
	}

}
