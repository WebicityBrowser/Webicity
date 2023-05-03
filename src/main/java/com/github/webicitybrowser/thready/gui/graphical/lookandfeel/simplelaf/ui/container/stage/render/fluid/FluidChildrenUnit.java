package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.stage.render.fluid;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.DefaultGraphicalMessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public class FluidChildrenUnit implements Unit {

	private final Box box;
	private final AbsoluteSize size;
	private final FluidChildrenResult[] renderResults;

	public FluidChildrenUnit(Box box, AbsoluteSize size, FluidChildrenResult[] renderResults) {
		this.box = box;
		this.size = size;
		this.renderResults = renderResults;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new FluidChildrenPainter(documentRect, renderResults);
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return this.size;
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new DefaultGraphicalMessageHandler(documentRect, box);
	}

}