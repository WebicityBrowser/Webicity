package everyos.engine.ribbon.ui.simple.appearence;

import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;

public interface Appearence {
	void render(Renderer r, SizePosGroup sizepos, UIManager uimgr);
	void paint(Renderer r);
	void directive(UIDirective directive);
	void processEvent(UIEvent e);
}
