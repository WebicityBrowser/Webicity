package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.imp;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.PipelinedContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class UIPipelineImp<T extends Context, U extends Box, V extends RenderedUnit> implements UIPipeline<T, U, V> {

	private final UIDisplay<T, U, V> display;

	public UIPipelineImp(UIDisplay<T, U, V> display) {
		this.display = display;
	}
	
	@Override
	public PipelinedContext<T, U, V> createContext(ComponentUI componentUI) {
		return PipelinedContext.create(display.createContext(componentUI), display);
	}

	@Override
	public UIDisplay<T, U, V> getDisplay() {
		return this.display;
	}

}
