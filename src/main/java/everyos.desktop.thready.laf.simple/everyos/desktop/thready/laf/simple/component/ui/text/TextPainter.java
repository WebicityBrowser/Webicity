package everyos.desktop.thready.laf.simple.component.ui.text;

import everyos.desktop.thready.basic.directive.ForegroundColorDirective;
import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.graphics.canvas.Paint2D;
import everyos.desktop.thready.core.graphics.canvas.imp.Paint2DBuilder;
import everyos.desktop.thready.core.graphics.color.Colors;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.graphics.text.LoadedFont;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.paint.PaintContext;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.positioning.Rectangle;

public class TextPainter implements Painter {

	private final Box box;
	private final Rectangle documentRect;
	private final String text;
	private final LoadedFont font;

	public TextPainter(Box box, Rectangle documentRect, String text, LoadedFont font) {
		this.box = box;
		this.documentRect = documentRect;
		this.text = text;
		this.font = font;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewportRect) {
		Paint2D paint = Paint2DBuilder.clone(canvas.getPaint())
			.setColor(getForegroundColor())
			.setLoadedFont(font)
			.build();
		
		canvas
			.withPaint(paint)
			.drawText(
				documentRect.getPosition().getX(),
				documentRect.getPosition().getY(),
				text);
	}

	private ColorFormat getForegroundColor() {
		return box
			.getDirectivePool()
			.inheritDirectiveOrEmpty(ForegroundColorDirective.class)
			.map(directive -> directive.getColor())
			.orElse(Colors.BLACK);
	}

}
