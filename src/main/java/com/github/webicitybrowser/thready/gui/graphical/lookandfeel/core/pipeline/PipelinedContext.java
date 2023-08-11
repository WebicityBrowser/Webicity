package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline;

import java.util.List;

import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.imp.PipelinedContextImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public interface PipelinedContext<T extends Context, U extends Box, V extends RenderedUnit> {
	
	List<BoundBox<U, V>> generateBoxes(BoxContext boxContext, StyleGenerator styleGenerator);

	T getRaw();

	static <T extends Context, U extends Box, V extends RenderedUnit> PipelinedContext<T, U, V> create(T context, UIDisplay<T, U, V> display) {
		return new PipelinedContextImp<>(context, display);
	}

}
