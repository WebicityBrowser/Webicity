package everyos.browser.webicity.webribbon.ui.webui.ui.content;

import everyos.browser.spec.jcss.cssom.property.PropertyName;
import everyos.browser.spec.jcss.cssom.property.backgroundcolor.BackgroundColorProperty;
import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.engine.ribbon.core.graphics.paintfill.Color;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class BlockBoxContent implements Content {

	private final Content innerContent;
	
	private Color backgroundColor;

	public BlockBoxContent(Content innerContent) {
		this.innerContent = innerContent;
	}

	@Override
	public void render(Box box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		innerContent.render(box, rd, sizepos, context);
	}

	@Override
	public void paint(Box box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		this.backgroundColor = ((BackgroundColorProperty) box.getProperties().getPropertyByName(PropertyName.BACKGROUND_COLOR)).getComputedColor();
		
		rd.getState().setBackground(backgroundColor);
		rd.useBackground();
		context.getRenderer().drawFilledRect(rd,
			0, 0, 
			box.getFinalSize().getWidth(), box.getFinalSize().getHeight());
		
		innerContent.paint(box, rd, viewport, context);
	}

	@Override
	public Content split() {
		BlockBoxContent content = new BlockBoxContent(innerContent.split());
		return content;
	}

}
