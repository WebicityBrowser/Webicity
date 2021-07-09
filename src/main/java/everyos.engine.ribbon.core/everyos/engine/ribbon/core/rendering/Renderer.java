package everyos.engine.ribbon.core.rendering;

import everyos.engine.ribbon.core.event.EventListener;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.graphics.FontStyle;
import everyos.engine.ribbon.core.graphics.GUIState;

public interface Renderer {
	// This will be an abstraction for implementation-dependent rendering code

	// Transformations
	void translate(int dx, int dy);

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
	void paintMouseListener(UIEventTarget c, int x, int y, int l, int w, EventListener<MouseEvent> listener);
	void paintListener(EventListener<UIEvent> listener);
	
	// State management
	GUIState getState();
	void restoreState(GUIState state);
	
	// Implementations should also include a method of creating/retrieving a window or screen
	
	// Allows for hooking into our renderer
	public static interface ListenerPaintListener {
		void onPaint(UIEventTarget c, int x, int y, int l, int h, EventListener<MouseEvent> listener);
		void onPaint(EventListener<UIEvent> listener);
	}

	// Other drawing methods with a default implementation

	/**
	 * @param x Abscissa of top left point of an imaginary rectangle which bounds the shape.
	 * @param y Ordinate of top left point of an imaginary rectangle which bounds the shape.
	 * @param w Width of an imaginary rectangle which bounds the shape.
	 * @param h Height of an imaginary rectangle which bounds the shape.
	 * @param d Diameter of circles forming the round corners.
	 */
	// TODO: Add special cases for construction when w or h or both are less than d. However that's a rare case.
	default void fillRoundRect(int x, int y, int w, int h, int d) {

		// Top Left Corner
		drawEllipse(x, y, d, d);
		// Top Right Corner
		drawEllipse(x + w - d, y, d, d);
		// Bottom Left Corner
		drawEllipse(x, y + h - d, d, d);
		// Bottom Right Corner
		drawEllipse(x + w - d, y + h - d, d, d);

		int r = d / 2;

		// Rectangle at top lying between top two corner circles
		drawFilledRect(x + r, y, w - d, d);
		// Rectangle at left lying between left two corner circles
		drawFilledRect(x, y + r, d, h - d);
		// Rectangle at right lying between right two corner circles
		drawFilledRect(x + w - d, y + r, d, h - d);
		// Rectangle at bottom lying between bottom two corner circles
		drawFilledRect(x + r, y + h - d, w - d, d);

		// Rectangle filling the void created in between
		drawFilledRect(x + d, y + d, w - d - d,  h - d - d);
	}
}
