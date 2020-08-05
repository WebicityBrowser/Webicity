package everyos.engine.ribbon.renderer.guirenderer;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.renderer.guirenderer.event.MouseListener;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;
import everyos.engine.ribbon.renderer.guirenderer.graphics.FontStyle;
import everyos.engine.ribbon.renderer.guirenderer.graphics.GUIState;

public interface GUIRenderer extends Renderer {
	//This will be an abstraction for implementation-dependent rendering code
	
	//Painting
	public GUIRenderer getSubcontext(int x, int y, int l, int h);
	public GUIRenderer getBufferedSubcontext(int x, int y, int width, int height);
	public void drawFilledRect(int x, int y, int width, int height);
	public void drawEllipse(int x, int y, int l, int h);
	public void drawLine(int x, int y, int l, int h);
	public void drawText(int x, int y, String text);
	public void setForeground(Color color);
	public void setBackground(Color color);
	public void useForeground();
	public void useBackground();
	public void draw();
	public int getFontHeight();
	public int getFontPaddingHeight();
	public int charWidth(int ch);
	public void setFont(String name, FontStyle style, int size);
	
	//User interaction
	public void onPaint(ListenerPaintListener listener);
	public void paintMouseListener(Component c, int x, int y, int l, int w, MouseListener listener); //TODO: We should be able to support non-rectangular shapes
	
	//State management
	public GUIState getState();
	public void restoreState(GUIState state);
	
	//Implementations should also include a method of creating/retrieving a window or screen
	
	//Allows for hooking into our renderer
	public static interface ListenerPaintListener {
		public void onPaint(Component c, int x, int y, int l, int h, MouseListener listener);
	}
}
