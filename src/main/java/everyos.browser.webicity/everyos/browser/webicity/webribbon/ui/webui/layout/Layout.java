package everyos.browser.webicity.webribbon.ui.webui.layout;

import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;

public interface Layout {
	void render(Renderer r, SizePosGroup sizepos, UIContext context, Appearence appearence);
	void paint(Renderer r, Rectangle viewport, Appearence appearence);
	UIBox getComputedUIBox();
	void processEvent(UIEvent event);
}
