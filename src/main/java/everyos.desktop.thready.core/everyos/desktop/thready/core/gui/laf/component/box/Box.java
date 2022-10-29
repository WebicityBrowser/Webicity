package everyos.desktop.thready.core.gui.laf.component.box;

import everyos.desktop.thready.core.gui.laf.component.render.Renderer;

public interface Box {
	
	void addChild(Box child);
	
	Box[] getIntegratedBoxes();

	Renderer getRenderer();
	
}
