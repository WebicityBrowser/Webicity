package everyos.browser.webicitybrowser.gui;

import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.core.shape.Dimension;

public interface Animation {
	Renderer step(Renderer r, boolean visible, Dimension bounds);
}
