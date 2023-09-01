package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.paint.BackgroundPainter;

public final class ElementPainter {
	
	private ElementPainter() {}
	
	public static void paint(ElementUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		BackgroundPainter.paintBackground(unit.styleDirectives(), globalPaintContext, localPaintContext);
		ElementChildrenPainter.paintChildren(unit, globalPaintContext, localPaintContext);
	}

}
