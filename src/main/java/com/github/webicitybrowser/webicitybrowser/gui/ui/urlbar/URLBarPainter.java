package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.builder.Paint2DBuilder;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.PaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;
import com.github.webicitybrowser.thready.gui.graphical.view.textfield.TextFieldPainter;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;
import com.github.webicitybrowser.webicitybrowser.gui.Styling;

public class URLBarPainter implements Painter {

	private final Box box;
	private final Rectangle documentRect;
	private final Font2D font;
	
	private final TextFieldPainter foregroundPainter;

	public URLBarPainter(
		Box box, Rectangle documentRect, Rectangle contentRect, ComponentUI componentUI, TextFieldViewModel textFieldViewModel, Font2D font
	) {
		this.box = box;
		this.documentRect = documentRect;
		this.font = font;
		
		this.foregroundPainter = new TextFieldPainter(contentRect, componentUI, textFieldViewModel, font);
	}

	@Override
	public void paint(PaintContext context, Canvas2D canvas, Rectangle viewportRect) {
		paintBackground(canvas);
		paintForeground(context, canvas, viewportRect);
	}

	private void paintBackground(Canvas2D canvas) {
		ColorFormat color = WebDirectiveUtil.getBackgroundColor(box.getStyleDirectives());
		Paint2D paint = Paint2DBuilder
			.clone(canvas.getPaint())
			.setColor(color)
			.build();
		
		float docX = documentRect.position().x();
		float docY = documentRect.position().y();
		float docW = documentRect.size().width();
		float docH = documentRect.size().height();
		
		Canvas2D ctx = canvas.withPaint(paint);
		
		ctx.drawRect(
			docX + Styling.BUTTON_WIDTH / 2, docY,
			docW - Styling.BUTTON_WIDTH, docH);
		
		ctx.drawEllipse(docX, docY, Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
		ctx.drawEllipse(docX + docW - Styling.BUTTON_WIDTH, docY, Styling.BUTTON_WIDTH, Styling.BUTTON_WIDTH);
	}
	
	private void paintForeground(PaintContext context, Canvas2D canvas, Rectangle viewportRect) {
		ColorFormat color = SimpleDirectiveUtil.getForegroundColor(box.getStyleDirectives());
		Paint2D paint = Paint2DBuilder
			.clone(canvas.getPaint())
			.setColor(color)
			.setFont(font)
			.build();
		Canvas2D ctx = canvas.withPaint(paint);
		
		foregroundPainter.paint(context, ctx, viewportRect);
	}
	
}
