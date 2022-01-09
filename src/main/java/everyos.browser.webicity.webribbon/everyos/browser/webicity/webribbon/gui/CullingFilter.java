package everyos.browser.webicity.webribbon.gui;

import everyos.engine.ribbon.core.shape.Rectangle;

public interface CullingFilter {
	
	boolean intersectsWith(Rectangle viewport);
	
	public static CullingFilter getRectangularCullingFilter(Rectangle bounds) {
		return viewport -> viewport.intersects(bounds);
	}
	
}
