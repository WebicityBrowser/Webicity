package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.paint;

import com.github.webicitybrowser.thready.color.Colors;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.imp.Paint2DBuilder;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;

public class BlockWrappingPainter implements Painter {

	private Rectangle documentRect;

	public BlockWrappingPainter(Rectangle documentRect) {
		this.documentRect = documentRect;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas) {
		Paint2D paint = new Paint2DBuilder()
			.setColor(Colors.WHITE)
			.build();
		canvas.setPaint(paint);
		canvas.drawRect(
			documentRect.position().x(),
			documentRect.position().y(),
			documentRect.size().width(),
			documentRect.size().height());
	}

}
