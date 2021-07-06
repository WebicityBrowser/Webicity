package everyos.engine.ribbon.renderer.skijarenderer;

import org.jetbrains.skija.BackendRenderTarget;
import org.jetbrains.skija.Canvas;
import org.jetbrains.skija.ColorSpace;
import org.jetbrains.skija.DirectContext;
import org.jetbrains.skija.Font;
import org.jetbrains.skija.FontMetrics;
import org.jetbrains.skija.FontMgr;
import org.jetbrains.skija.FramebufferFormat;
import org.jetbrains.skija.Paint;
import org.jetbrains.skija.RRect;
import org.jetbrains.skija.Rect;
import org.jetbrains.skija.Surface;
import org.jetbrains.skija.SurfaceColorFormat;
import org.jetbrains.skija.SurfaceOrigin;
import org.jetbrains.skija.TextBlob;
import org.jetbrains.skija.Typeface;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL32;

import everyos.engine.ribbon.core.event.MouseListener;
import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.graphics.FontStyle;
import everyos.engine.ribbon.core.graphics.GUIState;
import everyos.engine.ribbon.core.rendering.Renderer;

public class RibbonSkijaRenderer implements Renderer, AutoCloseable {
	private static ColorSpace colorSpace = ColorSpace.getSRGB();
	private Canvas canvas;
	private int 
		x = 0,
		y = 0,
		l = 0,
		h = 0;
	private int
		scrollY = 0;
	private int color;
	private GUIState state;
	private ListenerPaintListener lpl;
	private FontMetrics metrics;
	private DirectContext context;
	private RibbonSkijaRenderer parentRenderer;
	private Font font;
	private int[] widthCache;
	private FontMgr manager;
	private Typeface typeface;
	private int fontHeight;
	
	private boolean debugBoxes = false;
	
	private RibbonSkijaRenderer(int x, int y, int l, int h) {
		/*if (x<0) x = 0;
		if (y<0) y = 0;*/
		if (l<0) l = 0;
		if (h<0) h = 0;
		this.x = x; this.y = y;
		this.l = l; this.h = h;
	}
	
	private RibbonSkijaRenderer(RibbonSkijaRenderer parentRenderer, GUIState state, int x, int y, int l, int h) {
		//TODO: Anti-aliasing
		this(x, y, l, h);
		
		this.state = state;
		this.parentRenderer = parentRenderer;
		this.color = parentRenderer.color;
		this.canvas = parentRenderer.canvas;
		this.font = parentRenderer.font;
		this.widthCache = parentRenderer.widthCache;
	}
	
	public RibbonSkijaRenderer(DirectContext context, Canvas canvas, GUIState state, int x, int y, int l, int h) {
		this(x, y, l, h);
		this.state = state;
		this.canvas = canvas;
		this.context = context;
		this.manager = FontMgr.getDefault();
		this.typeface = manager.matchFamilyStyle("Times New Roman", org.jetbrains.skija.FontStyle.NORMAL);
		this.font = new Font(typeface, 12);
		this.color = 0xFF << 24;
		
		canvas.save();
	}

	private void beforeDraw() {
		if (debugBoxes) {
			canvas.restore();
			canvas.save();
			Rect rect = Rect.makeXYWH(this.x, this.y, this.l, this.h);
			
			try (Paint paint = new Paint()) {
				paint.setColor((255<<24)+(0<<16)+(255<<8)+255);
				paint.setStroke(true);
				canvas.drawRect(rect, paint);
			}
		}
		
		setClip(this.x, this.y, this.l, this.h);
	}
	
	private void setClip(int x, int y, int l, int h) {
		if (y<this.y) y = this.y;
		if (l>this.l) l = this.l;
		if (h>this.h) h = this.h;
		if (parentRenderer == null) {
			int fx = Math.max(0, x);
			int fy = Math.max(0, y);
			int fl = Math.max(0, l);
			int fh = Math.max(0, h);
			
			canvas.restore();
			canvas.save();
			canvas.clipRect(Rect.makeXYWH(fx, fy, fl, fh));
			return;
		}
		
		parentRenderer.setClip(x, y, l, h);
	}

	@Override
	public void translate(int dx, int dy) {
		this.x+=dx; this.y+=dy;
	}

	@Override
	public Renderer getSubcontext(int x, int y, int l, int h) {
		RibbonSkijaRenderer r = new RibbonSkijaRenderer(
			this, state.clone(),
			this.x+x, this.y+y-this.scrollY,
			l, h);
		r.onPaint((c, ex, ey, el, eh, listener)->{
			this._paintMouseListener(c, x+ex, y+ey, el, eh, listener);
		});
		return r;
	}

	@Override
	public void drawFilledRect(int x, int y, int l, int h) {
		beforeDraw();
		if (l<1 || h<1) return;
		Rect rect = Rect.makeXYWH(this.x+x, this.y+y-this.scrollY, l, h);
		
		try (Paint paint = new Paint()) {
			paint.setColor(color);
			canvas.drawRect(rect, paint);
		}
	}
	
	@Override
	public void drawEllipse(int x, int y, int l, int h) {
		beforeDraw();
		RRect ellipse = RRect.makeOvalXYWH(this.x+x, this.y+y-this.scrollY, l, h);
		try (Paint paint = new Paint()) {
			paint.setColor(color);
			canvas.drawRRect(ellipse, paint);
		}
	}

	@Override
	public void drawLine(int x, int y, int l, int h) {
		beforeDraw();
		try (Paint paint = new Paint()) {
			paint.setColor(color);
			canvas.drawLine(this.x+x, this.y+y-this.scrollY, this.x+x+l, this.y+y+h-this.scrollY, paint);
		}
	}

	@Override
	public int drawText(int x, int y, String text) {
		withMetrics();
		beforeDraw();
		
		short[] glyphs = font.getStringGlyphs(text);
		float[] widths = font.getWidths(glyphs);
        float[] xpos = new float[glyphs.length];
        int distance = 0;
        for (int i = 0; i < xpos.length; i++) {
            xpos[i] = distance;
            distance += widths[i];
        }

        try (Paint paint = new Paint()) {
			paint.setColor(color);
			canvas.drawTextBlob(TextBlob.makeFromPosH(glyphs, xpos, 0, font), this.x+x, this.y+y+fontHeight-this.scrollY, paint);
        }
		
		return distance; //TODO
	}

	@Override
	public void setForeground(Color color) {
		state.setForeground(color);
	}

	@Override
	public void setBackground(Color color) {
		state.setBackground(color);
	}

	@Override
	public void useForeground() {
		color = toColor(state.getForeground());
	}

	@Override
	public void useBackground() {
		color = toColor(state.getBackground());
	}

	@Override
	public void draw() {
		if (context!=null) {
			context.flush();
		}
	}

	@Override
	public int getFontHeight() {
		withMetrics();
		return fontHeight;
	}
	
	private void withMetrics() {
		if (metrics==null) {
			metrics = font.getMetrics();
			widthCache = new int[256];
			fontHeight = (int) metrics.getHeight();
		}
	}

	@Override
	public int getFontPaddingHeight() {
		withMetrics();
		return (int) metrics.getDescent()+1;
	}

	@Override
	public int charWidth(int ch) {
		withMetrics();
		if (ch<256 && widthCache[ch]!=0) {
			return widthCache[ch];
		}
		short glyph = font.getUTF32Glyph(ch);
		int width = (int) font.getWidths(new short[] {glyph})[0];
		if (ch<256) {
			widthCache[ch] = width;
		}
		return width;
	}

	@Override
	public void setFont(String name, FontStyle style, int size) {
		
	}
	
	@Override
	public void setScrollY(int scrollY) {
		this.scrollY = scrollY;
	}

	@Override
	public int getScrollY() {
		return this.scrollY;
	}

	@Override
	public void onPaint(ListenerPaintListener l) {
		this.lpl = l;
	}
	
	@Override
	public void paintMouseListener(UIEventTarget c, int x, int y, int l, int h, MouseListener listener) {
		_paintMouseListener(c, x, y, l, h, listener);
	}
	
	private void _paintMouseListener(UIEventTarget c, int x, int y, int l, int h, MouseListener listener) {
		//TODO: Solve why this does not work properly with scroll
		y -= this.scrollY;
		
		if (this.x==-1) {
			if (lpl!=null) lpl.onPaint(c, x, y, l, h, listener);
			return;
		}
		
		int x2 = x+l;
		int y2 = y+h;
		if (x<0) x=0;
		if (y<0) y=0;
		if (x2>this.l) x2=this.l;
		if (y2>this.h) y2=this.h;
		l = x2-x;
		h = y2-y;
		
		if (lpl!=null) lpl.onPaint(c, x, y, l, h, listener);
	}
	
	@Override
	public GUIState getState() {
		return state;
	}
	@Override
	public void restoreState(GUIState state) {
		this.state = state;
	}
	
	@Override
	public void close() {}
	
	private int toColor(Color foreground) {	
		return
			(0xFF << 24) +
			(foreground.getRed() << 16) +
			(foreground.getGreen() << 8) +
			foreground.getBlue(); // Equivalent to foreground.getBlue() << 0
	}

	public static RibbonSkijaRenderer of(long window) {
		int[] width = new int[1];
		int[] height = new int[1];
		GLFW.glfwGetFramebufferSize(window, width, height);
		
		DirectContext context = DirectContext.makeGL();

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
		return new RibbonSkijaRenderer(context, canvas, new GUIState(), 0, 0, width[0], height[0]) {
			@Override
			public void close() {
				surface.close();
				renderTarget.close();
				context.close();
			}
		};
	}
}
