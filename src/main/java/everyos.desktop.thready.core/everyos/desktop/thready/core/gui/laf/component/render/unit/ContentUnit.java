package everyos.desktop.thready.core.gui.laf.component.render.unit;

import everyos.desktop.thready.core.gui.laf.component.paint.ContentUnitPainter;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public interface ContentUnit extends Unit {

	AbsoluteSize getSize();
	
	//TODO: It might be better if painters are static
	ContentUnitPainter createPainter();
	
}
