package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.webicity.core.component.FrameComponent;

public class FrameDisplay implements UIDisplay<FrameContext, FrameBox, FrameUnit> {

	@Override
	public FrameContext createContext(ComponentUI componentUI) {
		return new FrameContext(this, componentUI);
	}

	@Override
	public List<FrameBox> generateBoxes(FrameContext displayContext, BoxContext boxContext) {
		if (displayContext.screenContent() == null) {
			return List.of();
		}
		
		FrameComponent frameComponent = (FrameComponent) displayContext.componentUI().getComponent();
		
		return List.of(new FrameBox(frameComponent, this, displayContext));
	}

	@Override
	public FrameUnit renderBox(FrameBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		return FrameRenderer.render(box, renderContext, localRenderContext);
	}

	@Override
	public void composite(FrameUnit unit, GlobalCompositeContext globalCompositeContext, LocalCompositeContext localCompositeContext) {
		FrameCompositor.composite(unit, globalCompositeContext, localCompositeContext);
	}

	@Override
	public void paint(FrameUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		FramePainter.paint(unit, globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(FrameUnit unit, Rectangle documentRect) {
		return new FrameMessageHandler(unit, documentRect);
	}

}
