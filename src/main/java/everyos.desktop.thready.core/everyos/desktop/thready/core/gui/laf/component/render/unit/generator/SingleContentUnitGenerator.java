package everyos.desktop.thready.core.gui.laf.component.render.unit.generator;

import everyos.desktop.thready.core.gui.laf.component.render.unit.ContentUnit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public interface SingleContentUnitGenerator {

	AbsoluteSize getUnitSize();
	
	ContentUnit getUnit();
	
}
