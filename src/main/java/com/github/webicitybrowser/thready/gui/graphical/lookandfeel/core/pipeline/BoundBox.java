package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.imp.BoundBoxImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public interface BoundBox<U extends Box, V extends RenderedUnit> {

	Box getRaw();
	
	BoundRenderedUnitGenerator<V> render(GlobalRenderContext globalRenderContext, LocalRenderContext childRenderContext);
	
	List<BoundBox<?, ?>> getAdjustedBoxTree();

	BoundBox<U, V> offshoot(U box);

	static <U extends Box, V extends RenderedUnit> BoundBox<U, V> create(U box, UIDisplay<?, U, V> display) {
		return new BoundBoxImp<>(box, display);
	}

}
