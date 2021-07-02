package everyos.browser.webicity.webribbon.ui.webui.layout;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Rectangle;

public class BlockLayout implements Layout {
	private InlineBlockLayout layout;
	
	public BlockLayout(WebComponent component, WebComponentUI ui) {
		layout = new InlineBlockLayout(component, ui);
	}
	
	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIContext context, Appearence appearence) {
		if (sizepos.getCurrentPointer().getX()!=0) sizepos.nextLine();
		layout.render(r, sizepos, context, appearence);
		if (sizepos.getCurrentPointer().getX()!=0) sizepos.nextLine();
	}

	@Override
	public void paint(Renderer r, Rectangle viewport, Appearence appearence) {
		layout.paint(r, viewport, appearence);
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
