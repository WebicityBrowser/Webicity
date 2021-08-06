package everyos.browser.webicity.webribbon.ui.webui.layout;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;

public class BlockLayout implements Layout {
	private InlineBlockLayout layout;
	
	public BlockLayout(WebComponent component, WebComponentUI ui) {
		layout = new InlineBlockLayout(component, ui);
	}
	
	@Override
	public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context, Appearence appearence) {
		if (sizepos.getCurrentPointer().getX()!=0) sizepos.nextLine();
		layout.render(rd, sizepos, context, appearence);
		if (sizepos.getCurrentPointer().getX()!=0) sizepos.nextLine();
	}

	@Override
	public void paint(RendererData rd, Rectangle viewport, WebPaintContext context, Appearence appearence) {
		layout.paint(rd, viewport, context, appearence);
	}

	@Override
	public UIBox getComputedUIBox() {
		return layout.getComputedUIBox();
	}

	@Override
	public void processEvent(UIEvent event) {
		//TODO
		layout.processEvent(event);
	}
}
