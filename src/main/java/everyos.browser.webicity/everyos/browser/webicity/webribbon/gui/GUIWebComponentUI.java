package everyos.browser.webicity.webribbon.gui;

import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.renderer.guirenderer.GUIRenderer;

public abstract class GUIWebComponentUI implements WebComponentUI {
	//TODO: Also pass a stack
	public abstract void render(GUIRenderer r, SizePosGroup sizepos, WebUIManager<GUIWebComponentUI> uimgr);
	public abstract void paint(GUIRenderer r);
	//public abstract void composite(GUIRenderer r);
	
	public abstract GUIWebComponentUI getParent();
	
	public abstract void invalidateLocal();
	public abstract void validate();
	public abstract boolean getValidated();
	
	public abstract void repaintLocal();
}
