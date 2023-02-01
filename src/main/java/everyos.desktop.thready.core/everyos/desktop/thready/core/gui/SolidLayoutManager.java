package everyos.desktop.thready.core.gui;

import everyos.desktop.thready.core.gui.stage.box.SolidBox;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public interface SolidLayoutManager {

	Unit render(RenderContext renderContext, AbsoluteSize precomputedSize, SolidBox[] children);
	
}
