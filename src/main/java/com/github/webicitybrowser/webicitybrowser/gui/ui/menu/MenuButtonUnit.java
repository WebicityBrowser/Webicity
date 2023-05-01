package com.github.webicitybrowser.webicitybrowser.gui.ui.menu;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.message.SimpleDefaultMessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public class MenuButtonUnit implements Unit {

	private final Box box;
	private final Font2D font;

	public MenuButtonUnit(Box box, Font2D font) {
		this.box = box;
		this.font = font;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new MenuButtonPainter(box, documentRect, font);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new SimpleDefaultMessageHandler(documentRect, box);
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return new AbsoluteSize(22, 22);
	}

}
