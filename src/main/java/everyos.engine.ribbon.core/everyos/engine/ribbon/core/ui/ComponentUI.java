package everyos.engine.ribbon.core.ui;

import everyos.engine.ribbon.core.accessibility.alt.AltTarget;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.graphics.Component;
import everyos.engine.ribbon.core.graphics.InvalidationLevel;
import everyos.engine.ribbon.core.graphics.PaintContext;
import everyos.engine.ribbon.core.graphics.RenderContext;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public interface ComponentUI extends AltTarget {
	
	//TODO: Also pass a stack
	void render(RendererData rd, SizePosGroup sizepos, RenderContext context);
	void paint(RendererData rd, PaintContext context);
	//void composite(RendererData rd);
	
	void directive(UIDirective directive);
	ComponentUI getParent();
	Component getComponent();
	
	void invalidate(InvalidationLevel level);
	boolean getValidated(InvalidationLevel reference);
	void validateTo(InvalidationLevel level);
	void invalidateLocal(InvalidationLevel level);
	
	void processEvent(UIEvent ev);
	
}
