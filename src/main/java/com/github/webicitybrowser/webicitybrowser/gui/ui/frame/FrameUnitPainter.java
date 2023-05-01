package com.github.webicitybrowser.webicitybrowser.gui.ui.frame;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.ChildCanvasSettings;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent;
import com.github.webicitybrowser.thready.windowing.core.ScreenContent.ScreenContentRedrawContext;

public class FrameUnitPainter implements Painter {

	private final Rectangle documentRect;
	private final ResourceLoader resourceLoader;
	private final ScreenContent screenContent;

	public FrameUnitPainter(Rectangle documentRect, ResourceLoader resourceLoader, ScreenContent screenContent) {
		this.documentRect = documentRect;
		this.resourceLoader = resourceLoader;
		this.screenContent = screenContent;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewport) {
		Canvas2D childCanvas = createChildCanvas(canvas);
		ScreenContentRedrawContext redrawContext = new ScreenContentRedrawContext(
			childCanvas, documentRect.size(), resourceLoader);
		screenContent.redraw(redrawContext);
	}

	private Canvas2D createChildCanvas(Canvas2D canvas) {
		return canvas.createClippedCanvas(
			documentRect.position().x(), documentRect.position().y(),
			documentRect.size().width(), documentRect.size().height(),
			new ChildCanvasSettings(false));
	}

}
