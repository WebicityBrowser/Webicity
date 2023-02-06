package everyos.browser.webicitybrowser.gui.ui.urlbar;

import java.util.Optional;

import everyos.browser.webicitybrowser.gui.behavior.URLBarComponent;
import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.composite.Compositor;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;
import everyos.desktop.thready.laf.simple.component.message.DefaultContentMessageHandler;

public class URLBarUnit implements Unit {

	private final Box box;
	private final URLBarComponent component;

	public URLBarUnit(Box box, URLBarComponent component) {
		this.box = box;
		this.component = component;
	}

	@Override
	public Optional<Compositor> createCompositor(Rectangle documentRect) {
		return Optional.empty();
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new URLBarPainter(box, documentRect, component);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new DefaultContentMessageHandler(documentRect, box);
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return AbsoluteSizeImp.ZERO_SIZE;
	}

}
