package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.layout.flow.inline;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.NoopMessageHandler;

public class FlowInlineUnit implements Unit {

	private final AbsoluteSize minimumSize;
	private RenderedInlineUnit[] children;

	public FlowInlineUnit(RenderedInlineUnit[] children, AbsoluteSize minimumSize) {
		this.minimumSize = minimumSize;
		this.children = children;
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return this.minimumSize;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new FlowInlineUnitPainter(children, documentRect);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new NoopMessageHandler();
	}

}
