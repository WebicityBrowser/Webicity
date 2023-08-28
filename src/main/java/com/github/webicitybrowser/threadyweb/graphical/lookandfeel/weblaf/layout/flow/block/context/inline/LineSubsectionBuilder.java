package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.BuildableRenderedUnit;

public class LineSubsectionBuilder implements LineSectionBuilder {

	private final BuildableRenderedUnit unit;
	private final AbsolutePosition startPosition;

	private float totalHeight = 0;

	public LineSubsectionBuilder(
		AbsolutePosition offsetPosition, BuildableRenderedUnit unit
	) {
		this.startPosition = offsetPosition;
		this.unit = unit;
	}

	@Override
	public void addUnit(Rectangle bounds, RenderedUnit unit) {
		totalHeight = Math.max(totalHeight, bounds.size().height());
		AbsolutePosition adjustedPosition = new AbsolutePosition(
			bounds.position().x() - startPosition.x(),
			bounds.position().y() - startPosition.y());
		Rectangle adjustedBounds = new Rectangle(adjustedPosition, bounds.size());
		this.unit.addChildUnit(unit, adjustedBounds);
	}

	@Override
	public void finalize(AbsolutePosition endPosition) {
		unit.setPreferredSize(new AbsoluteSize(
			endPosition.x() - startPosition.x(),
			totalHeight
		));
	}

	public AbsolutePosition getStartPosition() {
		return startPosition;
	}

	public BuildableRenderedUnit getUnit() {
		return unit;
	}
	
}
