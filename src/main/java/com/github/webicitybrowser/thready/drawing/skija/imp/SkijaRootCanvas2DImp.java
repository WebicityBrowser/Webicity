package com.github.webicitybrowser.thready.drawing.skija.imp;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.windowing.skija.SkijaRootCanvas2D;

import io.github.humbleui.skija.BackendRenderTarget;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.FramebufferFormat;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.SurfaceColorFormat;
import io.github.humbleui.skija.SurfaceOrigin;
import io.github.humbleui.types.Rect;

public class SkijaRootCanvas2DImp implements SkijaRootCanvas2D {
	
	private static final ColorSpace colorSpace = ColorSpace.getSRGB(); //.getDisplayP3();
	
	private final Canvas canvas;
	private final DirectContext directContext;
	
	private Paint rawPaint;

	public SkijaRootCanvas2DImp(Canvas canvas, DirectContext directContext) {
		this.canvas = canvas;
		this.directContext = directContext;
		this.rawPaint = createPaint();
	}

	@Override
	public void drawRect(float x, float y, float l, float h) {
		canvas.drawRect(new Rect(x, y, x + l, y + h), rawPaint);
	}
	
	@Override
	public void flush() {
		directContext.flush();
	}
	
	private Paint createPaint() {
		Paint rawPaint = new Paint();
		rawPaint.setColor(0xFFFF00FF);
		
		return rawPaint;
	}

	public static SkijaRootCanvas2D create(DirectContext directContext, AbsoluteSize size) {
		BackendRenderTarget renderTarget = createRenderTarget(size);
		Surface surface = createSurface(directContext, renderTarget);	
		Canvas canvas = surface.getCanvas();
		
		return new SkijaRootCanvas2DImp(canvas, directContext);
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
			(int) size.getWidth(), (int) size.getHeight(),
			0, 8, framebufferId,
			FramebufferFormat.GR_GL_RGBA8);
	}

}
