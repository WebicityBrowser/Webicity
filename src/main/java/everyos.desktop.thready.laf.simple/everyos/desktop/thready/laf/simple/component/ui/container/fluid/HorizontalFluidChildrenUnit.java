package everyos.desktop.thready.laf.simple.component.ui.container.fluid;

import java.util.Optional;

import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.stage.composite.Compositor;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.imp.RectangleImp;
import everyos.desktop.thready.core.positioning.util.AbsolutePositionMath;

public class HorizontalFluidChildrenUnit implements Unit {

	private final AbsoluteSize size;
	private final FluidChildrenResult[] renderResults;

	public HorizontalFluidChildrenUnit(AbsoluteSize size, FluidChildrenResult[] renderResults) {
		this.size = size;
		this.renderResults = renderResults;
	}

	@Override
	public Optional<Compositor> createCompositor(Rectangle documentRect) {
		return Optional.empty();
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new HorizontalFluidChildrenPainter(documentRect, renderResults);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new HorizontalChildrenMessageHandler(documentRect, createChildMessageHandlers(documentRect));
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return this.size;
	}
	
	private MessageHandler[] createChildMessageHandlers(Rectangle documentRect) {
		MessageHandler[] handlers = new MessageHandler[renderResults.length];
		for (int i = 0; i < renderResults.length; i++) {
			FluidChildrenResult result = renderResults[i];
			Rectangle childDocumentRect = getChildDocumentRect(documentRect, result);
			handlers[renderResults.length - i - 1] = result.unit().getMessageHandler(childDocumentRect);
		}
		
		return handlers;
	}

	private Rectangle getChildDocumentRect(Rectangle documentRect, FluidChildrenResult result) {
		AbsolutePosition newPosition = AbsolutePositionMath.sum(result.position(), documentRect.getPosition());
		AbsoluteSize newSize = result.unit().getMinimumSize();
		return new RectangleImp(newPosition, newSize);
	}

}
