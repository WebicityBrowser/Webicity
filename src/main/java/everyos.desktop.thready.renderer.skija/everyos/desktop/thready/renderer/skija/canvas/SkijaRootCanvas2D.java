package everyos.desktop.thready.renderer.skija.canvas;

import org.lwjgl.opengl.GL32;

import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.graphics.canvas.Canvas2DSettings;
import everyos.desktop.thready.core.graphics.canvas.Paint2D;
import everyos.desktop.thready.core.graphics.color.Colors;
import everyos.desktop.thready.core.graphics.color.formats.ColorFormat;
import everyos.desktop.thready.core.graphics.color.imp.InternalColorImp;
import everyos.desktop.thready.core.graphics.image.LoadedImage;
import everyos.desktop.thready.core.graphics.text.FontMetrics;
import everyos.desktop.thready.core.graphics.text.LoadedFont;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.renderer.skija.SkijaLoadedFont;
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
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;

public class SkijaRootCanvas2D implements Canvas2D {
	
	private static final ColorSpace colorSpace = ColorSpace.getSRGB(); //.getDisplayP3();
	
	private final Canvas canvas;
	private final DirectContext directContext;
	
	private Paint2D paint;
	private Paint rawPaint;

	public SkijaRootCanvas2D(Canvas canvas, DirectContext directContext, Paint2D paint) {
		this.canvas = canvas;
		this.directContext = directContext;
		setPaint(paint);
	}

	@Override
	public void drawRect(float x, float y, float l, float h) {
		canvas.drawRect(new Rect(x, y, x + l, y + h), rawPaint);
	}

	@Override
	public void drawEllipse(float x, float y, float l, float h) {
		canvas.drawRRect(RRect.makeOvalXYWH(x, y, l, h), rawPaint);
	}

	@Override
	public void drawLine(float x, float y, float l, float h) {
		canvas.drawLine(x, y, x + l, y + h, rawPaint);
	}

	@Override
	public void drawTriangle(float p1x, float p1y, float p2ox, float p2oy, float p3ox, float p3oy) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void drawTexture(float x, float y, LoadedImage image) {
		drawTexture(x, y, image.getNaturalWidth(), image.getNaturalHeight(), image);
	}

	@Override
	public void drawTexture(float x, float y, float l, float h, LoadedImage image) {
		// TODO Auto-generated method stub
	}

	@Override
	public void drawText(float x, float y, String text) {
		SkijaLoadedFont loadedFont = (SkijaLoadedFont) paint.getLoadedFont();
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
	public void drawCharacters(float x, float y, char[] chars) {
		drawText(x, y, new String(chars));
	}

	@Override
	public Canvas2D createIntersectionClippedCanvas(float x, float y, float l, float h, Canvas2DSettings settings) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Canvas2D withPaint(Paint2D paint) {
		return new SkijaRootCanvas2D(canvas, directContext, paint);
	}

	@Override
	public void setPaint(Paint2D paint) {
		this.paint = paint;
		this.rawPaint = createPaint(paint);
	}
	
	@Override
	public Paint2D getPaint() {
		return this.paint;
	}
	
	public void flush() {
		directContext.flush();
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
		int framebufferId = GL32.glGetInteger(GL32.GL_FRAMEBUFFER_BINDING);
		BackendRenderTarget renderTarget = BackendRenderTarget.makeGL(
			(int) size.getWidth(), (int) size.getHeight(),
			0, 8, framebufferId,
			FramebufferFormat.GR_GL_RGBA8);

		Surface surface = Surface.makeFromBackendRenderTarget(
			directContext,
			renderTarget,
			SurfaceOrigin.BOTTOM_LEFT,
			SurfaceColorFormat.RGBA_8888,
			colorSpace);
				
		Canvas canvas = surface.getCanvas();
		
		return new SkijaRootCanvas2D(canvas, directContext, createDefaultPaint());
	}

	private static Paint2D createDefaultPaint() {
		return new Paint2D() {
			@Override
			public LoadedFont getLoadedFont() {
				return null;
			}
			
			@Override
			public ColorFormat getColor() {
				return Colors.WHITE;
			}
		};
	}

}
