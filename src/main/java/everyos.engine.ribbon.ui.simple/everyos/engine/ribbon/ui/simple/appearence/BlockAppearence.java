package everyos.engine.ribbon.ui.simple.appearence;

import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.UIDirective;

public class BlockAppearence implements Appearence {
	private Dimension size;

	@Override
	public void render(RendererData rd, SizePosGroup sizepos, RenderContext context) {
		this.size = sizepos.getSize();
	}

	@Override
	public void paint(RendererData rd, PaintContext context) {
		rd.useBackground();
		context.getRenderer().drawFilledRect(rd, 0, 0, size.getWidth(), size.getHeight());
	}

	@Override
	public void directive(UIDirective directive) {
		
	}

	@Override
	public void processEvent(UIEvent event) {
		
	}
}
