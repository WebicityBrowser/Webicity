package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.styled;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.context.Context;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public class StyledUnitDisplay implements UIDisplay<Context, Box, StyledUnit> {

	@Override
	public Context createContext(ComponentUI componentUI) {
		throw new UnsupportedOperationException("Unimplemented method 'createContext'");
	}

	@Override
	public List<Box> generateBoxes(Context displayContext, BoxContext boxContext) {
		throw new UnsupportedOperationException("Unimplemented method 'generateBoxes'");
	}

	@Override
	public StyledUnit renderBox(Box box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		throw new UnsupportedOperationException("Unimplemented method 'renderBox'");
	}

	@Override
	public void composite(StyledUnit unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		StyledUnitCompositor.composite(unit, compositeContext, localCompositeContext);
	}

	@Override
	public void paint(StyledUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		StyledUnitPainter.paint(unit, globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(StyledUnit unit, Rectangle documentRect) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'createMessageHandler'");
	}
	
}
