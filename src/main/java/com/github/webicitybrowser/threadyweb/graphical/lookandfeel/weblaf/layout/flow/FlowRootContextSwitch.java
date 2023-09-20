package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.FloatContext;

public record FlowRootContextSwitch(AbsolutePosition predictedPosition, FloatContext floatContext) implements ContextSwitch {
	
}
