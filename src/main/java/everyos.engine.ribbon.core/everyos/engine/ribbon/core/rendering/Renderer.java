package everyos.engine.ribbon.core.rendering;

import everyos.engine.ribbon.core.event.MouseListener;
import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.graphics.FontStyle;
import everyos.engine.ribbon.core.graphics.GUIState;

public interface Renderer {
	//This will be an abstraction for implementation-dependent rendering code

	//Painting
	public Renderer getSubcontext(int x, int y, int l, int h);
	public void drawFilledRect(int x, int y, int width, int height);
	public void drawEllipse(int x, int y, int l, int h);
	public void drawLine(int x, int y, int l, int h);
	public int drawText(int x, int y, String text);
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
	//TODO: We should be able to support non-rectangular shapes
	public void paintMouseListener(UIEventTarget c, int x, int y, int l, int w, MouseListener listener);
	
	//State management
	public GUIState getState();
	public void restoreState(GUIState state);
	
	//Implementations should also include a method of creating/retrieving a window or screen
	
	//Allows for hooking into our renderer
	public static interface ListenerPaintListener {
		public void onPaint(UIEventTarget c, int x, int y, int l, int h, MouseListener listener);
	}
}
