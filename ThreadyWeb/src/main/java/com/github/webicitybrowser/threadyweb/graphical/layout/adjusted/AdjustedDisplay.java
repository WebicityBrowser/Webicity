package com.github.webicitybrowser.threadyweb.graphical.layout.adjusted;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeParameters;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer.CompositeReference;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public class AdjustedDisplay implements UIDisplay<Context, Box, AdjustedUnit> {

	@Override
	public Context createContext(ComponentUI componentUI) {
		throw new UnsupportedOperationException("Unimplemented method 'createContext'");
	}

	@Override
	public List<Box> generateBoxes(Context displayContext, BoxContext boxContext) {
		throw new UnsupportedOperationException("Unimplemented method 'generateBoxes'");
	}

	@Override
	public AdjustedUnit renderBox(Box box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		throw new UnsupportedOperationException("Unimplemented method 'renderBox'");
	}

	@Override
	@SuppressWarnings("unchecked")
	public void composite(AdjustedUnit unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		RenderedUnit innerUnit = unit.renderedUnit();

		compositeContext.enterChildContext(
			unit.adjustedBounds(),
			new CompositeParameters(CompositeReference.VIEWPORT, () -> AbsolutePosition.ZERO_POSITION));
		LocalCompositeContext childCompositeContext = new LocalCompositeContext(new Rectangle(
			AbsolutePosition.ZERO_POSITION,
			unit.adjustedBounds().size()));
		((UIDisplay<?, ?, RenderedUnit>) innerUnit.display()).composite(innerUnit, compositeContext, childCompositeContext);
		compositeContext.exitChildContext();
	};

	@Override
	@SuppressWarnings("unchecked")
	public void paint(AdjustedUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		RenderedUnit innerUnit = unit.renderedUnit();
		((UIDisplay<?, ?, RenderedUnit>) innerUnit.display()).paint(innerUnit, globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(AdjustedUnit unit, Rectangle documentRect) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'createMessageHandler'");
	}
	
}
