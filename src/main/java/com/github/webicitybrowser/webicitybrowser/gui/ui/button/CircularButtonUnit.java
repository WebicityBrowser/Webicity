package com.github.webicitybrowser.webicitybrowser.gui.ui.button;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.DefaultGraphicalMessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public class CircularButtonUnit implements Unit {

	private final Box box;
	private final Image image;

	public CircularButtonUnit(Box box, Image image) {
		this.box = box;
		this.image = image;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new CircularButtonPainter(box, documentRect, image);
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new DefaultGraphicalMessageHandler(documentRect, box);
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return new AbsoluteSize(22, 22);
	}

}
