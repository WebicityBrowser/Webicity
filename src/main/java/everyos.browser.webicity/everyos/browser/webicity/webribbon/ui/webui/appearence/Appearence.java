package everyos.browser.webicity.webribbon.ui.webui.appearence;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public interface Appearence {
	void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context);
	void paint(RendererData rd, Rectangle viewport, WebPaintContext context);
	void recalculatePaintCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap properties, Appearence appearence);
}
