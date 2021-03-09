package everyos.engine.ribbon.renderer.guirenderer.input;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.renderer.guirenderer.shape.Rectangle;

public class MouseBinding {
	public int type;
	public Component component;
	public Rectangle bounds;
	public Rectangle obounds;

	public MouseBinding(Rectangle bounds, Component c) {
		this.bounds = (Rectangle) bounds.clone();
		this.obounds = (Rectangle) bounds.clone();
		this.component = c;
	}
	
	
	public boolean aabb(int x, int y) {
		return
			x>=bounds.x&&x<=bounds.x+bounds.width&&
			y>=bounds.y&&y<=bounds.y+bounds.height;
	}
}
