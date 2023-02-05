package everyos.desktop.thready.laf.simple.component.paint;

import everyos.desktop.thready.basic.directive.BackgroundColorDirective;
import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.graphics.canvas.Paint2D;
import everyos.desktop.thready.core.graphics.canvas.imp.Paint2DBuilder;
import everyos.desktop.thready.core.graphics.color.Colors;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.paint.PaintContext;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.Rectangle;

public class BlockWrappingPainter implements Painter {
	
	private final Box box;
	private final Rectangle documentRect;
	private final Unit innerUnit;
	private final Rectangle innerUnitDocumentRect;
	

	public BlockWrappingPainter(Box box, Rectangle documentRect, Unit innerUnit, Rectangle innerUnitDocumentRect) {
		this.box = box;
		this.documentRect = documentRect;
		this.innerUnit = innerUnit;
		this.innerUnitDocumentRect = innerUnitDocumentRect;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewportRect) {
		paintBackground(canvas);
		innerUnit.getPainter(innerUnitDocumentRect).paint(context, canvas, viewportRect);
	}

	private void paintBackground(Canvas2D canvas) {
		ColorFormat backgroundColor = getBackgroundColor();
		
		Paint2D paint = Paint2DBuilder.clone(canvas.getPaint())
			.setColor(backgroundColor)
			.build();
		
		canvas
			.withPaint(paint)
			.drawRect(
				documentRect.getPosition().getX(),
				documentRect.getPosition().getY(),
				documentRect.getSize().getWidth(),
				documentRect.getSize().getHeight());
	}

	private ColorFormat getBackgroundColor() {
		return box
			.getDirectivePool()
			.getDirectiveOrEmpty(BackgroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.TRANSPARENT);
	}

}
