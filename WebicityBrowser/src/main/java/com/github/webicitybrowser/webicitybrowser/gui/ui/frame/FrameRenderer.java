package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.GenericBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.webicity.core.component.FrameComponent;

public final class FrameRenderer {

	private FrameRenderer() {}
	
	public static FrameUnit render(GenericBox<FrameComponent, FrameContext> box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		return new FrameUnit(localRenderContext.preferredSize(), box, renderContext.resourceLoader(), box.displayContext().screenContent());
	}

}
