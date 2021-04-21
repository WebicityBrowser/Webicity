package everyos.engine.ribbon.renderer.awtrenderer;


import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.renderer.guirenderer.event.MouseListener;
import everyos.engine.ribbon.renderer.guirenderer.event.UIEventTarget;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;
import everyos.engine.ribbon.renderer.guirenderer.graphics.FontStyle;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIState;

public class RibbonAWTRenderer implements Renderer {
	private Graphics g;
	private int x = -1;
	private int y = -1;
	private int l = -1;
	private int h = -1;
	private FontMetrics metrics;
	private int height = -1;
	private GUIState state;
	private ListenerPaintListener lpl;

	public RibbonAWTRenderer(Graphics g, GUIState state) {
		this.g = g;
		this.state = state;
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Override
	public Renderer getSubcontext(int x, int y, int l, int h) {
		RibbonAWTRenderer r = new RibbonAWTRenderer(g.create(x, y, l, h), state.clone());
		r.x = x; r.y = y;
		r.l = l; r.h = h;
		r.onPaint((c, ex, ey, el, eh, listener)->{
			this.paintMouseListener(c, ex, ey, el, eh, listener);
		});
		return r;
	}

	@Override
	public void drawFilledRect(int x, int y, int l, int h) {
		g.fillRect(x, y, l, h);
	}
	
	@Override
	public void drawEllipse(int x, int y, int l, int h) {
		g.fillOval(x, y, l, h);
	}

	@Override
	public void drawLine(int x, int y, int l, int h) {
		g.drawLine(x, y, x+l, y+h);
	}

	@Override
	public int drawText(int x, int y, String text) {
		g.drawString(text, x, y+g.getFontMetrics().getAscent());
		return g.getFontMetrics().stringWidth(text);
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
		g.setColor(color(state.getForeground()));
	}

	@Override
	public void useBackground() {
		g.setColor(color(state.getBackground()));
	}

	@Override
	public void draw() {}

	@Override
	public int getFontHeight() {
		if (height==-1) {
			if (metrics==null) metrics = g.getFontMetrics();
			height = metrics.getHeight();
		}
		return height;
	}
	
	@Override
	public int getFontPaddingHeight() {
		if (metrics==null) metrics = g.getFontMetrics();
		return metrics.getMaxDescent();
	}

	@Override
	public int charWidth(int ch) {
		if (metrics==null) metrics = g.getFontMetrics();
		return metrics.charWidth(ch);
	}

	@Override
	public void setFont(String name, FontStyle style, int size) {
		this.metrics = null;
		this.height = -1;
		int fontstyle;
		switch(style) {
			case PLAIN:
				fontstyle = Font.PLAIN;
				break;
			default:
				fontstyle = Font.PLAIN;
		}
		g.setFont(new Font(name, fontstyle, size));
	}

	@Override
	public void onPaint(ListenerPaintListener l) {
		this.lpl = l;
	}
	@Override
	public void paintMouseListener(UIEventTarget c, int x, int y, int l, int h, MouseListener listener) {
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
		if (lpl!=null) lpl.onPaint(c, this.x+x, this.y+y, l, h, listener);
	}
	
	@Override
	public GUIState getState() {
		return state;
	}
	@Override
	public void restoreState(GUIState state) {
		this.state = state;
	}
	
	private java.awt.Color color(Color color) {
		return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue());
	}
}
