package everyos.desktop.thready.core.gui.laf.component.render;

import everyos.desktop.thready.core.gui.laf.component.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;

public interface Renderer {

	Unit[] render(AbsoluteSize precomputedSize);
	
	void postRender(Rectangle documentRect);
	
}