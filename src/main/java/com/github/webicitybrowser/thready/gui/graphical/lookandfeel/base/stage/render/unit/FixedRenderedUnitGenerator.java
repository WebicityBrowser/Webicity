package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.render.unit;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;

public class FixedRenderedUnitGenerator<T extends RenderedUnit> implements RenderedUnitGenerator<T> {

	private final T[] units;

	private int index = 0;

	@SuppressWarnings("unchecked")
	public FixedRenderedUnitGenerator(T... units) {
		this.units = units;
	}

	@Override
	public GenerationResult generateNextUnit(AbsoluteSize preferredBounds, boolean forceFit) {
		if (completed()) {
			index = units.length + 1;
			return GenerationResult.COMPLETED;
		}
		if (!forceFit && units[index].preferredSize().width() > preferredBounds.width()) {
			return GenerationResult.NO_FIT;
		}
		index++;

		return GenerationResult.NORMAL;
	}

	@Override
	public T getLastGeneratedUnit() {
		if (index == 0 || index > units.length) {
			return null;
		}
		return units[index - 1];
	}

	@Override
	public boolean completed() {
		return index >= units.length;
	}
	
}