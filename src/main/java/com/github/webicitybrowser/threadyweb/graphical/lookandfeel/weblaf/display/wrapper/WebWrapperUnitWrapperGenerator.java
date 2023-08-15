package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;

public class WebWrapperUnitWrapperGenerator<V extends RenderedUnit> implements RenderedUnitGenerator<WebWrapperUnit<V>> {
	
	private final BoundRenderedUnitGenerator<V> innerUnitGenerator;
	private final WebWrapperWrapperBox<?, V> wrapperBox;

	private WebWrapperUnit<V> lastUnit;

	public WebWrapperUnitWrapperGenerator(BoundRenderedUnitGenerator<V> innerUnitGenerator, WebWrapperWrapperBox<?, V> wrapperBox) {
		this.innerUnitGenerator = innerUnitGenerator;
		this.wrapperBox = wrapperBox;
	}

	@Override
	public GenerationResult generateNextUnit(AbsoluteSize preferredBounds, boolean forceFit) {
		GenerationResult generationResult = innerUnitGenerator.getRaw().generateNextUnit(preferredBounds, forceFit);
		BoundRenderedUnit<V> innerUnit = innerUnitGenerator.getLastGeneratedUnit();
		lastUnit = innerUnit == null ? null : new WebWrapperUnit<>(wrapperBox, innerUnit);
		
		return generationResult;
	}

	@Override
	public WebWrapperUnit<V> getLastGeneratedUnit() {
		return lastUnit;
	}

	@Override
	public boolean completed() {
		return innerUnitGenerator.getRaw().completed();
	}

}
