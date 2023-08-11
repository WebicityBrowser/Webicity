package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

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
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.NoopMessageHandler;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayLayout;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowInnerDisplayLayout;

public class ElementDisplay implements UIDisplay<ElementContext, ChildrenBox, InnerDisplayUnit> {
	
	private static final InnerDisplayLayout FLOW_INNER_DISPLAY_LAYOUT = new FlowInnerDisplayLayout();

	@Override
	public ElementContext createContext(ComponentUI componentUI) {
		return new ElementContext(componentUI);
	}

	@Override
	public List<ChildrenBox> generateBoxes(ElementContext displayContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		return ElementBoxGenerator.generateBoxes(displayContext, boxContext, styleGenerator);
	}

	@Override
	public RenderedUnitGenerator<InnerDisplayUnit> renderBox(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		if (box instanceof ElementBox elementBox) {
			return elementBox.layout().renderBox(box, globalRenderContext, localRenderContext);
		} else {
			return FLOW_INNER_DISPLAY_LAYOUT.renderBox(box, globalRenderContext, localRenderContext);
		}
	}

	@Override
	public void paint(InnerDisplayUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		ElementPainter.paint(unit, globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(InnerDisplayUnit unit, Rectangle documentRect) {
		return new NoopMessageHandler();
	}

}
