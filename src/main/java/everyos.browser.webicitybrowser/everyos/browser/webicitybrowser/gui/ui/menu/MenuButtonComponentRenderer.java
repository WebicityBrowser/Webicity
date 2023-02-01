package everyos.browser.webicitybrowser.gui.ui.menu;

import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.gui.stage.render.SolidRenderer;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public class MenuButtonComponentRenderer implements SolidRenderer {

	private final Box box;

	public MenuButtonComponentRenderer(Box box) {
		this.box = box;
	}

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		return new MenuButtonUnit(box);
	}

}
