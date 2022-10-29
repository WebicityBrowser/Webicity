package everyos.desktop.thready.core.gui.laf.component.render.unit;

import java.util.Optional;

import everyos.desktop.thready.core.gui.laf.component.composite.Compositor;

public interface Unit {

	Optional<Compositor> getCompositor();
	
	ContextSwitch[] getContextSwitches();
	
}
