package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.inline;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.paint.BackgroundPainter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementChildrenPainter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.ElementUnit;

public final class ElementInlinePainter {

	private ElementInlinePainter() {}

	public static void paint(BuildableRenderedUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		BackgroundPainter.paintBackground(unit.styleDirectives(), globalPaintContext, localPaintContext);
		ElementChildrenPainter.paintChildren(asElementUnit(unit), globalPaintContext, localPaintContext);
	}

	private static ElementUnit asElementUnit(BuildableRenderedUnit unit) {
		return new ElementUnit(
			unit.display(),
			unit.styleDirectives(),
			LayoutResult.create(unit.childLayoutResults(), unit.fitSize()));
	}
	
}
