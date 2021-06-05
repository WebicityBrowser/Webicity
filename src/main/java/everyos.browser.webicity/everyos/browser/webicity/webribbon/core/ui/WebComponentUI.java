package everyos.browser.webicity.webribbon.core.ui;

import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;

public interface WebComponentUI {
	//TODO: Also pass a stack
	void render(Renderer r, SizePosGroup sizepos, UIContext context);
	void paint(Renderer r, Rectangle viewport);
	//void composite(GUIRenderer r);
	
	WebComponentUI getParent();
	
	void invalidate();
	void invalidateLocal();
	void validate();
	boolean getValidated();
	
	UIBox getUIBox();

	void processEvent(UIEvent ev);
}
