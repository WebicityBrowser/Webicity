package everyos.engine.ribbon.ui.simple.appearence;

import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.UIDirective;

public interface Appearence {
	void render(RendererData rd, SizePosGroup sizepos, RenderContext context);
	void paint(RendererData rd, PaintContext context);
	void directive(UIDirective directive);
	void processEvent(UIEvent e);
}
