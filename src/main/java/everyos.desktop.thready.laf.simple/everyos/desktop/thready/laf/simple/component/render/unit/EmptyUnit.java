package everyos.desktop.thready.laf.simple.component.render.unit;

import java.util.Optional;

import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.stage.composite.Compositor;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;
import everyos.desktop.thready.laf.simple.component.message.EmptyMessageHandler;
import everyos.desktop.thready.laf.simple.component.paint.EmptyPainter;

public class EmptyUnit implements Unit {

	@Override
	public Optional<Compositor> createCompositor(Rectangle documentRect) {
		return Optional.empty();
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new EmptyPainter();
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new EmptyMessageHandler();
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return AbsoluteSizeImp.ZERO_SIZE;
	}

}
