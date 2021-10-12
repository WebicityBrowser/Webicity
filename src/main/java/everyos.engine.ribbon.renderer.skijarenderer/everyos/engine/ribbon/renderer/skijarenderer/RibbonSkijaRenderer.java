package everyos.engine.ribbon.renderer.skijarenderer;

import java.util.HashMap;

import org.jetbrains.skija.BackendRenderTarget;
import org.jetbrains.skija.Canvas;
import org.jetbrains.skija.ColorSpace;
import org.jetbrains.skija.DirectContext;
import org.jetbrains.skija.FramebufferFormat;
import org.jetbrains.skija.Paint;
import org.jetbrains.skija.RRect;
import org.jetbrains.skija.Rect;
import org.jetbrains.skija.Surface;
import org.jetbrains.skija.SurfaceColorFormat;
import org.jetbrains.skija.SurfaceOrigin;
import org.jetbrains.skija.TextBlob;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL32;

import everyos.engine.ribbon.core.event.EventListener;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.rendering.RibbonFont;
import everyos.engine.ribbon.core.shape.Rectangle;

public class RibbonSkijaRenderer implements Renderer {
	private static final ColorSpace colorSpace = ColorSpace.getSRGB();
	private static final boolean debugBoxes = false;
	
	private final DirectContext context;
	private final Canvas canvas;
	private final HashMap<FontInfo, RibbonSkijaFont> fonts;
	
	private ListenerPaintListener lpl;
	
	public RibbonSkijaRenderer(Canvas canvas, DirectContext context) {
		this.context = context;
		this.canvas = canvas;
		
		this.fonts = new HashMap<>();
		
		canvas.save();
	}

	private void beforeDraw(RendererData data) {
		canvas.restore();
		canvas.save();
		
		if (debugBoxes) {
			drawDebugBox(data.getBounds());
		}
		
		setClip(data.getClip());
		canvas.translate(data.getBounds()[0]+data.getTranslateX(), data.getBounds()[1]+data.getTranslateY());
	}
	
	private void drawDebugBox(int[] bounds) {
		Rect rect = Rect.makeXYWH(bounds[0], bounds[1], bounds[2], bounds[3]);
		
		try (Paint paint = new Paint()) {
			paint.setColor((255<<24)+(0<<16)+(255<<8)+255);
			paint.setStroke(true);
			canvas.drawRect(rect, paint);
		}
	}

	private void setClip(int[] bounds) {
		int fx = Math.max(0, bounds[0]);
		int fy = Math.max(0, bounds[1]);
		int fl = Math.max(0, bounds[2]);
		int fh = Math.max(0, bounds[3]);
		
		canvas.clipRect(Rect.makeXYWH(fx, fy, fl, fh));
	}

	@Override
	public void drawFilledRect(RendererData data, int x, int y, int l, int h) {
		beforeDraw(data);
		if (l < 1 || h < 1) {
			return;
		}
		Rect rect = Rect.makeXYWH(x, y, l, h);
		
		try (Paint paint = new Paint()) {
			paint.setColor(toColor(data.getCurrentColor()));
			canvas.drawRect(rect, paint);
		}
	}
	
	@Override
	public void drawEllipse(RendererData data, int x, int y, int l, int h) {
		beforeDraw(data);
		RRect ellipse = RRect.makeOvalXYWH(x, y, l, h);
		
		try (Paint paint = new Paint()) {
			paint.setColor(toColor(data.getCurrentColor()));
			canvas.drawRRect(ellipse, paint);
		}
	}

	@Override
	public void drawLine(RendererData data, int x, int y, int l, int h) {
		beforeDraw(data);
		try (Paint paint = new Paint()) {
			paint.setColor(toColor(data.getCurrentColor()));
			canvas.drawLine(x, y, x+l, y+h, paint);
		}
	}

	@Override
	public int drawText(RendererData data, int x, int y, String text) {
		beforeDraw(data);
		
		RibbonSkijaFont font = (RibbonSkijaFont) data.getState().getFont();
		short[] glyphs = font.getRaw().getStringGlyphs(text);
		float[] widths = font.getRaw().getWidths(glyphs);
        float[] xpos = new float[glyphs.length];
        int distance = 0;
        for (int i = 0; i < xpos.length; i++) {
            xpos[i] = distance;
            distance += widths[i];
        }

        try (Paint paint = new Paint()) {
			paint.setColor(toColor(data.getCurrentColor()));
			canvas.drawTextBlob(TextBlob.makeFromPosH(glyphs, xpos, 0, font.getRaw()), x, y+font.getHeight(), paint);
        }
		
		return distance; //TODO
	}

	@Override
	public void draw() {
		if (context!=null) {
			context.flush();
		}
	}
	
	@Override
	public RibbonFont getFont(String name, int weight, int size) {
		// TODO: Remove dead fonts
		return fonts.computeIfAbsent(new FontInfo(name, weight, size), _1->RibbonSkijaFont.of(name, weight, size));
	}
	
	@Override
	public void paintMouseListener(RendererData rd, UIEventTarget c, int x, int y, int l, int h, EventListener<MouseEvent> listener) {
		x += rd.getBounds()[0];
		y += rd.getBounds()[1];
		y -= rd.getTranslateY();
		
		int[] bounds = Rectangle.intersectRaw(rd.getClip(), new int[] {x, y, l, h});
		
		lpl.onPaint(c, bounds, listener);
	}
	
	@Override
	public void paintListener(EventListener<UIEvent> listener) {
		if (lpl!=null) {
			lpl.onPaint(listener);
		}
	}
	
	public void onPaint(ListenerPaintListener lpl) {
		this.lpl = lpl;
	}
	
	private int toColor(Color foreground) {	
		return
			(foreground.getAlpha() << 24) +
			(foreground.getRed() << 16) +
			(foreground.getGreen() << 8) +
			foreground.getBlue(); // Equivalent to foreground.getBlue() << 0
	}

	public static RibbonSkijaRenderer of(long window, DirectContext context) {
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetFramebufferSize(window, width, height);

		int fbId = GL32.glGetInteger(GL32.GL_FRAMEBUFFER_BINDING); // GL_FRAMEBUFFER_BINDING
		BackendRenderTarget renderTarget = BackendRenderTarget.makeGL(
			width[0],
			height[0],
			0,
			8,
			fbId,
			FramebufferFormat.GR_GL_RGBA8);

		Surface surface = Surface.makeFromBackendRenderTarget(
			context,
			renderTarget,
			SurfaceOrigin.BOTTOM_LEFT,
			SurfaceColorFormat.RGBA_8888,
			colorSpace);
				
		Canvas canvas = surface.getCanvas();
		
		return new RibbonSkijaRenderer(canvas, context);
	}
}