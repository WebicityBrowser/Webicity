package everyos.browser.webicity.webribbon.core.ui;

import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.renderer.guirenderer.event.UIEvent;
import everyos.engine.ribbon.renderer.guirenderer.shape.Rectangle;

public interface WebComponentUI {
	void invalidate();
	
	//TODO: Also pass a stack
	void render(Renderer r, SizePosGroup sizepos, UIContext context);
	void paint(Renderer r, Rectangle viewport);
	//void composite(GUIRenderer r);
	
	WebComponentUI getParent();
	
	void invalidateLocal();
	void validate();
	boolean getValidated();
	
	void repaintLocal();
	UIBox getUIBox();

	void processEvent(UIEvent ev);
}
