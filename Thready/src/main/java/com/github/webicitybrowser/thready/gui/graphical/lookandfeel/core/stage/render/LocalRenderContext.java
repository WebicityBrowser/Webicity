package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;

public record LocalRenderContext(AbsoluteSize preferredSize, ContextSwitch[] contextSwitches) {

	public static LocalRenderContext create(AbsoluteSize preferredSize, ContextSwitch[] switches) {
		return new LocalRenderContext(preferredSize, switches);
	}
	
}
