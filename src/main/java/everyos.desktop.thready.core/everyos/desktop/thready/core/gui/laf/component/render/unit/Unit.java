package everyos.desktop.thready.core.gui.laf.component.render.unit;

import java.util.Optional;

import everyos.desktop.thready.core.gui.laf.component.composite.Compositor;
import everyos.desktop.thready.core.gui.laf.component.paint.Painter;
import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.positioning.Rectangle;

public interface Unit {

	Optional<Compositor> createCompositor();
	
	Painter getPainter();
	
	MessageHandler getMessageHandler();
	
	ContextSwitch[] getContextSwitches();
	
	// I don't really like this
	void postRender(Rectangle documentRect);
	
}
