package everyos.browser.webicity.webribbon.gui.input;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.engine.ribbon.core.shape.Rectangle;

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
			x>=bounds.getX()&&x<=bounds.getX()+bounds.getWidth()&&
			y>=bounds.getY()&&y<=bounds.getY()+bounds.getHeight();
	}
}