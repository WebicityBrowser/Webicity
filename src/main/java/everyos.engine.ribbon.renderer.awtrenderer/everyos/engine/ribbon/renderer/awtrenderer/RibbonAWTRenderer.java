package everyos.engine.ribbon.renderer.awtrenderer;


import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;
import everyos.engine.ribbon.renderer.guirenderer.graphics.FontStyle;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIState;

public class RibbonAWTRenderer implements GUIRenderer {
	private Graphics g;
	private BufferedImage image;
	private Graphics parent;
	private int x;
	private int y;
	private int l;
	private int h;
	private FontMetrics metrics;
	private int height = -1;
	private GUIState state;

	public RibbonAWTRenderer(Graphics g, GUIState state) {
		this.g = g;
		this.state = state;
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	public RibbonAWTRenderer(Graphics parent, GUIState state, int x, int y, int l, int h) {
		this.image = new BufferedImage(l, h, BufferedImage.TYPE_INT_ARGB);
		this.g = image.getGraphics();
		this.state = state;
		this.parent = parent;
		this.x = x; this.y = y;
		this.l = l; this.h = h;
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	@Override public GUIRenderer getSubcontext(int x, int y, int l, int h) {
		return new RibbonAWTRenderer(g.create(x, y, l, h), state);
	}

	@Override public void drawFilledRect(int x, int y, int l, int h) {
		g.fillRect(x, y, l, h);
	}
	
	@Override public void drawEllipse(int x, int y, int l, int h) {
		g.fillOval(x, y, l, h);
	}

	@Override public void drawLine(int x, int y, int l, int h) {
		g.drawLine(x, y, x+l, y+h);
	}

	@Override public void drawText(int x, int y, String text) {
		g.drawString(text, x, y+g.getFontMetrics().getAscent());
	}

	@Override public void setColor(Color color) {
		g.setColor(new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue()));
	}

	@Override public GUIRenderer getBufferedSubcontext(int x, int y, int width, int height) {
		return new RibbonAWTRenderer(g, state, x, y, width, height);
	}

	@Override public void draw() {
		if (this.image!=null) {
			parent.drawImage(image, x, y, l, h, null);
		}
	}

	@Override public int getFontHeight() {
		if (height==-1) {
			if (metrics==null) metrics = g.getFontMetrics();
			height = metrics.getHeight();
		}
		return height;
	}
	
	@Override public int getFontPaddingHeight() {
		if (metrics==null) metrics = g.getFontMetrics();
		return metrics.getMaxDescent();
	}

	@Override public int charWidth(int ch) {
		if (metrics==null) metrics = g.getFontMetrics();
		return metrics.charWidth(ch);
	}

	@Override public void setFont(String name, FontStyle style, int size) {
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

	@Override public GUIState getState() {
		return state;
	}
	@Override public void restoreState(GUIState state) {
		this.state = state;
	}
}
