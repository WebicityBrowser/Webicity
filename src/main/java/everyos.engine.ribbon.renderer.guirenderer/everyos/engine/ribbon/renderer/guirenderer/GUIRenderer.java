package everyos.engine.ribbon.renderer.guirenderer;

import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;
import everyos.engine.ribbon.renderer.guirenderer.graphics.FontStyle;

public interface GUIRenderer extends Renderer {
	//This will be an abstraction for implementation-dependent rendering code
	public GUIRenderer getSubcontext(int x, int y, int l, int h);
	public GUIRenderer getBufferedSubcontext(int x, int y, int width, int height);
	public void drawFilledRect(int x, int y, int width, int height);
	public void drawEllipse(int x, int y, int l, int h);
	public void drawLine(int x, int y, int l, int h);
	public void drawText(int x, int y, String text);
	public void setColor(Color color); //TODO: Not AWT colors
	public void draw();
	public int getFontHeight();
	public int getFontPaddingHeight();
	public int charWidth(int ch);
	public void setFont(String name, FontStyle style, int size);
	
	//Implementations should also include a method of creating/retrieving a window or screen
}
