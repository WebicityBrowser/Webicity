package everyos.desktop.thready.core.gui.stage.render;

import everyos.desktop.thready.core.graphics.ResourceGenerator;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public interface RenderContext {
	
	AbsoluteSize getViewportSize();

	ResourceGenerator getResourceGenerator();
	
}
