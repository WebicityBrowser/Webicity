package everyos.desktop.thready.core.gui.laf.component.render.unit.generator;

import everyos.desktop.thready.core.gui.laf.component.render.unit.Unit;
import everyos.desktop.thready.core.gui.laf.component.render.unit.WrappingUnit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public interface WrappingUnitGenerator {

	AbsoluteSize getBaselineSizeOffset();
	
	AbsoluteSize getSizeOffsetPerUnit();
	
	WrappingUnit generateUnit();
	
	void addChildToCurrentUnit(Unit child);
	
}
