package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public interface RenderCache {
	
	<U extends Box, V extends RenderedUnit> V cachedRender(U box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext);

	<U extends Box> AbsoluteSize minContentSize(U box, GlobalRenderContext globalRenderContext, boolean isHorizontal);

	<U extends Box> AbsoluteSize maxContentSize(U box, GlobalRenderContext globalRenderContext, boolean isHorizontal);

	void swap();

}
