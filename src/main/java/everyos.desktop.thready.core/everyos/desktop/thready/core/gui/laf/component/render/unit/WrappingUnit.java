package everyos.desktop.thready.core.gui.laf.component.render.unit;

import everyos.desktop.thready.core.gui.laf.component.paint.WrappingUnitPainter;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public interface WrappingUnit extends Unit {
	
	AbsoluteSize getSizeOffset();

	Unit[] getChildren();
	
	WrappingUnitPainter createPainter();
	
}
