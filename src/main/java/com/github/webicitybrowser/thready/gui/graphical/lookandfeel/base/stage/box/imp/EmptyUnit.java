package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.imp;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;

public class EmptyUnit implements Unit {

	@Override
	public AbsoluteSize getMinimumSize() {
		return new AbsoluteSize(0, 0);
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return (context, canvas, viewport) -> {};
	}

}
