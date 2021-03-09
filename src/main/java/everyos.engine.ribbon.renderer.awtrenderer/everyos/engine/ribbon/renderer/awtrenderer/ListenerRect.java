package everyos.engine.ribbon.renderer.awtrenderer;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.renderer.guirenderer.event.MouseListener;
import everyos.engine.ribbon.renderer.guirenderer.shape.Rectangle;

public class ListenerRect {
	private Rectangle bounds;
	private MouseListener listener;
	private Component component;

	public ListenerRect(Rectangle bounds, Component component, MouseListener listener) {
		this.bounds = bounds;
		this.listener = listener;
		this.component = component;
	}
	
	public Rectangle getBounds() { return bounds; }
	public MouseListener getListener() { return listener; }
	public Component getComponent() { return component; };
}
