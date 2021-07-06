package everyos.engine.ribbon.ui.simple.layout;

import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;

public interface Layout {
	void render(Renderer r, SizePosGroup sizepos, UIManager uimgr, Appearence appearence);
	void paint(Renderer r, Appearence appearence);
	void processEvent(UIEvent event);
	void directive(UIDirective directive);
}
