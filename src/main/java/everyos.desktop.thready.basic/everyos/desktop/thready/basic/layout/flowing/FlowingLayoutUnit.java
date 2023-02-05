package everyos.desktop.thready.basic.layout.flowing;

import java.util.Optional;

import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.stage.composite.Compositor;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;
import everyos.desktop.thready.core.positioning.imp.RectangleImp;
import everyos.desktop.thready.core.positioning.util.AbsolutePositionMath;

public class FlowingLayoutUnit implements Unit {

	private final FlowingLayoutResult[] renderResults;
	
	public FlowingLayoutUnit(FlowingLayoutResult[] renderResults) {
		this.renderResults = renderResults;
	}
	
	@Override
	public Optional<Compositor> createCompositor(Rectangle documentRect) {
		return Optional.empty();
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new FlowingLayoutPainter(documentRect, renderResults);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new FlowingLayoutMessageHandler(documentRect, createChildMessageHandlers(documentRect));
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return AbsoluteSizeImp.ZERO_SIZE;
	}
	
	private MessageHandler[] createChildMessageHandlers(Rectangle documentRect) {
		MessageHandler[] handlers = new MessageHandler[renderResults.length];
		for (int i = 0; i < renderResults.length; i++) {
			FlowingLayoutResult result = renderResults[i];
			Rectangle childDocumentRect = getChildDocumentRect(documentRect, result);
			handlers[renderResults.length - i - 1] = result.unit().getMessageHandler(childDocumentRect);
		}
		
		return handlers;
	}

	private Rectangle getChildDocumentRect(Rectangle documentRect, FlowingLayoutResult result) {
		Rectangle renderedRect = result.renderedRect();
		AbsolutePosition newPosition = AbsolutePositionMath.sum(renderedRect.getPosition(), documentRect.getPosition());
		AbsoluteSize newSize = renderedRect.getSize();
		return new RectangleImp(newPosition, newSize);
	}

}
