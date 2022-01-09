package everyos.browser.webicity.webribbon.ui.webui.display.inner.flow.layout;

import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.layout.BlockLevelBox;
import everyos.browser.webicity.webribbon.gui.box.layout.InlineLevelBox;
import everyos.browser.webicity.webribbon.gui.box.stage.PaintStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.RenderStageBox;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

//TODO: I don't know what a good name for this class (or the containing package) would be
public final class FlowLayout {

	private FlowLayout() {}

	public static void render(RenderStageBox box, RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		//TODO: What if we have some mysterious 3rd type of box?
		// This was originally a method inside the Box itself, but I wanted to make boxes simpler
		if (box instanceof InlineLevelBox) {
			InlineLevelBoxFlowLayout.render(box, rd, sizepos, context);
		} else if (box instanceof BlockLevelBox) {
			BlockLevelBoxFlowLayout.render(box, rd, sizepos, context);
		}
	}

	public static void paint(PaintStageBox box, RendererData rd, Rectangle viewport, WebPaintContext context) {
		if (box instanceof InlineLevelBox) {
			InlineLevelBoxFlowLayout.paint(box, rd, viewport, context);
		} else if (box instanceof BlockLevelBox) {
			BlockLevelBoxFlowLayout.paint(box, rd, viewport, context);
		}
	}
	
}
