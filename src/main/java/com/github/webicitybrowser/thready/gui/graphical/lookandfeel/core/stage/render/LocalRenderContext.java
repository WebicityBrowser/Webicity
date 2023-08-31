package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;

public interface LocalRenderContext {

	AbsoluteSize getPreferredSize();
	
	FontMetrics getParentFontMetrics();

	ContextSwitch[] getContextSwitches();
	
	public static LocalRenderContext create(AbsoluteSize preferredSize, FontMetrics parentFontMetrics, ContextSwitch[] switches) {
		return new LocalRenderContext() {
			
			@Override
			public AbsoluteSize getPreferredSize() {
				return preferredSize;
			}

			@Override
			public FontMetrics getParentFontMetrics() {
				return parentFontMetrics;
			}
			
			@Override
			public ContextSwitch[] getContextSwitches() {
				return switches;
			}
			
		};
	}
	
}
