package everyos.browser.webicity.webribbon.ui.webui.display.inner.flow;

import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.BlockLevelBox;
import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class FlowBlockContext implements FlowContext {

	@Override
	public void render(Box box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		for (Box rawChild: box.getChildren()) {
			prepareNextLine(sizepos);
			
			BlockLevelBox child = (BlockLevelBox) rawChild;
			
			//TODO: Handle width/height CSS?
			//TODO: Handle full box model
			child.setFinalPos(sizepos.getCurrentPointer());
			
			SizePosGroup childSize = new SizePosGroup(
				sizepos.getSize().getWidth(), 0,
				0, 0,
				sizepos.getSize().getWidth(), -1);
			child.render(rd, childSize, context);
			
			child.setFinalSize(childSize.getSize());
			
			sizepos.setMinLineHeight(childSize.getSize().getHeight());
			sizepos.nextLine();
		}
	}
	
	@Override
	public void paint(Box box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		//TODO: Will we always be a BlockLevelBox, though?
		for (Box rawChild: box.getChildren()) {
			BlockLevelBox child = (BlockLevelBox) rawChild;
			if (child.getPaintCullingFilter().intersectsWith(viewport)) {
				RendererData rd2 = rd.getSubcontext(
					child.getFinalPos().getX(), child.getFinalPos().getY(),
					child.getFinalSize().getWidth(), child.getFinalSize().getHeight());
				child.paint(rd2, viewport, context);
			}
		}
	}
	
	private void prepareNextLine(SizePosGroup sizepos) {
		if (sizepos.getCurrentPointer().getX() != 0) {
			sizepos.nextLine();
		}
	}

}
