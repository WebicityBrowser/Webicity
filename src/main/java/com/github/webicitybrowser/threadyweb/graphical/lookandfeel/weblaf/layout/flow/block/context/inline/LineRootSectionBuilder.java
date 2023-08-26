package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class LineRootSectionBuilder implements LineSectionBuilder {

	private final List<ChildLayoutResult> childLayoutResults = new ArrayList<>();
	private final AbsolutePosition offsetPosition;

	private float totalHeight = 0;

	public LineRootSectionBuilder(AbsolutePosition offsetPosition) {
		this.offsetPosition = offsetPosition;
	}

	@Override
	public void addUnit(Rectangle bounds, RenderedUnit unit) {
		totalHeight = Math.max(totalHeight, bounds.size().height());
		AbsolutePosition adjustedPosition = new AbsolutePosition(
			bounds.position().x() + offsetPosition.x(),
			bounds.position().y() + offsetPosition.y());
		Rectangle adjustedBounds = new Rectangle(adjustedPosition, bounds.size());
		childLayoutResults.add(new ChildLayoutResult(unit, adjustedBounds));
	}

	@Override
	public void finalize(AbsolutePosition endPosition) {
		// TODO Auto-generated method stub
	}

	public List<ChildLayoutResult> getChildLayoutResults() {
		return childLayoutResults;
	}
	
}
