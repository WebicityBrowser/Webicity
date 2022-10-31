package everyos.desktop.thready.core.gui.laf.component.render;

import everyos.desktop.thready.core.gui.laf.component.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public interface Renderer {

	Unit[] render(AbsoluteSize precomputedSize);
	
}