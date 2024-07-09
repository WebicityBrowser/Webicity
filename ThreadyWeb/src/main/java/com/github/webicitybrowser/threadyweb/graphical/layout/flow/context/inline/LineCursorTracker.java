package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.LineDimension.LineDirection;

public class LineCursorTracker {
	
	private final LineDirection lineDirection;
	
	private LineDimension currentSize;
	private LineDimension currentPointer;

	public LineCursorTracker(LineDirection lineDirection) {
		this.lineDirection = lineDirection;
		this.currentSize = new LineDimension(0, 0, lineDirection);
		this.currentPointer = new LineDimension(0, 0, lineDirection);
	}

	public LineDirection direction() {
		return this.lineDirection;
	}
	
	public void add(AbsoluteSize unitSize) {
		LineDimension unitLineSize = LineDimensionConverter.convertToLineDimension(unitSize, lineDirection);
		this.currentSize = lineSizeCoveredAfterAdd(unitLineSize);
		float newPointerX = currentPointer.run() + unitLineSize.run();
		this.currentPointer = new LineDimension(newPointerX, currentPointer.depth(), lineDirection);
	}

	public boolean addWillOverflowLine(AbsoluteSize unitSize, float inlineSize) {
		LineDimension unitLineSize = LineDimensionConverter.convertToLineDimension(unitSize, lineDirection);
		if (inlineSize == RelativeDimension.UNBOUNDED) return false;
		if (unitLineSize.run() == 0) return false;
		
		return lineSizeCoveredAfterAdd(unitLineSize).run() > inlineSize;
	}
	
	public void reset() {
		this.currentSize = new LineDimension(0, 0, lineDirection);
		this.currentPointer = new LineDimension(0, 0, lineDirection);
	}

	public void nextLine() {
		this.currentPointer = new LineDimension(0, currentSize.depth(), lineDirection);
	}

	public AbsoluteSize getSizeCovered() {
		return LineDimensionConverter.convertToAbsoluteSize(currentSize);
	}

	public LineDimension getNextPosition() {
		return currentPointer;
	}
	
	private LineDimension lineSizeCoveredAfterAdd(LineDimension unitLineSize) {
		float runComponent = Math.max(currentSize.run(), currentPointer.run() + unitLineSize.run());
		float depthComponent = Math.max(currentSize.depth(), currentPointer.depth() + unitLineSize.depth());
		return new LineDimension(runComponent, depthComponent, lineDirection);
	}

}
