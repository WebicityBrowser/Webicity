package everyos.browser.webicity.webribbon.gui.box;

import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Position;
import everyos.engine.ribbon.ui.simple.shape.SizePosGroup;

public interface InlineLevelBox extends Box {
	
	/**
	 * Attempt to split this box to fit in a given width
	 * @param rd The renderer data if the current renderer.
	 * @param width The amount of width that the component should fit in before being split
	 * @param context The current web render context
	 * @param first Whether this box is the first box being displayed on the containing line
	 * @return An array whose first element is the box that fits on the current line, and whose
	 *     second element is a box to be split over future lines. The first element should have
	 *     a position of (0, 0), behave as if it had been rendered, and have the same size as
	 *     would be the final size of a sizeposgroup that would be passed to it had it been rendered,
	 *     created by arguments (0, 0, width, 0, width, -1).
	 */
	InlineLevelBox[] split(RendererData rd, int width, WebRenderContext context, boolean first);

	static void autoRenderSplit(InlineLevelBox box, RendererData rd, WebRenderContext context) {
		SizePosGroup spg = new SizePosGroup(0, 0, 0, 0, -1, -1);
		box.render(rd, spg, context);
		box.setFinalPos(new Position(0, 0));
		box.setFinalSize(spg.getSize());
	}
	
}
