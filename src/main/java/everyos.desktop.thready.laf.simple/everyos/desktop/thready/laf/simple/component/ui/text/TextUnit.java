package everyos.desktop.thready.laf.simple.component.ui.text;

import java.util.Optional;

import everyos.desktop.thready.core.graphics.text.LoadedFont;
import everyos.desktop.thready.core.gui.message.MessageHandler;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.composite.Compositor;
import everyos.desktop.thready.core.gui.stage.paint.Painter;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.laf.simple.component.message.DefaultContentMessageHandler;

public class TextUnit implements Unit {

	private final Box box;
	private final String text;
	private final LoadedFont font;
	private final AbsoluteSize size;

	public TextUnit(Box box, String text, LoadedFont font, AbsoluteSize size) {
		this.box = box;
		this.text = text;
		this.font = font;
		this.size = size;
	}

	@Override
	public Optional<Compositor> createCompositor(Rectangle documentRect) {
		return Optional.empty();
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new TextPainter(box, documentRect, text, font);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new DefaultContentMessageHandler(documentRect, box);
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return this.size;
	}

}
