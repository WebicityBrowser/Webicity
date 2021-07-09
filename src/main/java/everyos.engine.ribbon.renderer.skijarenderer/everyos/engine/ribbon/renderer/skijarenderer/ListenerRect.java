package everyos.engine.ribbon.renderer.skijarenderer;

import everyos.engine.ribbon.core.event.EventListener;
import everyos.engine.ribbon.core.event.MouseEvent;
import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.shape.Rectangle;

public class ListenerRect {
	private Rectangle bounds;
	private EventListener<MouseEvent> listener;
	private UIEventTarget target;

	public ListenerRect(Rectangle bounds, UIEventTarget target, EventListener<MouseEvent> listener) {
		this.bounds = bounds;
		this.listener = listener;
		this.target = target;
	}
	
	public Rectangle getBounds() {
		return bounds;
	}
	
	public EventListener<MouseEvent> getListener() {
		return listener;
	}
	
	public UIEventTarget getEventTarget() {
		return target;
	}
}
