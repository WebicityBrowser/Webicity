package everyos.browser.webicity.webribbon.ui.webui.layout;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class BlockLayout implements Layout {
	private InlineBlockLayout layout;
	
	public BlockLayout(WebComponent component, WebComponentUI ui) {
		layout = new InlineBlockLayout(component, ui);
	}
	
	@Override
	public void recalculatePaintCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap properties, Appearence appearence) {
		layout.recalculatePaintCSSOM(cssomNode, properties, appearence);
	}
	
	@Override
	public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context, Appearence appearence) {
		ensureLineDedicated(sizepos);
		layout.render(rd, sizepos, context, appearence);
		ensureLineDedicated(sizepos);
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
	
	private void ensureLineDedicated(SizePosGroup sizepos) {
		if (sizepos.getCurrentPointer().getX()!=0) {
			sizepos.nextLine();
		}
	}
}
