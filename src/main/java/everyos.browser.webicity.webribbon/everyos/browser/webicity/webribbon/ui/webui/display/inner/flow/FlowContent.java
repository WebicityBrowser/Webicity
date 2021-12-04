package everyos.browser.webicity.webribbon.ui.webui.display.inner.flow;

import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.BlockLevelBox;
import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.browser.webicity.webribbon.gui.box.InlineLevelBox;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public class FlowContent implements Content {
	
	private FlowContext context;
	
	@Override
	public void render(Box box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		//TODO: Handle float
		//TODO: Cropping, also correct sizepos
		//TODO: Also handle FlexContent and stuff
		
		calculateContext(box);
		if (this.context == null) {
			return;
		}
		
		this.context.render(box, rd, sizepos, context);
	}

	@Override
	public void paint(Box box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		if (this.context == null) {
			return;
		}
		
		this.context.paint(box, rd, viewport, context);
	}

	@Override
	public Content split() {
		FlowContent content = new FlowContent();
		
		return content;
	}
	
	private void calculateContext(Box box) {
		Box[] children = box.getChildren();
		if (children.length == 0) {
			return;
		} else if (children[0] instanceof BlockLevelBox) {
			this.context = new FlowBlockContext();
		} else if (children[0] instanceof InlineLevelBox) {
			this.context = new FlowInlineContext();
		}
	}
		
}
