package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text.stage.paint;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.core.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;

public class TextPainter implements Painter {

	private final Box box;
	private final Rectangle documentRect;
	private final String text;
	private final Font2D font;

	public TextPainter(Box box, Rectangle documentRect, String text, Font2D font) {
		this.box = box;
		this.documentRect = documentRect;
		this.text = text;
		this.font = font;
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas) {
		Paint2D paint = Paint2DBuilder.clone(canvas.getPaint())
			.setColor(getForegroundColor())
			.setFont(font)
			.build();
		
		canvas
			.withPaint(paint)
			.drawText(
				documentRect.position().x(),
				documentRect.position().y(),
				text);
	}

	private ColorFormat getForegroundColor() {
		DirectivePool styleDirectives = box.getStyleDirectives();
		return SimpleDirectiveUtil.getForegroundColor(styleDirectives);
	}

}
