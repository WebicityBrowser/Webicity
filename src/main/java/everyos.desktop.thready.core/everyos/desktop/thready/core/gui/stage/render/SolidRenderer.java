package everyos.desktop.thready.core.gui.stage.render;

import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public interface SolidRenderer {

	Unit render(RenderContext renderContext, AbsoluteSize precomputedSize);
	
}
