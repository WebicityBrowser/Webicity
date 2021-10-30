package everyos.browser.webicity.webribbon.ui.webui.rendering.box;

import everyos.browser.webicity.webribbon.gui.WebPaintContext;
import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.browser.webicity.webribbon.gui.box.CullingFilter;
import everyos.browser.webicity.webribbon.gui.box.InlineLevelBox;
import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Rectangle;
import everyos.engine.ribbon.core.shape.SizePosGroup;

public class CachedRenderBox extends InlineLevelBoxBase {

	private InlineLevelBox box;

	public CachedRenderBox(InlineLevelBox box) {
		this.box = box;
	}

	@Override
	public void render(RendererData rd, SizePosGroup sizepos, WebRenderContext context) {
		// Should not actually be called
	}

	@Override
	public void paint(RendererData rd, Rectangle viewport, WebPaintContext context) {
		// Should not actually be called
	}
	
	public InlineLevelBox[] split(RendererData rd, int width, WebRenderContext context, boolean first) {
		
		if (first) {
			return new InlineLevelBox[] { box, null };
		}
		
		if (box.getFinalSize().getWidth() < width) {
			return new InlineLevelBox[] { box, null };
		} else {
			return new InlineLevelBox[] { null, this };
		}
		
	}

	@Override
	public CullingFilter getPaintCullingFilter() {
		return vp -> true;
	}
	
	protected Box getBox() {
		return this.box;
	}

}
