package everyos.desktop.thready.core.gui.laf.component.paint;

import everyos.desktop.thready.core.graphics.canvas.Canvas2D;
import everyos.desktop.thready.core.gui.laf.component.render.unit.Unit;

public interface WrappingUnitPainter {

	void paint(PaintContext context, Canvas2D canvas, Unit nextUnit);
	
}
