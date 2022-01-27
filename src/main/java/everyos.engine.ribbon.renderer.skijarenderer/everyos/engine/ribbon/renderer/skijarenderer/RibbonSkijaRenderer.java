package everyos.engine.ribbon.renderer.skijarenderer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL32;

import everyos.engine.ribbon.core.event.EventListener;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.graphics.paintfill.Color;
import everyos.engine.ribbon.core.graphics.paintfill.PaintFill;
import everyos.engine.ribbon.core.input.mouse.MouseEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.rendering.ResourceGenerator;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.renderer.skijarenderer.event.ListenerPaintListener;
import io.github.humbleui.skija.BackendRenderTarget;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.FramebufferFormat;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.SurfaceColorFormat;
import io.github.humbleui.skija.SurfaceOrigin;
import io.github.humbleui.skija.TextBlob;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;

public class RibbonSkijaRenderer implements Renderer {
	
	private static final ColorSpace colorSpace = ColorSpace.getSRGB();
	private static final ResourceGenerator resourceGenerator = new SkijaResourceGenerator();
	private static final boolean debugBoxes = false;
	
	private final DirectContext context;
	private final Canvas canvas;
	
	private ListenerPaintListener lpl;
	
	public RibbonSkijaRenderer(Canvas canvas, DirectContext context) {
		this.context = context;
		this.canvas = canvas;
		
		canvas.save();
	}

	@Override
	public void drawFilledRect(RendererData data, int x, int y, int l, int h) {
		beforeDraw(data);
		if (l < 1 || h < 1) {
			return;
		}
		Rect rect = Rect.makeXYWH(x, y, l, h);
		
		try (Paint paint = createPaint(data)) {
			canvas.drawRect(rect, paint);
		}
	}
	
	@Override
	public void drawEllipse(RendererData data, int x, int y, int l, int h) {
		beforeDraw(data);
		if (l < 1 || h < 1) {
			return;
		}
		RRect ellipse = RRect.makeOvalXYWH(x, y, l, h);
		
		try (Paint paint = createPaint(data)) {
			canvas.drawRRect(ellipse, paint);
		}
	}

	@Override
	public void drawLine(RendererData data, int x, int y, int l, int h) {
		beforeDraw(data);
		if (l < 0 || h < 0) {
			return;
		}
		try (Paint paint = createPaint(data)) {
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

        try (Paint paint = createPaint(data)) {
			canvas.drawTextBlob(TextBlob.makeFromPosH(glyphs, xpos, 0, font.getRaw()), x, y+font.getHeight()-font.getPaddingHeight(), paint);
        }
		
		return distance; //TODO
	}

	@Override
	public void draw() {
		if (context != null) {
			context.flush();
		}
	}
	
	@Override
	public void paintMouseListener(RendererData rd, UIEventTarget c, int x, int y, int l, int h, EventListener<MouseEvent> listener) {
		x += rd.getBounds()[0];
		y += rd.getBounds()[1];
		y -= rd.getTranslate()[1];
		
		int[] bounds = Rectangle.intersectRaw(rd.getClip(), new int[] {x, y, l, h});
		
		lpl.onPaint(c, bounds, listener);
	}
	
	@Override
	public void paintListener(EventListener<UIEvent> listener) {
		if (lpl!=null) {
			lpl.onPaint(listener);
		}
	}
	
	@Override
	public ResourceGenerator getResourceGenerator() {
		return resourceGenerator;
	}
	
	protected void onPaint(ListenerPaintListener lpl) {
		this.lpl = lpl;
	}
	
	private void beforeDraw(RendererData data) {
		canvas.restore();
		canvas.save();
		
		if (debugBoxes) {
			drawDebugBox(data.getBounds());
		}
		
		setClip(data.getClip());
		canvas.translate(data.getBounds()[0]+data.getTranslate()[0], data.getBounds()[1]+data.getTranslate()[1]);
	}
	
	private void drawDebugBox(int[] bounds) {
		int fx = Math.max(0, bounds[0]);
		int fy = Math.max(0, bounds[1]);
		int fl = Math.max(0, bounds[2]);
		int fh = Math.max(0, bounds[3]);
		
		Rect rect = Rect.makeXYWH(fx, fy, fl, fh);
		
		try (Paint paint = new Paint()) {
			paint.setColor((255 << 24) + (0 << 16) + (255 << 8) + 255);
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
	
	private int toColor(Color foreground) {	
		return
			(foreground.getAlpha() << 24) +
			(foreground.getRed() << 16) +
			(foreground.getGreen() << 8) +
			foreground.getBlue(); // Equivalent to foreground.getBlue() << 0
	}
	
	private Paint createPaint(RendererData rd) {
		Paint paint = new Paint();
		PaintFill fill = rd.getCurrentPaintFill();
		
		if (fill instanceof Color) {
			paint.setColor(toColor(((Color) fill)));
		} else {
			throw new UnsupportedOperationException("Unsupported paint fill: " + String.valueOf(fill));
		}
		
		return paint;
	}

	public static RibbonSkijaRenderer of(long window, DirectContext context) {
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetFramebufferSize(window, width, height);

		int fbId = GL32.glGetInteger(GL32.GL_FRAMEBUFFER_BINDING); // GL_FRAMEBUFFER_BINDING
		BackendRenderTarget renderTarget = BackendRenderTarget.makeGL(
			width[0], height[0],
			0, 8, fbId,
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