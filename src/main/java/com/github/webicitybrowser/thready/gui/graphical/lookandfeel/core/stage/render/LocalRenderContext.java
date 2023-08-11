package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;

public interface LocalRenderContext {

	AbsoluteSize getPreferredSize();
	
	ContextSwitch[] getContextSwitches();
	
	public static LocalRenderContext create(AbsoluteSize preferredSize, ContextSwitch[] switches) {
		return new LocalRenderContext() {
			
			@Override
			public AbsoluteSize getPreferredSize() {
				return preferredSize;
			}
			
			@Override
			public ContextSwitch[] getContextSwitches() {
				return switches;
			}
			
		};
	}
	
}
