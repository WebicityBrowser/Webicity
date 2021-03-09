package everyos.browser.webicity.webribbon.core.ui;

import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.rendering.Renderer;

public interface WebComponentUI {
	public void invalidate();
	
	//TODO: Also pass a stack
	public abstract void render(Renderer r, SizePosGroup sizepos, WebUIManager uimgr);
	public abstract void paint(Renderer r);
	//public abstract void composite(GUIRenderer r);
	
	public abstract WebComponentUI getParent();
	
	public abstract void invalidateLocal();
	public abstract void validate();
	public abstract boolean getValidated();
	
	public abstract void repaintLocal();
}
