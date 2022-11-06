package everyos.desktop.thready.core.gui.laf.component.render.unit.generator;

import everyos.desktop.thready.core.gui.laf.component.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public interface MultiContentUnitGenerator {
	
	AbsoluteSize getBaseSize();
	
	Unit getUnits(int offset, int length);
	
	int getNumberOfUnits();
	
}
