package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.webicitybrowser.gui.Styling;

public class URLBarUnit implements Unit {

	private final Box box;
	private final ComponentUI componentUI;
	private final Font2D font;
	private final TextFieldViewModel textFieldViewModel;

	public URLBarUnit(Box box, ComponentUI componentUI, Font2D font, TextFieldViewModel textFieldViewModel) {
		this.box = box;
		this.componentUI = componentUI;
		this.font = font;
		this.textFieldViewModel = textFieldViewModel;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		Rectangle contentRect = getContentRect(documentRect);
		return new URLBarPainter(box, documentRect, contentRect, componentUI, textFieldViewModel, font);
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return AbsoluteSize.ZERO_SIZE;
	}

	@Override
	public MessageHandler getMessageHandler(Rectangle documentRect) {
		Rectangle contentRect = getContentRect(documentRect);
		return new URLBarMessageHandler(documentRect, contentRect, box, textFieldViewModel, font);
	}
	
	private Rectangle getContentRect(Rectangle documentRect) {
		float xOffset = Styling.BUTTON_WIDTH / 2;
		
		AbsolutePosition originalPosition = documentRect.position();
		AbsolutePosition adjustedPosition = new AbsolutePosition(originalPosition.x() + xOffset, originalPosition.y());
		
		AbsoluteSize originalSize = documentRect.size();
		AbsoluteSize adjustedSize = new AbsoluteSize(
			Math.max(0, originalSize.width() - xOffset * 2),
			originalSize.height());
		
		return new Rectangle(adjustedPosition, adjustedSize);
	}

}
