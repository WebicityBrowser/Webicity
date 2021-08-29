package everyos.browser.webicitybrowser.gui.animation;

import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;

public interface Animation {
	RendererData step(RendererData rd, boolean visible, Dimension bounds);
}
