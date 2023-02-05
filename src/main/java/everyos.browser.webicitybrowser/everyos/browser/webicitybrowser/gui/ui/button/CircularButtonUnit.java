package everyos.browser.webicitybrowser.gui.ui.button;

import java.util.Optional;

import everyos.desktop.thready.core.graphics.image.LoadedImage;
import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.composite.Compositor;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;
import everyos.desktop.thready.laf.simple.component.message.DefaultContentMessageHandler;

public class CircularButtonUnit implements Unit {

	private final Box box;
	private final LoadedImage image;

	public CircularButtonUnit(Box box, LoadedImage image) {
		this.box = box;
		this.image = image;
	}

	@Override
	public Optional<Compositor> createCompositor(Rectangle documentRect) {
		return Optional.empty();
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new CircularButtonPainter(box, documentRect, image);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new DefaultContentMessageHandler(documentRect, box);
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return new AbsoluteSizeImp(22, 22);
	}

}
