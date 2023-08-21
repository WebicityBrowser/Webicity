package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.display.wrapper;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;

public class WebWrapperUnitWrapperGenerator<V extends RenderedUnit> implements RenderedUnitGenerator<WebWrapperUnit<V>> {
	
	private final UIDisplay<?, ?, ?> display;
	private final RenderedUnitGenerator<V> innerUnitGenerator;
	private final WebWrapperBox wrapperBox;

	private WebWrapperUnit<V> lastUnit;

	public WebWrapperUnitWrapperGenerator(UIDisplay<?, ?, ?> display, RenderedUnitGenerator<V> innerUnitGenerator, WebWrapperBox wrapperBox) {
		this.display = display;
		this.innerUnitGenerator = innerUnitGenerator;
		this.wrapperBox = wrapperBox;
	}

	@Override
	public GenerationResult generateNextUnit(AbsoluteSize preferredBounds, boolean forceFit) {
		GenerationResult generationResult = innerUnitGenerator.generateNextUnit(preferredBounds, forceFit);
		V innerUnit = innerUnitGenerator.getLastGeneratedUnit();
		lastUnit = innerUnit == null ? null : new WebWrapperUnit<>(display, wrapperBox, innerUnit);
		
		return generationResult;
	}

	@Override
	public WebWrapperUnit<V> getLastGeneratedUnit() {
		return lastUnit;
	}

	@Override
	public boolean completed() {
		return innerUnitGenerator.completed();
	}

}
