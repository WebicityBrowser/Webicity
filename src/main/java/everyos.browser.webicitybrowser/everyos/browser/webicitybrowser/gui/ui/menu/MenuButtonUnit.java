package everyos.browser.webicitybrowser.gui.ui.menu;

import java.util.Optional;

import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.composite.Compositor;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;
import everyos.desktop.thready.laf.simple.component.message.DefaultMessageHandler;

public class MenuButtonUnit implements Unit {

	private final Box box;

	public MenuButtonUnit(Box box) {
		this.box = box;
	}

	@Override
	public Optional<Compositor> createCompositor(Rectangle documentRect) {
		return Optional.empty();
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new MenuButtonPainter(box, documentRect);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new DefaultMessageHandler();
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return new AbsoluteSizeImp(22, 22);
	}

}
