package com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.composite;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeParameters;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer.CompositeReference;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent.ScreenContentRedrawContext;

public final class ContentCompositor {
	
	private ContentCompositor() {}

	@SuppressWarnings("unchecked")
	public static <V extends RenderedUnit> List<CompositeLayer> performCompositeCycle(ScreenContentRedrawContext redrawContext, V rootUnit) {
		AbsoluteSize contentSize = redrawContext.contentSize();
		Rectangle rootBounds = new Rectangle(new AbsolutePosition(0, 0), contentSize);
		GlobalCompositeContextImp compositeContext = new GlobalCompositeContextImp();
		LocalCompositeContext localCompositeContext = new LocalCompositeContext(rootBounds);
		UIDisplay<?, ?, V> rootDisplay = (UIDisplay<?, ?, V>) rootUnit.display();
		CompositeParameters parameters = new CompositeParameters(CompositeReference.VIEWPORT, () -> new AbsolutePosition(0, 0));
		compositeContext.enterChildContext(rootBounds, parameters);
		rootDisplay.composite((V) rootUnit, compositeContext, localCompositeContext);
		compositeContext.exitChildContext();
		return compositeContext.getLayers();
	}

}
