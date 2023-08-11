package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.imp.UIPipelineImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public interface UIPipeline<T extends Context, U extends Box, V extends RenderedUnit> {

	PipelinedContext<T, U, V> createContext(ComponentUI componentUI);
	
	UIDisplay<T, U, V> getDisplay();

	static <T extends Context, U extends Box, V extends RenderedUnit> UIPipeline<T, U, V> create(UIDisplay<T, U, V> rootDisplay) {
		return new UIPipelineImp<>(rootDisplay);
	}
	
}
