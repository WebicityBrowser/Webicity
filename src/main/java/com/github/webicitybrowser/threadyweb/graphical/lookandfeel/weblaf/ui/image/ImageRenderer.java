package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;

public final class ImageRenderer {

	private ImageRenderer() {}

	public static ImageUnit render(ImageBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		return new ImageUnit(box.display(), new AbsoluteSize(0, 0), box.styleDirectives());	
	}

}
