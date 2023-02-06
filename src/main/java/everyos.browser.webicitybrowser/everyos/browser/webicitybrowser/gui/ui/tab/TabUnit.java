package everyos.browser.webicitybrowser.gui.ui.tab;

import java.util.Optional;

import everyos.browser.webicitybrowser.gui.binding.component.tab.TabComponent;
import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.composite.Compositor;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;
import everyos.desktop.thready.laf.simple.component.message.DefaultContentMessageHandler;

public class TabUnit implements Unit {

	private final Box box;
	private final TabComponent component;

	public TabUnit(Box box, TabComponent component) {
		this.box = box;
		this.component = component;
	}

	@Override
	public Optional<Compositor> createCompositor(Rectangle documentRect) {
		return Optional.empty();
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new TabPainter(box, documentRect, component);
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
