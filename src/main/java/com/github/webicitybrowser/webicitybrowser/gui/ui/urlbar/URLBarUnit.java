package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public class URLBarUnit implements Unit {

	private final Box box;
	private final Font2D font;
	private final TextFieldViewModel textFieldViewModel;

	public URLBarUnit(Box box, Font2D font, TextFieldViewModel textFieldViewModel) {
		this.box = box;
		this.font = font;
		this.textFieldViewModel = textFieldViewModel;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new URLBarPainter(box, documentRect, textFieldViewModel, font);
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return AbsoluteSize.ZERO_SIZE;
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		return new URLBarMessageHandler(documentRect, box, textFieldViewModel, font);
	}

}
