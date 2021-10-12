package everyos.browser.webicity.webribbon.ui.webui.appearence;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssom.property.backgroundcolor.BackgroundColorProperty;
import everyos.browser.spec.jcss.cssom.property.color.ColorProperty;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.engine.ribbon.core.graphics.Color;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class DefaultAppearence implements Appearence {
	private Color color;
	private Color bgcolor;

	@Override
	public void recalculatePaintCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap properties, Appearence appearence) {
		this.color = ((ColorProperty) properties.getPropertyByName(PropertyName.COLOR)).getComputedColor();
		this.bgcolor = ((BackgroundColorProperty) properties.getPropertyByName(PropertyName.BACKGROUND_COLOR)).getComputedColor();
	}
	
	@Override
	public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		
	}
	
	@Override
	public void paint(RendererData rd, Rectangle viewport, WebPaintContext context) {
		rd.getState().setForeground(color);
		rd.getState().setBackground(bgcolor);
	}
}
