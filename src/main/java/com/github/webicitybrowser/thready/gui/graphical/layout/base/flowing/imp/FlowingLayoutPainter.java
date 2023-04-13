package com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.imp;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;

public class FlowingLayoutPainter implements Painter {
	
	private final Rectangle documentRect;
	private final FlowingLayoutResult[] childrenResults;

	public FlowingLayoutPainter(Rectangle documentRect, FlowingLayoutResult[] childrenResults) {
		this.documentRect = documentRect;
		this.childrenResults = childrenResults;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas) {
		paintChildren(context, canvas);
	}

	private void paintChildren(PaintContext context, Canvas2D canvas) {
		for (FlowingLayoutResult childResult: childrenResults) {
			paintChild(context, canvas, childResult);
		}
	}

	private void paintChild(PaintContext context, Canvas2D canvas, FlowingLayoutResult resultToPaint) {
		Painter resultPainter = resultToPaint.unit().getPainter(documentRect);
		// TODO: Pass properly clipped canvas
		resultPainter.paint(context, canvas);
	}

}