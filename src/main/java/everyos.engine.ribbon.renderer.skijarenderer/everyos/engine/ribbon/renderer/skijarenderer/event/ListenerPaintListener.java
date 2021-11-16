package everyos.engine.ribbon.renderer.skijarenderer.event;

import everyos.engine.ribbon.core.event.EventListener;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.input.mouse.MouseEvent;

public interface ListenerPaintListener {
	
	void onPaint(UIEventTarget c, int[] bounds, EventListener<MouseEvent> listener);
	void onPaint(EventListener<UIEvent> listener);
	
}