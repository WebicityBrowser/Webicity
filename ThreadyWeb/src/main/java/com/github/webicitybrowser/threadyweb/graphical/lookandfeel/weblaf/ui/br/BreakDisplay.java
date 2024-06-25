package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.br;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public class BreakDisplay implements UIDisplay<BreakContext, BreakBox, BreakUnit> {

	@Override
	public BreakContext createContext(ComponentUI componentUI) {
		return new BreakContext(this, componentUI);
	}

	@Override
	public List<BreakBox> generateBoxes(BreakContext displayContext, BoxContext boxContext) {
		return List.of(new BreakBox(
			this, displayContext.componentUI().getComponent(), displayContext.styleDirectives()
		));
	}

	@Override
	public BreakUnit renderBox(BreakBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'renderBox'");
	}

	@Override
	public void paint(BreakUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'paint'");
	}

	@Override
	public MessageHandler createMessageHandler(BreakUnit unit, Rectangle documentRect) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'createMessageHandler'");
	}

}
