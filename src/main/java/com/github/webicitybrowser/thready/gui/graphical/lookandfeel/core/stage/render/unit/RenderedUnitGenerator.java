package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;

public interface RenderedUnitGenerator<T extends RenderedUnit> {

	// ForceFit: Still make a minimal unit, even if it would not fit
	// Returns null if no unit will fit and not forceFit.
	GenerationResult generateNextUnit(AbsoluteSize preferredBounds, boolean forceFit);

	T getLastGeneratedUnit();

	boolean completed();

	public static enum GenerationResult {
		NORMAL, NO_FIT, COMPLETED
	}
	
}
