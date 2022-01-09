package everyos.browser.webicity.webribbon.ui.webui.display.inner.flow.layout;

import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.stage.PaintStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.RenderStageBox;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public final class InlineLevelBoxFlowLayout {

	private InlineLevelBoxFlowLayout() {}
	
	public static void render(RenderStageBox box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		box.getContent().render(box, rd, sizepos, context);
	}

	public static void paint(PaintStageBox box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		box.getContent().paint(box, rd, viewport, context);
	}

}
