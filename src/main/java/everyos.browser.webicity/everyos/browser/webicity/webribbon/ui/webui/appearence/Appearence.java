package everyos.browser.webicity.webribbon.ui.webui.appearence;

import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;

public interface Appearence {
	void render(Renderer r, SizePosGroup sizepos, UIContext context);
	void paint(Renderer r, Rectangle viewport);
}
