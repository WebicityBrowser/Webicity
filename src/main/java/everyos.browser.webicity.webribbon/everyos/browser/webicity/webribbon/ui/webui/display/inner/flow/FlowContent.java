package everyos.browser.webicity.webribbon.ui.webui.display.inner.flow;

import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.layout.BlockLevelBox;
import everyos.browser.webicity.webribbon.gui.box.layout.InlineLevelBox;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.gui.box.stage.PaintStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.RenderStageBox;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public class FlowContent implements Content {
	
	private FlowContext context;
	
	@Override
	public void render(RenderStageBox box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
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
	public void paint(PaintStageBox box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		if (this.context == null) {
			return;
		}
		
		this.context.paint(box, rd, viewport, context);
	}

	@Override
	public MultiBox[] split(MultiBox box, RendererData rd, int width, WebRenderContext context) {
		calculateContext(box);
		if (this.context == null) {
			//TODO: Ensure box has correct content
			return new MultiBox[] { box, null };
		}
		
		return this.context.split(box, rd, width, context);
	}
	
	private void calculateContext(RenderStageBox box) {
		RenderStageBox[] children = box.getChildren();
		if (children.length == 0) {
			return;
		} else if (children[0] instanceof BlockLevelBox) {
			this.context = new FlowBlockContext();
		} else if (children[0] instanceof InlineLevelBox) {
			this.context = new FlowInlineContext();
		}
	}
		
}
