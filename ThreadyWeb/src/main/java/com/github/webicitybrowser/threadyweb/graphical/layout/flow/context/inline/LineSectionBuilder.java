package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.LineDimension.LineDirection;

public class LineSectionBuilder {

	private final LineDirection lineDirection;

	private final List<RenderedUnit> units = new ArrayList<>();

	public LineSectionBuilder(LineDirection lineDirection) {
		this.lineDirection = lineDirection;
	}

	public void addUnit(RenderedUnit unit) {
		units.add(unit);
	}

	public ChildLayoutResult[] render() {
		List<ChildLayoutResult> results = new ArrayList<>();
		LineCursorTracker cursorTracker = new LineCursorTracker(lineDirection);
		for (RenderedUnit unit : units) {
			LineDimension currentPosition = cursorTracker.getNextPosition();
			// TODO: Don't pass null
			AbsolutePosition actualPosition = LineDimensionConverter.convertToAbsolutePosition(currentPosition, null, null);
			ChildLayoutResult result = new ChildLayoutResult(unit, new Rectangle(actualPosition, unit.fitSize()));
			cursorTracker.add(unit.fitSize());
			results.add(result);
		}

		return results.toArray(new ChildLayoutResult[0]);
	}

}
