package everyos.browser.webicitybrowser.gui.ui.tab;

import everyos.browser.webicitybrowser.gui.binding.component.tab.TabComponent;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.gui.stage.render.SolidRenderer;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public class TabComponentRenderer implements SolidRenderer {

	private final Box box;
	private final TabComponent component;

	public TabComponentRenderer(Box box, TabComponent component) {
		this.box = box;
		this.component = component;
	}

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		return new TabUnit(box, component);
	}

}
