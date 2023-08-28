package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.inline;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementContext;

public class ElementInlineDisplay implements UIDisplay<ElementContext, ChildrenBox, BuildableRenderedUnit> {

	@Override
	public ElementContext createContext(ComponentUI componentUI) {
		throw new UnsupportedOperationException("Unimplemented method 'createContext'");
	}

	@Override
	public List<ChildrenBox> generateBoxes(ElementContext displayContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		throw new UnsupportedOperationException("Unimplemented method 'generateBoxes'");
	}

	@Override
	public BuildableRenderedUnit renderBox(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		throw new UnsupportedOperationException("Unimplemented method 'renderBox'");
	}

	@Override
	public void paint(BuildableRenderedUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		ElementInlinePainter.paint(unit, globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(BuildableRenderedUnit unit, Rectangle documentRect) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'createMessageHandler'");
	}
	
}
