package everyos.engine.ribbon.core.ui;

import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public interface ComponentUI {
	void directive(UIDirective directive);
	
	void invalidate(InvalidationLevel level);
	boolean getValidated(InvalidationLevel reference);
	void validateTo(InvalidationLevel level);
	void invalidateLocal(InvalidationLevel level);
	
	//TODO: Also pass a stack
	void render(Renderer r, SizePosGroup sizepos, UIManager uimgr);
	void paint(Renderer r);
	//void composite(GUIRenderer r);
	
	ComponentUI getParent();
}
