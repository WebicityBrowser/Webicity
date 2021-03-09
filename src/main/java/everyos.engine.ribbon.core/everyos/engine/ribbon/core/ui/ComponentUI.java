package everyos.engine.ribbon.core.ui;

import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.renderer.guirenderer.shape.SizePosGroup;

public interface ComponentUI {
	public void directive(UIDirective directive);
	public void invalidate();
	public void hint(int hint);
	
	//TODO: Also pass a stack
	void render(Renderer r, SizePosGroup sizepos, UIManager uimgr);
	void paint(Renderer r);
	//void composite(GUIRenderer r);
	
	ComponentUI getParent();
	
	void invalidateLocal();
	void validate();
	boolean getValidated();
	
	void repaintLocal();
}
