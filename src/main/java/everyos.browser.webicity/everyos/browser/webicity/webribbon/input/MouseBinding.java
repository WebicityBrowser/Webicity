package everyos.browser.webicity.webribbon.input;

import everyos.browser.webicity.webribbon.component.WebComponent;
import everyos.engine.ribbon.renderer.guirenderer.shape.Rectangle;

public class MouseBinding {
	public int type;
	public WebComponent component;
	public Rectangle bounds;
	public Rectangle obounds;

	public MouseBinding(Rectangle bounds, WebComponent c) {
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