package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.DefaultGraphicalMessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabComponent;

public class TabUnit implements Unit {

	private final Box box;
	private final TabComponent component;
	private final Font2D font;

	public TabUnit(Box box, TabComponent component, Font2D font) {
		this.box = box;
		this.component = component;
		this.font = font;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new TabPainter(box, documentRect, component, font);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new DefaultGraphicalMessageHandler(documentRect, box);
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return AbsoluteSize.ZERO_SIZE;
	}

}
