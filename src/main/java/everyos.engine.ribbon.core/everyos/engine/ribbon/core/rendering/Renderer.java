package everyos.engine.ribbon.core.rendering;

import everyos.engine.ribbon.core.event.EventListener;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.event.UIEventTarget;

public interface Renderer {
	// This will be an abstraction for implementation-dependent rendering code

	// Painting
	void drawFilledRect(RendererData data, int x, int y, int width, int height);
	void drawEllipse(RendererData data, int x, int y, int l, int h);
	void drawLine(RendererData data, int x, int y, int l, int h);
	int drawText(RendererData data, int x, int y, String text);
	void draw();
	
	// Data objects
	RibbonFont getFont(String name, int weight, int size);
	
	// TODO: We should be able to support non-rectangular shapes
	void paintMouseListener(RendererData data, UIEventTarget c, int x, int y, int l, int w, EventListener<MouseEvent> listener);
	void paintListener(EventListener<UIEvent> listener);
	
	// Implementations should also include a method of creating/retrieving a window or screen
}