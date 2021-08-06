package everyos.engine.ribbon.ui.simple.layout;

import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.ui.simple.appearence.Appearence;

public interface Layout {
	void render(RendererData rd, SizePosGroup sizepos, RenderContext context, Appearence appearence);
	void paint(RendererData rd, PaintContext context, Appearence appearence);
	void processEvent(UIEvent event);
	void directive(UIDirective directive);
}
