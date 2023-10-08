package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public interface GlobalCompositeContext {

	void enterChildContext(Rectangle bounds, CompositeParameters parameters);

	void exitChildContext();

	void addPaintUnit(RenderedUnit paintableUnit, LocalCompositeContext localCompositeContext);
	
}
