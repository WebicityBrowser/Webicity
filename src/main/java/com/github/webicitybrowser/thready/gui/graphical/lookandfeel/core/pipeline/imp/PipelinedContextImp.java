package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.imp;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.PipelinedContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class PipelinedContextImp<T extends Context, U extends Box, V extends RenderedUnit> implements PipelinedContext<T, U, V> {

	private final T context;
	private final UIDisplay<T, U, V> display;

	public PipelinedContextImp(T context, UIDisplay<T, U, V> display) {
		this.context = context;
		this.display = display;
	}
	
	@Override
	public List<BoundBox<U, V>> generateBoxes(BoxContext boxContext, StyleGenerator styleGenerator) {
		return display
			.generateBoxes(context, boxContext, styleGenerator)
			.stream()
			.map(box -> BoundBox.create(box, display))
			.toList();
	}

	@Override
	public T getRaw() {
		return context;
	}
	
}
