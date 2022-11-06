package everyos.desktop.thready.core.gui.laf.component.render;

import everyos.desktop.thready.core.gui.laf.component.render.unit.generator.UnitGenerator;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public interface Renderer {

	UnitGenerator render(AbsoluteSize precomputedSize);
	
}