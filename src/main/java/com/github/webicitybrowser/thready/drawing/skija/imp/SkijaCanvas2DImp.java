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
		SkijaFont2D loadedFont = (SkijaFont2D) paint.getFont();
		Font font = loadedFont.getRaw();
		FontMetrics metrics = loadedFont.getMetrics();
		
		short[] glyphs = new short[text.length()];
		float[] xpos = new float[glyphs.length];
		int distance = 0;
		for (int i = 0; i < xpos.length; i++) {
			int codePoint = text.codePointAt(i);
			glyphs[i] = loadedFont.getCharacterGlyph(codePoint);
			xpos[i] = distance;
			distance += metrics.getCharacterWidth(codePoint);
		}
		
		TextBlob textBlob = TextBlob.makeFromPosH(glyphs, xpos, 0, font);
		float adjustedY = y + metrics.getHeight() - metrics.getLeading();
		canvas.drawTextBlob(textBlob, x, adjustedY, rawPaint);
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
