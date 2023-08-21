package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.render.unit.SingleRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.DefaultGraphicalMessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.webicity.core.component.FrameComponent;

public class FrameDisplay implements UIDisplay<FrameContext, FrameBox, FrameUnit> {

	@Override
	public FrameContext createContext(ComponentUI componentUI) {
		return new FrameContext(this, componentUI);
	}

	@Override
	public List<FrameBox> generateBoxes(FrameContext displayContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		if (displayContext.screenContent() == null) {
			return List.of();
		}
		
		FrameComponent frameComponent = (FrameComponent) displayContext.componentUI().getComponent();
		DirectivePool styleDirectives = styleGenerator.getStyleDirectives();
		
		return List.of(new FrameBox(frameComponent, styleDirectives, this, displayContext));
	}

	@Override
	public RenderedUnitGenerator<FrameUnit> renderBox(FrameBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		return new SingleRenderedUnitGenerator<>(FrameRenderer.render(box, renderContext, localRenderContext));
	}

	@Override
	public void paint(FrameUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		FramePainter.paint(unit, globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(FrameUnit unit, Rectangle documentRect) {
		return new DefaultGraphicalMessageHandler(documentRect, unit.box());
	}

}
