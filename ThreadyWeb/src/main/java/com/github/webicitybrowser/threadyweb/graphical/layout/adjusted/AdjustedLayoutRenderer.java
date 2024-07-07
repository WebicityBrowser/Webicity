package com.github.webicitybrowser.threadyweb.graphical.layout.adjusted;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;

public final class AdjustedLayoutRenderer {

	private AdjustedLayoutRenderer() {}
	
	public static List<ChildLayoutResult> render(LayoutManagerContext layoutManagerContext, List<Box> outOfFlowChildren) {
		List<ChildLayoutResult> layoutResults = new ArrayList<>(outOfFlowChildren.size());
		// TODO: Implement non-relative sizes

		return layoutResults;
	}

}
