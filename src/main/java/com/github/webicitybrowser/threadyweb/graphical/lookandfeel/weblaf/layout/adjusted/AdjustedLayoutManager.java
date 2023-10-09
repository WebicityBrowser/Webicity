package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.adjusted;

import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;

public class AdjustedLayoutManager implements SolidLayoutManager {

	private final SolidLayoutManager innerLayoutManager;

	public AdjustedLayoutManager(SolidLayoutManager innerLayoutManager) {
		this.innerLayoutManager = innerLayoutManager;
	}

	@Override
	public LayoutResult render(LayoutManagerContext layoutManagerContext) {
		LayoutResult innerLayoutResult = innerLayoutManager.render(layoutManagerContext);
		ChildLayoutResult[] adjustedChildLayoutResults = adjustChildLayoutResults(innerLayoutResult.childLayoutResults());

		return LayoutResult.create(adjustedChildLayoutResults, innerLayoutResult.fitSize());
	}

	private ChildLayoutResult[] adjustChildLayoutResults(ChildLayoutResult[] childLayoutResults) {
		ChildLayoutResult[] adjustedChildLayoutResults = new ChildLayoutResult[childLayoutResults.length];
		for (int i = 0; i < childLayoutResults.length; i++) {
			adjustedChildLayoutResults[i] = adjustChildLayoutResult(childLayoutResults[i]);
		}

		return adjustedChildLayoutResults;
	}

	private ChildLayoutResult adjustChildLayoutResult(ChildLayoutResult childLayoutResult) {
		return childLayoutResult;		
	}
	
}
