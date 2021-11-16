package everyos.browser.webicity.webribbon.gui;

import everyos.browser.spec.javadom.intf.inf.ActivationBehaviorContext;
import everyos.browser.webicity.webribbon.core.ui.Pallete;
import everyos.engine.ribbon.core.rendering.Renderer;

public interface WebPaintContext {
	
	Renderer getRenderer();
	Pallete getPallete();
	ActivationBehaviorContext getActivationBehaviorContext();
	
}
