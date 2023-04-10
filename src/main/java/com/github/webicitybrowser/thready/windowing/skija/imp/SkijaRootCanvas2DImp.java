package com.github.webicitybrowser.thready.windowing.skija.imp;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.github.webicitybrowser.thready.color.format.ColorFormat;
import com.github.webicitybrowser.thready.color.imp.InternalColorImp;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.Canvas2D;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.drawing.skija.SkijaFont2D;
import com.github.webicitybrowser.thready.windowing.skija.SkijaRootCanvas2D;

import io.github.humbleui.skija.BackendRenderTarget;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FramebufferFormat;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.SurfaceColorFormat;
import io.github.humbleui.skija.SurfaceOrigin;
import io.github.humbleui.skija.TextBlob;
import io.github.humbleui.types.Rect;

public class SkijaRootCanvas2DImp implements SkijaRootCanvas2D {
	
	private static final ColorSpace colorSpace = ColorSpace.getSRGB(); //.getDisplayP3();
	
	private final Canvas canvas;
	private final DirectContext directContext;
	
	private Paint2D paint;
	private Paint rawPaint;

	public SkijaRootCanvas2DImp(Canvas canvas, Paint2D paint, DirectContext directContext) {
		this.canvas = canvas;
		this.directContext = directContext;
		setPaint(paint);
	}

	@Override
	public void drawRect(float x, float y, float l, float h) {
		canvas.drawRect(new Rect(x, y, x + l, y + h), rawPaint);
	}
	
	@Override
	public void drawText(float x, float y, String text) {
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
	public Paint2D getPaint() {
		return this.paint;
	}

	@Override
	public Canvas2D withPaint(Paint2D paint) {
		return new SkijaRootCanvas2DImp(canvas, paint, directContext);
	}
	
	@Override
	public void flush() {
		directContext.flush();
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

	public static SkijaRootCanvas2D create(DirectContext directContext, AbsoluteSize size) {
		BackendRenderTarget renderTarget = createRenderTarget(size);
		Surface surface = createSurface(directContext, renderTarget);	
		Canvas canvas = surface.getCanvas();
		
		return new SkijaRootCanvas2DImp(canvas, new SkijaDefaultPaint2D(), directContext);
	}

	private static Surface createSurface(DirectContext directContext, BackendRenderTarget renderTarget) {
		return Surface.makeFromBackendRenderTarget(
			directContext,
			renderTarget,
			SurfaceOrigin.BOTTOM_LEFT,
			SurfaceColorFormat.RGBA_8888,
			colorSpace);
	}

	private static BackendRenderTarget createRenderTarget(AbsoluteSize size) {
		int framebufferId = GL11.glGetInteger(GL30.GL_FRAMEBUFFER_BINDING);
		return BackendRenderTarget.makeGL(
			(int) size.width(), (int) size.height(),
			0, 8, framebufferId,
			FramebufferFormat.GR_GL_RGBA8);
	}

}
