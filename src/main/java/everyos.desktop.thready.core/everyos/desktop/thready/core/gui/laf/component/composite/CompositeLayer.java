package everyos.desktop.thready.core.gui.laf.component.composite;

import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.gui.laf.component.paint.PaintContext;
import everyos.desktop.thready.core.positioning.Rectangle;

public interface CompositeLayer {
	
	CompositeReference getReference();
	
	// How does layer know viewport size?
	Rectangle getPosition();
	
	CompositeLayer[] getChildren();
	
	void draw(PaintContext context, Canvas2D canvas, Rectangle viewportRect);
	
	// Probably pass events directly to the composite layer
	// But this might prevent
	
	// Maybe a canvas restriction system? So we can stay in bounds of what would be
	// parent canvas had we not composited
	
}
