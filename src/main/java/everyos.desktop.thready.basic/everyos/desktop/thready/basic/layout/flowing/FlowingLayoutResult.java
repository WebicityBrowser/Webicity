package everyos.desktop.thready.basic.layout.flowing;

import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.Rectangle;

public record FlowingLayoutResult(Rectangle renderedRect, Unit unit, int priority) {
	
}
