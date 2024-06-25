package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

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

public class TabDisplay implements UIDisplay<TabContext, TabBox, TabUnit> {

	@Override
	public TabContext createContext(ComponentUI componentUI) {
		return new TabContext(this, componentUI);
	}

	@Override
	public List<TabBox> generateBoxes(TabContext displayContext, BoxContext boxContext) {
		return List.of(new TabBox(displayContext));
	}

	@Override
	public TabUnit renderBox(TabBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		return TabRenderer.render(box, renderContext, localRenderContext);
	}

	@Override
	public void paint(TabUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		TabPainter.paint(unit, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(TabUnit unit, Rectangle documentRect) {
		return new TabMessageHandler(documentRect, unit);
	}

}
