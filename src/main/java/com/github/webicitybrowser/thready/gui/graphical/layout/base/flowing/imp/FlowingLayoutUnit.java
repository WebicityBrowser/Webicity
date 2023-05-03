package com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.imp;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsolutePositionMath;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public class FlowingLayoutUnit implements Unit {

	private final FlowingLayoutResult[] childrenResults;
	private final AbsoluteSize minimumSize;

	public FlowingLayoutUnit(FlowingLayoutResult[] childrenResults, AbsoluteSize minimumSize) {
		this.childrenResults = childrenResults;
		this.minimumSize = minimumSize;
	}
	
	@Override
	public AbsoluteSize getMinimumSize() {
		return this.minimumSize;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new FlowingLayoutPainter(documentRect, childrenResults);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new FlowingLayoutMessageHandler(documentRect, createChildMessageHandlers(documentRect));
	}

	private MessageHandler[] createChildMessageHandlers(Rectangle documentRect) {
		MessageHandler[] handlers = new MessageHandler[childrenResults.length];
		for (int i = 0; i < childrenResults.length; i++) {
			FlowingLayoutResult result = childrenResults[i];
			Rectangle childDocumentRect = getChildDocumentRect(documentRect, result);
			handlers[childrenResults.length - i - 1] = result.unit().getMessageHandler(childDocumentRect);
		}
		
		return handlers;
	}

	private Rectangle getChildDocumentRect(Rectangle documentRect, FlowingLayoutResult result) {
		Rectangle renderedRect = result.renderedRect();
		AbsolutePosition newPosition = AbsolutePositionMath.sum(renderedRect.position(), documentRect.position());
		AbsoluteSize newSize = renderedRect.size();
		return new Rectangle(newPosition, newSize);
	}
	
}
