package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.adjusted;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class AdjustedLayoutRenderer {

	private AdjustedLayoutRenderer() {}
	
	public static List<ChildLayoutResult> render(
		LayoutManagerContext layoutManagerContext, List<Box> outOfFlowChildren, Function<Boolean, SizeCalculationContext> sizeCalculationContextGenerator
	) {
		List<ChildLayoutResult> layoutResults = new ArrayList<>(outOfFlowChildren.size());
		/*for (Box child : layoutManagerContext.children()) {
			PositionType positionType = PositionOffsetUtil.getPositionType(child.styleDirectives());
			
		}*/
		// TODO: Implement non-relative sizes

		return layoutResults;
	}

}
