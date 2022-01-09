package everyos.browser.webicity.webribbon.gui;

import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.gui.box.stage.PaintStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.RenderStageBox;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public interface Content {
	
	void render(RenderStageBox box, RendererData rd, SizePosGroup sizepos, WebRenderContext context);
	void paint(PaintStageBox box, RendererData rd, Rectangle viewport, WebPaintContext context);
	//void composite(RendererData rd);
	
	//TODO: Allow splitting heightwise?
	/**
	 * Split a box with the rules of this content.
	 * This method should only be called if the content MUST be split.
	 * @param box The box that this content belongs to
	 * @param rd The current renderer data
	 * @param width The width that this content should fit into after the split, if possible
	 * @param context The current render context
	 * @return A box split via the rules of this content
	 */
	MultiBox[] split(MultiBox box, RendererData rd, int width, WebRenderContext context);
	
}
