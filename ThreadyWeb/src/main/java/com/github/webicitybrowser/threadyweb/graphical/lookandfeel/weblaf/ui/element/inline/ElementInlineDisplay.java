package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.inline;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementCompositor;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementUnit;

public class ElementInlineDisplay implements UIDisplay<ElementContext, ChildrenBox, RenderedUnit> {

	@Override
	public ElementContext createContext(ComponentUI componentUI) {
		throw new UnsupportedOperationException("Unimplemented method 'createContext'");
	}

	@Override
	public List<ChildrenBox> generateBoxes(ElementContext displayContext, BoxContext boxContext) {
		throw new UnsupportedOperationException("Unimplemented method 'generateBoxes'");
	}

	@Override
	public ElementUnit renderBox(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		throw new UnsupportedOperationException("Unimplemented method 'renderBox'");
	}

	@Override
	public void composite(RenderedUnit unit, GlobalCompositeContext compositeContext, LocalCompositeContext localCompositeContext) {
		ElementCompositor.composite(asElementUnit((BuildableRenderedUnit) unit), compositeContext, localCompositeContext);	
	}

	@Override
	public void paint(RenderedUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		ElementInlinePainter.paint((ElementUnit) unit, globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(RenderedUnit unit, Rectangle documentRect) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'createMessageHandler'");
	}

	private static ElementUnit asElementUnit(BuildableRenderedUnit unit) {
		return new ElementUnit(
			unit.display(),
			unit.styleDirectives(),
			LayoutResult.create(unit.childLayoutResults(), unit.fitSize()));
	}

}
