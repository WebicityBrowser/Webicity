package everyos.browser.webicity.webribbon.ui.webui.display.inner.flow;

import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.gui.box.stage.PaintStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.RenderStageBox;
import everyos.browser.webicity.webribbon.ui.webui.display.inner.flow.layout.FlowLayout;
import everyos.browser.webicity.webribbon.ui.webui.helper.BoxUtil;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public class FlowBlockContext implements FlowContext {

	@Override
	public void render(RenderStageBox box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		for (RenderStageBox child: box.getChildren()) {
			prepareNextLine(sizepos);
			
			//TODO: Handle full box model
			child.setPosition(sizepos.getCurrentPointer());
			
			SizePosGroup childSize = new SizePosGroup(
				sizepos.getSize().getWidth(), 0,
				0, 0,
				sizepos.getSize().getWidth(), -1);
			FlowLayout.render(child, rd, childSize, context);
			
			sizepos.move(child.getContentSize().getWidth(), true);
			sizepos.setMinLineHeight(child.getContentSize().getHeight());
			sizepos.nextLine();
		}
	}
	
	@Override
	public void paint(PaintStageBox box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		for (PaintStageBox child: box.getChildren()) {
			if (createRectFor(child).intersects(viewport)) {
				RendererData rd2 = rd.getSubcontext(
					child.getPosition().getX(), child.getPosition().getY(),
					child.getContentSize().getWidth(), child.getContentSize().getHeight());
				FlowLayout.paint(child, rd2, viewport, context);
			}
		}
	}
	
	@Override
	public MultiBox[] split(MultiBox box, RendererData rd, int width, WebRenderContext context) {
		return BoxUtil.keepWholeSplitWithContent(box, this);
	}

	private void prepareNextLine(SizePosGroup sizepos) {
		if (sizepos.getCurrentPointer().getX() != 0) {
			sizepos.nextLine();
		}
	}

	private Rectangle createRectFor(PaintStageBox box) {
		return new Rectangle(
			box.getPosition(),
			box.getContentSize());
	}
	
}
