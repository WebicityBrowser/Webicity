package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.render.unit;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.paint.BlockWrappingPainter;

public class BlockWrappingUnit implements Unit {

	public BlockWrappingUnit(AbsoluteSize finalSize, Box box) {
		
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new BlockWrappingPainter(documentRect);
	}
	
	public static Unit render(AbsoluteSize precomputedSize, Box box) {
		AbsoluteSize finalSize = precomputedSize;
		return new BlockWrappingUnit(finalSize, box);
	}
	
}
