package everyos.desktop.thready.core.gui.stage.render.unit;

import java.util.Optional;

import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.stage.composite.Compositor;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;

public interface Unit {

	Optional<Compositor> createCompositor(Rectangle documentRect);
	
	Painter getPainter(Rectangle documentRect);
	
	MessageHandler getMessageHandler(Rectangle documentRect);
	
	AbsoluteSize getMinimumSize();
	
}
