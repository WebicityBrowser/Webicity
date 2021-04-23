package everyos.engine.ribbon.renderer.awtrenderer;

import everyos.engine.ribbon.core.event.MouseListener;
import everyos.engine.ribbon.core.event.UIEventTarget;
import everyos.engine.ribbon.core.shape.Rectangle;

public class ListenerRect {
	private Rectangle bounds;
	private MouseListener listener;
	private UIEventTarget target;

	public ListenerRect(Rectangle bounds, UIEventTarget target, MouseListener listener) {
		this.bounds = bounds;
		this.listener = listener;
		this.target = target;
	}
	
	public Rectangle getBounds() { return bounds; }
	public MouseListener getListener() { return listener; }
	public UIEventTarget getEventTarget() { return target; };
}
