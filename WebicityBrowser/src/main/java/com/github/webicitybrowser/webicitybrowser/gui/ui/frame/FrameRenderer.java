package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;

public final class FrameRenderer {

	private FrameRenderer() {}
	
	public static FrameUnit render(FrameBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		return new FrameUnit(localRenderContext.preferredSize(), box, renderContext.resourceLoader(), box.displayContext().screenContent());
	}

}
