package com.github.webicitybrowser.thready.drawing.skija.imp;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.color.imp.InternalColorImp;
import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
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
import io.github.humbleui.skija.TextBlob;
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

			currentX += drawPartialText(windowText, currentX, adjustedY, currentFont);
			windowStart = windowEnd + 1;
		}
	}

	@Override
	public void drawLine(float x, float y, float run, float fall) {
		beforePaint();
		if (run < 0 || fall < 0) {
			return;
		}
		canvas.drawLine(x, y, x + run, y + fall, rawPaint);
	}

	@Override
	public void drawTexture(float x, float y, float l, float h, Image texture) {
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
		
		SkijaCanvasSettings childSettings = createMovedChildSettings(childCanvasSettings, childRectangle);
		
		return new SkijaCanvas2DImp(canvas, paint, childSettings);
	}

	@Override
	public Canvas2D createTranslatedCanvas(float x, float y) {
		SkijaCanvasSettings translatedCanvasSettings = createTranslatedCanvasSettings(x, y);

		return new SkijaCanvas2DImp(canvas, paint, translatedCanvasSettings);
	}

	private float drawPartialText(String text, float x, float y, Font font) {
		short[] glyphs = font.getStringGlyphs(text);
		float[] glyphWidths = font.getWidths(glyphs);
		float[] glyphPositions = new float[glyphWidths.length];
		float distance = 0;
		for (int i = 0; i < glyphWidths.length; i++) {
			if (i > 0) {
				distance += paint.getLetterSpacing();
			}
			glyphPositions[i] = distance;
			distance += glyphWidths[i];
		}


		TextBlob textBlob = TextBlob.makeFromPosH(glyphs, glyphPositions, 0, font);
		canvas.drawTextBlob(textBlob, x, y, rawPaint);
		return distance;
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

	private void setPaint(Paint2D paint) {
		this.paint = paint;
		this.rawPaint = createPaint(paint);
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
		if (canvasSettings.clipBounds() != null) {
			applyClip();
		}
		canvas.translate(
			canvasSettings.offset().x() - canvasSettings.translation().x(),
			canvasSettings.offset().y() - canvasSettings.translation().y());
	}

	private void applyClip() {
		Rectangle clipBounds = canvasSettings.clipBounds();
		AbsolutePosition clipPosition = clipBounds.position();
		AbsoluteSize clipSize = clipBounds.size();
		canvas.clipRect(new Rect(
			clipPosition.x(),
			clipPosition.y(),
			clipPosition.x() + clipSize.width(),
			clipPosition.y() + clipSize.height()));
	}

	private SkijaCanvasSettings createMovedChildSettings(ChildCanvasSettings childCanvasSettings, Rectangle childRectangle) {
		AbsolutePosition childOffset = childCanvasSettings.preservePosition() ?
			canvasSettings.offset() :
			AbsoluteDimensionsMath.sum(canvasSettings.offset(), childRectangle.position(), AbsolutePosition::new);
		
		return new SkijaCanvasSettings(childOffset, childRectangle, canvasSettings.translation());
	}

	private SkijaCanvasSettings createTranslatedCanvasSettings(float x, float y) {
		AbsolutePosition newTranslation = AbsoluteDimensionsMath.sum(canvasSettings.translation(), new AbsolutePosition(x, y), AbsolutePosition::new);
		return new SkijaCanvasSettings(canvasSettings.offset(), canvasSettings.clipBounds(), newTranslation);
	}
	
}
