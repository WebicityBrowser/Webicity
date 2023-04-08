package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.render.unit;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.paint.BlockWrappingPainter;

public class BlockWrappingUnit implements Unit {

	private final Box box;
	private final Unit innerUnit;

	public BlockWrappingUnit(Box box, AbsoluteSize finalSize, Unit innerUnit) {
		this.box = box;
		this.innerUnit = innerUnit;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		Painter innerPainter = innerUnit.getPainter(documentRect);
		return new BlockWrappingPainter(box, documentRect, innerPainter);
	}
	
	public static Unit render(Box box, AbsoluteSize precomputedSize, Function<AbsoluteSize, Unit> innerUnitGenerator) {
		Unit innerContentUnit = innerUnitGenerator.apply(precomputedSize);
		AbsoluteSize finalSize = precomputedSize;
		return new BlockWrappingUnit(box, finalSize, innerContentUnit);
	}
	
}
