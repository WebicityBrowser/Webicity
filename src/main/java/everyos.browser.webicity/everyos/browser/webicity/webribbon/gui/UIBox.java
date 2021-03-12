package everyos.browser.webicity.webribbon.gui;

import everyos.engine.ribbon.renderer.guirenderer.shape.Rectangle;

public interface UIBox {
	boolean intersectsWith(Rectangle viewport);
	
	public static UIBox getRectangularUIBox(Rectangle bounds) {
		return viewport->viewport.intersects(bounds);
	}
	
	//TODO: Ability to get Z-index
}
