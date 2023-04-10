package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text.stage.render;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text.stage.paint.TextPainter;

public class TextUnit implements Unit {

	private final Box box;
	private final String text;
	private final Font2D font;
	private final AbsoluteSize size;

	public TextUnit(Box box, String text, Font2D font, AbsoluteSize size) {
		this.box = box;
		this.text = text;
		this.font = font;
		this.size = size;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new TextPainter(box, documentRect, text, font);
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return this.size;
	}

}
