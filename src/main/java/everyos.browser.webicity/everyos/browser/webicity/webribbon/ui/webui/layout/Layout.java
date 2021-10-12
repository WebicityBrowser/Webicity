package everyos.browser.webicity.webribbon.ui.webui.layout;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.gui.UIBox;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.ui.webui.appearence.Appearence;
import everyos.engine.ribbon.core.event.UIEvent;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public interface Layout {
	void recalculatePaintCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap properties, Appearence appearence);
	void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context, Appearence appearence);
	void paint(RendererData rd, Rectangle viewport, WebPaintContext context, Appearence appearence);
	UIBox getComputedUIBox();
	void processEvent(UIEvent event);
}
