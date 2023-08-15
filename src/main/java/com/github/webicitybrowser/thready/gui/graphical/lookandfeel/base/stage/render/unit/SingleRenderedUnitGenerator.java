package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.render.unit;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteSizeMath;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;

public class SingleRenderedUnitGenerator<T extends RenderedUnit> implements RenderedUnitGenerator<T> {

	private final T renderedUnit;
	
	private boolean completed;
	
	public SingleRenderedUnitGenerator(T renderedUnit) {
		this.renderedUnit = renderedUnit;
	}

	@Override
	public GenerationResult generateNextUnit(AbsoluteSize preferredBounds, boolean forceFit) {
		if (completed) {
			throw new IllegalStateException("Already returned rendered unit!");
		}
		if (!forceFit && !AbsoluteSizeMath.fits(renderedUnit.preferredSize(), preferredBounds)) {
			return GenerationResult.NO_FIT;
		}
		
		this.completed = true;

		return GenerationResult.NORMAL;
	}

	@Override
	public T getLastGeneratedUnit() {
		if (!completed) {
			return null;
		}
		return renderedUnit;
	}

	@Override
	public boolean completed() {
		return completed;
	}

}
