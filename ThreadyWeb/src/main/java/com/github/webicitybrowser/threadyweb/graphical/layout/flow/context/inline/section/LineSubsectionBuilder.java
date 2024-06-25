package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.section;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.section.LineSectionUtil.ChildEntry;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BuildableRenderedUnit;

public class LineSubsectionBuilder implements LineSectionBuilder {

	private final List<ChildEntry> childEntries = new ArrayList<>(4);
	private final BuildableRenderedUnit unit;
	private final LineDimension startPosition;

	public LineSubsectionBuilder(
		LineDimension offsetPosition, BuildableRenderedUnit unit
	) {
		this.startPosition = offsetPosition;
		this.unit = unit;
	}

	@Override
	public void addUnit(RenderedUnit unit, LineDimension adjustedSize, LineDimension itemPosition) {
		assert adjustedSize.direction() == itemPosition.direction();
		LineDimension adjustedPosition = AbsoluteDimensionsMath.diff(
			itemPosition, startPosition,
			(run, depth) -> new LineDimension(run, depth, startPosition.direction()));
		childEntries.add(new ChildEntry(unit, adjustedSize, adjustedPosition));
	}

	@Override
	public void finalize(AbsoluteSize sectionSize) {
		LineDimension fitSize = LineSectionUtil.determineTotalLineSize(childEntries, startPosition.direction());
		AbsoluteSize absoluteFitSize = LineDimensionConverter.convertToAbsoluteSize(fitSize);
		unit.setFitSize(absoluteFitSize);

		for (ChildEntry childEntry: childEntries) {
			AbsoluteSize itemSize = LineDimensionConverter.convertToAbsoluteSize(childEntry.adjustedSize());
			AbsolutePosition itemPosition = LineDimensionConverter.convertToAbsolutePosition(childEntry.position(), absoluteFitSize, itemSize);
			Rectangle itemBounds = new Rectangle(itemPosition, itemSize);
			unit.addChildUnit(childEntry.unit(), itemBounds);
		}
	}

	public LineDimension getStartPosition() {
		return startPosition;
	}

	public BuildableRenderedUnit getUnit() {
		return unit;
	}
	
}
