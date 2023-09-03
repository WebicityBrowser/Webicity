package com.github.webicitybrowser.thready.drawing.skija.imp;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.color.imp.InternalColorImp;
import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsolutePositionMath;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.ChildCanvasSettings;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.drawing.skija.SkijaFont2D;
import com.github.webicitybrowser.thready.windowing.skija.imp.SkijaCanvasSettings;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;

public class SkijaCanvas2DImp implements Canvas2D {

	private final Canvas canvas;
	private final SkijaCanvasSettings canvasSettings;
	
	private Paint2D paint;
	private Paint rawPaint;

	public SkijaCanvas2DImp(Canvas canvas, Paint2D paint, SkijaCanvasSettings canvasSettings) {
		this.canvas = canvas;
		this.canvasSettings = canvasSettings;
		setPaint(paint);
	}

	@Override
	public void drawRect(float x, float y, float l, float h) {
		beforePaint();
		canvas.drawRect(new Rect(x, y, x + l, y + h), rawPaint);
	}
	
	@Override
	public void drawEllipse(float x, float y, float l, float h) {
		beforePaint();
		canvas.drawRRect(RRect.makeOvalXYWH(x, y, l, h), rawPaint);
	}
	
	@Override
	public void drawText(float x, float y, String text) {
		beforePaint();

		SkijaFont2D skijaFont = (SkijaFont2D) paint.getFont();
		FontMetrics metrics = skijaFont.getMetrics();
		
		int windowStart = 0;
		float currentX = x;
		float adjustedY = y + metrics.getCapHeight();
		while (windowStart < text.length()) {
			Font currentFont = skijaFont.getEffectiveFont(text.codePointAt(windowStart));
			int windowEnd = endOfConsecutiveFontChars(text, windowStart);
			String windowText = text.substring(windowStart, windowEnd + 1);
			canvas.drawString(windowText, currentX, adjustedY, currentFont, rawPaint);

			currentX += metrics.getStringWidth(windowText);
			windowStart = windowEnd + 1;
		}
	}
	
	private int endOfConsecutiveFontChars(String text, int windowStart) {
		SkijaFont2D skijaFont = (SkijaFont2D) paint.getFont();
		Font initialFont = skijaFont.getEffectiveFont(text.codePointAt(windowStart));
		int windowEnd = windowStart + 1;
		while (windowEnd < text.length()) {
			Font font = skijaFont.getEffectiveFont(text.codePointAt(windowEnd));
			if (font != initialFont) {
				return windowEnd - 1;
			}
			windowEnd++;
		}

		return windowEnd - 1;
	}

	@Override
	public void drawLine(float x, float y, int run, float fall) {
		beforePaint();
		if (run < 0 || fall < 0) {
			return;
		}
		canvas.drawLine(x, y, x + run, y + fall, rawPaint);
	}

	@Override
	public void drawTexture(float x, float y, int l, int h, Image texture) {
		beforePaint();
		io.github.humbleui.skija.Image rawImage = ((SkijaImage) texture).getRawImage();
		Rect rect = Rect.makeXYWH(x, y, l, h);
		canvas.drawImageRect(rawImage, rect, rawPaint);
	}
	
	@Override
	public Paint2D getPaint() {
		return this.paint;
	}

	@Override
	public Canvas2D withPaint(Paint2D paint) {
		return new SkijaCanvas2DImp(canvas, paint, canvasSettings);
	}
	
	@Override
	public Canvas2D createClippedCanvas(float x, float y, float width, float height, ChildCanvasSettings childCanvasSettings) {
		Rectangle childRectangle = new Rectangle(
			new AbsolutePosition(x, y),
			new AbsoluteSize(width, height));
		
		SkijaCanvasSettings childSettings = createChildSettings(childCanvasSettings, childRectangle);
		
		return new SkijaCanvas2DImp(canvas, paint, childSettings);
	}

	private void setPaint(Paint2D paint) {
		this.paint = paint;
		this.rawPaint = createPaint(paint);// TODO Auto-generated method stub
	}
	
	private Paint createPaint(Paint2D paint) {
		Paint rawPaint = new Paint();
		rawPaint.setColor(convertColorToInt(paint.getColor()));
		
		return rawPaint;
	}
	
	private int convertColorToInt(ColorFormat color) {
		InternalColorImp colorInternals = ((InternalColorImp) color.toRawColor());
		
		return
			(colorInternals.getAlpha8() << 24) +
			(colorInternals.getRed8() << 16) +
			(colorInternals.getGreen8() << 8) +
			colorInternals.getBlue8();
	}
	
	private void beforePaint() {
		canvas.restore();
		canvas.save();
		canvas.translate(
			canvasSettings.offset().x(),
			canvasSettings.offset().y());
	}

	private SkijaCanvasSettings createChildSettings(ChildCanvasSettings childCanvasSettings, Rectangle childRectangle) {
		
		AbsolutePosition childOffset = childCanvasSettings.preservePosition() ?
			canvasSettings.offset() :
			AbsolutePositionMath.sum(canvasSettings.offset(), childRectangle.position());
		
		return new SkijaCanvasSettings(childOffset, childRectangle);
	}
	
}
