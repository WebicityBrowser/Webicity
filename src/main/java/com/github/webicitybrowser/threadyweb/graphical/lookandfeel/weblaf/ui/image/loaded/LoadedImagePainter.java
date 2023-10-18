package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.loaded;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;

public class LoadedImagePainter {
	
	private LoadedImagePainter() {}

	public static void paint(LoadedImageUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		Canvas2D canvas = localPaintContext.canvas();
		Rectangle documentRect = localPaintContext.documentRect();

		canvas.drawTexture(
			documentRect.position().x(),
			documentRect.position().y(),
			documentRect.size().width(),
			documentRect.size().height(),
			unit.imageFrames()[0]);
	}

}
