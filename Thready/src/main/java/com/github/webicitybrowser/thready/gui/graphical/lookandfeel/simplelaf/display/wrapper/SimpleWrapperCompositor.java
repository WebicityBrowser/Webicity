package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.display.wrapper;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public final class SimpleWrapperCompositor {

	public static <V extends RenderedUnit> void composite(
		SimpleWrapperUnit<V> unit, UIDisplay<?, ?, V> childDisplay, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext
	) {
		compositeContext.addPaintUnit(unit, localCompositeContext);
		childDisplay.composite(unit.childUnit(), compositeContext, localCompositeContext);
	}

}
