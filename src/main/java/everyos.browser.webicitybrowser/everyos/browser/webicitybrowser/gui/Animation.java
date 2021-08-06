package everyos.browser.webicitybrowser.gui;

import everyos.engine.ribbon.core.rendering.RendererData;
import everyos.engine.ribbon.core.shape.Dimension;

public interface Animation {
	RendererData step(RendererData rd, boolean visible, Dimension bounds);
}
