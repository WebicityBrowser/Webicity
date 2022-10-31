package everyos.desktop.thready.core.gui.laf.component.render.unit;

import everyos.desktop.thready.core.gui.laf.component.paint.ContentUnitPainter;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public interface ContentUnit extends Unit {

	AbsoluteSize getMinimumSize();
	
	ContentUnitPainter createPainter();
	
}
