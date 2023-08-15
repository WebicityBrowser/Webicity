package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.imp.BoundRenderedUnitGeneratorImp;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;

public interface BoundRenderedUnitGenerator<V extends RenderedUnit> {

	RenderedUnitGenerator<V> getRaw();
	
	BoundRenderedUnit<V> getLastGeneratedUnit();
	
	public static <V extends RenderedUnit> BoundRenderedUnitGenerator<V> create(RenderedUnitGenerator<V> unitGenerator, UIDisplay<?, ?, V> display) {
		return new BoundRenderedUnitGeneratorImp<>(unitGenerator, display);
	}
	
}
