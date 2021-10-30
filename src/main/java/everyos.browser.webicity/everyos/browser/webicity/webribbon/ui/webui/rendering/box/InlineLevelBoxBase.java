package everyos.browser.webicity.webribbon.ui.webui.rendering.box;

import everyos.browser.webicity.webribbon.gui.WebRenderContext;
import everyos.browser.webicity.webribbon.gui.box.Box;
import everyos.browser.webicity.webribbon.gui.box.InlineLevelBox;
import everyos.engine.ribbon.core.rendering.RendererData;

public abstract class InlineLevelBoxBase extends BoxBase implements InlineLevelBox {
	
	public InlineLevelBox[] split(RendererData rd, int width, WebRenderContext context, boolean first) {
		
		InlineLevelBox.autoRenderSplit(this, rd, context);
		
		if (first) {
			return new InlineLevelBox[] { this, null };
		}
		
		if (getFinalSize().getWidth() <= width) {
			return new InlineLevelBox[] { this, null };
		} else {
			return new InlineLevelBox[] { null, new CachedRenderBox(this) };
		}
		
	}

	@Override
	public Box[] getChildren() {
		return new Box[0];
	}
	
}
