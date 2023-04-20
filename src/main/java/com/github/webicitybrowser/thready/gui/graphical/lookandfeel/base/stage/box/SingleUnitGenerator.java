package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.imp.EmptyUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.PartialUnitPreview;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.UnitGenerator;

public class SingleUnitGenerator implements UnitGenerator {

	private static final Unit EMPTY_UNIT = new EmptyUnit();
	
	private final Unit unit;
	
	private boolean merged;

	public SingleUnitGenerator(Unit unit) {
		this.unit = unit;
	}

	@Override
	public PartialUnitPreview previewNextUnit() {
		if (merged) {
			throw new IllegalStateException("Unit already merged!");
		}
		
		return new PartialUnitPreview() {
			@Override
			public AbsoluteSize sizeAfterAppend() {
				return unit.getMinimumSize();
			}

			@Override
			public void append() {
				merged = true;
			}
		};
	}

	@Override
	public Unit getMergedUnits() {
		return completed() ? unit : EMPTY_UNIT;
	}

	@Override
	public boolean completed() {
		return this.merged;
	}

}
