package everyos.browser.webicity.webribbon.ui.webui.display.inner.none;

import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.gui.box.stage.PaintStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.RenderStageBox;
import everyos.browser.webicity.webribbon.ui.webui.helper.BoxUtil;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public class EmptyContent implements Content {
	
	@Override
	public void render(RenderStageBox box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		
	}

	@Override
	public void paint(PaintStageBox box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		
	}
	
	@Override
	public MultiBox[] split(MultiBox box, RendererData rd, int width, WebRenderContext context) {
		return BoxUtil.keepWholeSplitWithContent(box, box.getContent());
	};

}
