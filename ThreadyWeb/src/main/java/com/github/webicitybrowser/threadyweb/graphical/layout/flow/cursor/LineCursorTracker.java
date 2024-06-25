package com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension.LineDirection;

public class LineCursorTracker implements CursorTracker {
	
	private final LineDirection lineDirection;
	
	private LineDimension currentSize;
	private LineDimension currentPointer;

	public LineCursorTracker(LineDirection lineDirection) {
		this.lineDirection = lineDirection;
		this.currentSize = new LineDimension(0, 0, lineDirection);
		this.currentPointer = new LineDimension(0, 0, lineDirection);
	}
	
	@Override
	public void add(AbsoluteSize unitSize) {
		LineDimension unitLineSize = LineDimensionConverter.convertToLineDimension(unitSize, lineDirection);
		this.currentSize = lineSizeCoveredAfterAdd(unitLineSize);
		float newPointerX = currentPointer.run() + unitLineSize.run();
		this.currentPointer = new LineDimension(newPointerX, currentPointer.depth(), lineDirection);
	}

	@Override
	public boolean addWillOverflowLine(AbsoluteSize unitSize, LineDimension lineSize) {
		LineDimension unitLineSize = LineDimensionConverter.convertToLineDimension(unitSize, lineDirection);
		if (lineSize.run() == RelativeDimension.UNBOUNDED) {
			return false;
		}
		return lineSizeCoveredAfterAdd(unitLineSize).run() > lineSize.run();
	}
	
	@Override
	public void nextLine() {
		this.currentPointer = new LineDimension(0, currentSize.depth(), lineDirection);
	}

	@Override
	public AbsoluteSize getSizeCovered() {
		return LineDimensionConverter.convertToAbsoluteSize(currentSize);
	}

	@Override
	public LineDimension getNextPosition() {
		return currentPointer;
	}
	
	private LineDimension lineSizeCoveredAfterAdd(LineDimension unitLineSize) {
		float runComponent = Math.max(currentSize.run(), currentPointer.run() + unitLineSize.run());
		float depthComponent = Math.max(currentSize.depth(), currentPointer.depth() + unitLineSize.depth());
		return new LineDimension(runComponent, depthComponent, lineDirection);
	}

}
