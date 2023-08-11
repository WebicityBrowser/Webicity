package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsolutePositionMath;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.DefaultGraphicalMessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public final class ContainerMessageHandler {

	private ContainerMessageHandler() {}
	
	public static MessageHandler createMessageHandler(ContainerRenderedUnit unit, Rectangle documentRect) {
		ChildLayoutResult[] childLayoutResults = unit.childLayoutResults();
		MessageHandler[] childHandlers = new MessageHandler[childLayoutResults.length];
		for (int i = 0; i < childLayoutResults.length; i++) {
			ChildLayoutResult childLayoutResult = childLayoutResults[i];
			Rectangle childDocumentRect = computeResultDocumentRect(documentRect, childLayoutResult.relativeRect());
			childHandlers[i] = childLayoutResult.unit().createMessageHandler(childDocumentRect);
		}
		return new DefaultGraphicalMessageHandler(documentRect, unit.box(), childHandlers);
	}
	
	private static Rectangle computeResultDocumentRect(Rectangle documentRect, Rectangle renderedRect) {
		AbsolutePosition documentPosition = AbsolutePositionMath.sum(
			documentRect.position(),
			renderedRect.position());
		
		return new Rectangle(documentPosition, renderedRect.size());
	}
	
}
