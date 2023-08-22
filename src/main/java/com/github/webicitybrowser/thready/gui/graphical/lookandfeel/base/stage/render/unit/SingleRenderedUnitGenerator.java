package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.render.unit;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteSizeMath;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;

public class SingleRenderedUnitGenerator<T extends RenderedUnit> implements RenderedUnitGenerator<T> {

	private final T renderedUnit;
	
	private byte stage = 0;
	
	public SingleRenderedUnitGenerator(T renderedUnit) {
		assert renderedUnit != null;
		this.renderedUnit = renderedUnit;
	}

	@Override
	public GenerationResult generateNextUnit(AbsoluteSize preferredBounds, boolean forceFit) {
		if (stage > 0) {
			this.stage = 2;
			return GenerationResult.COMPLETED;
		}
		if (!forceFit && !AbsoluteSizeMath.fits(renderedUnit.preferredSize(), preferredBounds)) {
			return GenerationResult.NO_FIT;
		}
		
		this.stage = 1;

		return GenerationResult.NORMAL;
	}

	@Override
	public T getLastGeneratedUnit() {
		if (this.stage != 1) {
			return null;
		}
		return renderedUnit;
	}

	@Override
	public boolean completed() {
		return this.stage > 0;
	}

}
