package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.paint;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;

public class BlockWrappingPainter implements Painter {
	
	private final Painter innerPainter;
	private final BackgroundPainter backgroundPainter;

	public BlockWrappingPainter(Box box, Rectangle documentRect, Painter innerPainter) {
		this.innerPainter = innerPainter;
		this.backgroundPainter = new BackgroundPainter(box, documentRect);
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewport) {
		backgroundPainter.paint(context, canvas, viewport);
		paintInnerContent(context, canvas, viewport);
	}

	
	
	private void paintInnerContent(PaintContext context, Canvas2D canvas, Rectangle viewport) {
		innerPainter.paint(context, canvas, viewport);
	}

}
