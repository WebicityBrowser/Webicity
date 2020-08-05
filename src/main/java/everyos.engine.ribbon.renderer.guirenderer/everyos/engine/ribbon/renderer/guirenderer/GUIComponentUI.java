package everyos.engine.ribbon.renderer.guirenderer;

import everyos.engine.ribbon.core.ui.ComponentUI;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;

public abstract class GUIComponentUI implements ComponentUI {
	//TODO: Also pass a stack
	public abstract void render(GUIRenderer r, SizePosGroup sizepos, UIManager<GUIComponentUI> uimgr);
	public abstract void paint(GUIRenderer r);
	//public abstract void composite(GUIRenderer r);
	
	public abstract GUIComponentUI getParent();
	
	public abstract void invalidateLocal();
	public abstract void validate();
	public abstract boolean getValidated();
	
	public abstract void repaintLocal();
}
