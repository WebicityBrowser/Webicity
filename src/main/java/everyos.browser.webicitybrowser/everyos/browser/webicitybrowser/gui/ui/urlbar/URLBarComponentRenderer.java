package everyos.browser.webicitybrowser.gui.ui.urlbar;

import everyos.browser.webicitybrowser.gui.behavior.URLBarComponent;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.gui.stage.render.SolidRenderer;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public class URLBarComponentRenderer implements SolidRenderer {

	private final Box box;
	private final URLBarComponent component;

	public URLBarComponentRenderer(Box box, URLBarComponent component) {
		this.box = box;
		this.component = component;
	}

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		return new URLBarUnit(box, component);
	}

}
