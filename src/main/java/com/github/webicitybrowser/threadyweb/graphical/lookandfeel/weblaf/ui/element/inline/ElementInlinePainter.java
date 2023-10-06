package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.inline;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.paint.BackgroundPainter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementUnit;

public final class ElementInlinePainter {

	private ElementInlinePainter() {}

	public static void paint(ElementUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		BackgroundPainter.paintBackground(unit.styleDirectives(), globalPaintContext, localPaintContext);
	}
	
}
