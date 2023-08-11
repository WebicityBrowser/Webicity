package com.github.webicitybrowser.thready.windowing.skija.imp;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.Paint2D;
import com.github.webicitybrowser.thready.drawing.skija.imp.SkijaCanvas2DImp;
import com.github.webicitybrowser.thready.windowing.skija.SkijaRootCanvas2D;

import io.github.humbleui.skija.BackendRenderTarget;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.FramebufferFormat;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.SurfaceColorFormat;
import io.github.humbleui.skija.SurfaceOrigin;

public class SkijaRootCanvas2DImp extends SkijaCanvas2DImp implements SkijaRootCanvas2D {
	
	private static final ColorSpace colorSpace = ColorSpace.getSRGB(); //.getDisplayP3();
	
	private final DirectContext directContext;

	public SkijaRootCanvas2DImp(Canvas canvas, Paint2D paint, DirectContext directContext) {
		super(canvas, paint, createCanvasSettings());
		this.directContext = directContext;
	}

	@Override
	public void flush() {
		directContext.flush();
	}
	
	private static SkijaCanvasSettings createCanvasSettings() {
		return new SkijaCanvasSettings(new AbsolutePosition(0, 0), null);
	}

	public static SkijaRootCanvas2D create(DirectContext directContext, AbsoluteSize size) {
		BackendRenderTarget renderTarget = createRenderTarget(size);
		Surface surface = createSurface(directContext, renderTarget);	
		Canvas canvas = surface.getCanvas();
		
		return new SkijaRootCanvas2DImp(canvas, new SkijaDefaultPaint2D(), directContext);
	}

	private static Surface createSurface(DirectContext directContext, BackendRenderTarget renderTarget) {
		return Surface.wrapBackendRenderTarget(
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
