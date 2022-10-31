package everyos.desktop.thready.core.gui.laf.component.render.unit;

import java.util.Optional;

import everyos.desktop.thready.core.gui.laf.component.composite.Compositor;
import everyos.desktop.thready.core.positioning.Rectangle;

public interface Unit {

	Optional<Compositor> createCompositor();
	
	ContextSwitch[] getContextSwitches();
	
	// I don't really like this
	void postRender(Rectangle documentRect);
	
	// TODO: Event handler
	
}
