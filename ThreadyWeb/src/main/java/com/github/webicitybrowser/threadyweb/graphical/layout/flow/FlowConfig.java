package com.github.webicitybrowser.threadyweb.graphical.layout.flow;

import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitGenerator;

public record FlowConfig(Function<Box, BuildableRenderedUnit> buildableUnitGenerator, StyledUnitGenerator styledUnitGenerator) {
	
}
