package everyos.engine.ribbon.core.ui;

import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public interface ComponentUI {
	//TODO: Also pass a stack
	void render(Renderer r, SizePosGroup sizepos, UIManager uimgr);
	void paint(Renderer r);
	//void composite(GUIRenderer r);
	
	void directive(UIDirective directive);
	ComponentUI getParent();
	Component getComponent();
	
	void invalidate(InvalidationLevel level);
	boolean getValidated(InvalidationLevel reference);
	void validateTo(InvalidationLevel level);
	void invalidateLocal(InvalidationLevel level);
	
	void processEvent(UIEvent ev);
}
