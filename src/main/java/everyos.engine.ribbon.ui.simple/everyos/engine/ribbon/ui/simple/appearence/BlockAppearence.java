package everyos.engine.ribbon.ui.simple.appearence;

import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;
import everyos.engine.ribbon.core.shape.SizePosGroup;
import everyos.engine.ribbon.core.ui.UIDirective;
import everyos.engine.ribbon.core.ui.UIManager;

public class BlockAppearence implements Appearence {
	private Dimension size;

	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIManager uimgr) {
		this.size = sizepos.getSize();
	}

	@Override
	public void paint(Renderer r) {
		r.useBackground();
		r.drawFilledRect(0, 0, size.getWidth(), size.getHeight());
	}

	@Override
	public void directive(UIDirective directive) {
		
	}

	@Override
	public void processEvent(UIEvent event) {
		
	}
}
