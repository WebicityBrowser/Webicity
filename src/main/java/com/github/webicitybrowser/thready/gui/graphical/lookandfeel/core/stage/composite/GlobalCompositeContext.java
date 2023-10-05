package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer.CompositeReference;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public interface GlobalCompositeContext {

	void enterChildContext(Rectangle bounds, CompositeReference reference);

	void exitChildContext();

	void addPaintUnit(RenderedUnit paintableUnit, LocalCompositeContext localCompositeContext);
	
}
