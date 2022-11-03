package everyos.desktop.thready.core.gui.laf.component.render.unit;

import java.util.Optional;

import everyos.desktop.thready.core.gui.laf.component.composite.Compositor;
import everyos.desktop.thready.core.gui.laf.component.event.EventHandler;
import everyos.desktop.thready.core.gui.laf.component.paint.Painter;
import everyos.desktop.thready.core.positioning.Rectangle;

public interface Unit {

	Optional<Compositor> createCompositor();
	
	Painter getPainter();
	
	EventHandler getEventHandler();
	
	ContextSwitch[] getContextSwitches();
	
	// I don't really like this
	void postRender(Rectangle documentRect);
	
}
