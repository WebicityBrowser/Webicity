package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;

public interface Unit {
	
	AbsoluteSize getMinimumSize();

	Painter getPainter(Rectangle documentRect);

	default MessageHandler getMessageHandler(Rectangle documentRect) {
		throw new UnsupportedOperationException();
	};
	
}
