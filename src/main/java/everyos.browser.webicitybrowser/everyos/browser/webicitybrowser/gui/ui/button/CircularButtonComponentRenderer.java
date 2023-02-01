package everyos.browser.webicitybrowser.gui.ui.button;

import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.gui.stage.render.SolidRenderer;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public class CircularButtonComponentRenderer implements SolidRenderer {

	private final Box box;

	public CircularButtonComponentRenderer(Box box) {
		this.box = box;
	}

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		return new CircularButtonUnit(box);
	}

}
