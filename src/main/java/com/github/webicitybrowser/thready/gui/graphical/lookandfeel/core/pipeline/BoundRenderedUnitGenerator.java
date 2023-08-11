package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline;

import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.imp.BoundRenderedUnitGeneratorImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;

public interface BoundRenderedUnitGenerator<V extends RenderedUnit> {

	RenderedUnitGenerator<V> getRaw();
	
	BoundRenderedUnit<V> getUnit(Function<RenderedUnitGenerator<V>, V> mapper);
	
	public static <V extends RenderedUnit> BoundRenderedUnitGenerator<V> create(RenderedUnitGenerator<V> renderBox, UIDisplay<?, ?, V> display) {
		return new BoundRenderedUnitGeneratorImp<>(renderBox, display);
	}
	
}
