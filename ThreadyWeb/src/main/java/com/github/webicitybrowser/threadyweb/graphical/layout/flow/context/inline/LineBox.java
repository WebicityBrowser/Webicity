package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import java.util.ArrayList;
import java.util.List;
import java.util.Deque;
import java.util.ArrayDeque;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.LineDimension.LineDirection;

public class LineBox {
	
	private final LineDirection lineDirection;
	private final List<LineEntry> lineEntries;
	private final Rectangle positioningInfo;

	private final Deque<LineEntry.UnitEnter> activeUnits = new ArrayDeque<>();

	public LineBox(LineDirection lineDirection) {
		this(lineDirection, null, null);
	}

	public LineBox(LineDirection lineDirection, List<LineEntry> initialLineEntries, Rectangle positioningInfo) {
		this.lineDirection = lineDirection;
		this.lineEntries = new ArrayList<>();
		this.positioningInfo = positioningInfo;

		if (initialLineEntries != null) {
			addAllEntries(initialLineEntries);
		}
	}

	public void addEntry(LineEntry lineEntry) {
		lineEntries.add(lineEntry);
		if (lineEntry instanceof LineEntry.UnitEnter unitEnter) {
			activeUnits.push(unitEnter);
		} else if (lineEntry instanceof LineEntry.UnitExit) {
			activeUnits.pop();
		}
	}

	public void addAllEntries(List<LineEntry> lineEntries) {
		for (LineEntry lineEntry : lineEntries) {
			addEntry(lineEntry);
		}
	}

	public List<LineEntry> entries() {
		return lineEntries;
	}

	public LineDirection direction() {
		return lineDirection;
	}

	public Rectangle positioningInfo() {
		return positioningInfo;
	}

	public List<LineEntry> splitRemainingSections() {
		List<LineEntry> splitOff = new ArrayList<>();
		while (!activeUnits.isEmpty()) {
			splitOff.add(new LineEntry.UnitEnter(activeUnits.pop().directives(), false));
			lineEntries.add(new LineEntry.UnitExit());
		}

		return splitOff;
	}

}
