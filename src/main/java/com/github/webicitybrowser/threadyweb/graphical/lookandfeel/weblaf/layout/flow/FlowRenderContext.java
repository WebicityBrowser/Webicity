package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow;

import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BuildableRenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitGenerator;

public record FlowRenderContext(
	ChildrenBox box,
	GlobalRenderContext globalRenderContext,
	LocalRenderContext localRenderContext,
	Function<DirectivePool, BuildableRenderedUnit> innerUnitGenerator,
	StyledUnitGenerator styledUnitGenerator,
	FlowRootContextSwitch flowRootContextSwitch
) {

}
