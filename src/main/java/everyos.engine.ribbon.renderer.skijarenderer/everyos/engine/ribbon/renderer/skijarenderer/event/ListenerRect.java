package everyos.engine.ribbon.renderer.skijarenderer.event;

import everyos.engine.ribbon.core.event.EventListener;
import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.input.mouse.MouseEvent;
import everyos.engine.ribbon.core.shape.Rectangle;

public class ListenerRect {
	
	private final Rectangle bounds;
	private final EventListener<MouseEvent> listener;
	private final UIEventTarget target;

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
