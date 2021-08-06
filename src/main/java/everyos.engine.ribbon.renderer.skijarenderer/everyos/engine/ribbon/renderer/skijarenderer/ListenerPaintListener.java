package everyos.engine.ribbon.renderer.skijarenderer;

import everyos.engine.ribbon.core.event.EventListener;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.event.UIEventTarget;

public interface ListenerPaintListener {
	void onPaint(UIEventTarget c, int[] bounds, EventListener<MouseEvent> listener);
	void onPaint(EventListener<UIEvent> listener);
}