package everyos.engine.ribbon.core.rendering;

import everyos.engine.ribbon.core.event.MouseListener;
import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.graphics.FontStyle;
import everyos.engine.ribbon.core.graphics.GUIState;

public interface Renderer {
	// This will be an abstraction for implementation-dependent rendering code

	// Painting
	Renderer getSubcontext(int x, int y, int l, int h);
	void drawFilledRect(int x, int y, int width, int height);
	void drawEllipse(int x, int y, int l, int h);
	void drawLine(int x, int y, int l, int h);
	int drawText(int x, int y, String text);
	void setForeground(Color color);
	void setBackground(Color color);
	void useForeground();
	void useBackground();
	void draw();
	int getFontHeight();
	int getFontPaddingHeight();
	int charWidth(int ch);
	void setFont(String name, FontStyle style, int size);
	
	// "Settings"
	void setScrollY(int scrollY);
	int getScrollY();
	
	// User interaction
	void onPaint(ListenerPaintListener listener);
	// TODO: We should be able to support non-rectangular shapes
	void paintMouseListener(UIEventTarget c, int x, int y, int l, int w, MouseListener listener);
	
	// State management
	GUIState getState();
	void restoreState(GUIState state);
	
	// Implementations should also include a method of creating/retrieving a window or screen
	
	// Allows for hooking into our renderer
	public static interface ListenerPaintListener {
		void onPaint(UIEventTarget c, int x, int y, int l, int h, MouseListener listener);
	}
}
