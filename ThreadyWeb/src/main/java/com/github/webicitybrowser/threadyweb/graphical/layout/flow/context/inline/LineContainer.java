package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.LineDimension.LineDirection;

public class LineContainer {
	
	private final LineDirection lineDirection;
	
	private final List<LineBox> lines;

	public LineContainer(LineDirection lineDirection) {
		this.lineDirection = lineDirection;
		this.lines = new ArrayList<>();
		
		newLine();
	}

	public LineContainer(LineDirection lineDirection, List<LineBox> lines) {
		this.lineDirection = lineDirection;
		this.lines = lines;
	}

	public LineDirection direction() {
		return lineDirection;
	}

	public List<LineBox> lines() {
		return lines;
	}

	public void reset() {
		lines.clear();
		newLine();
	}

	public LineBox currentLine() {
		if (lines.isEmpty()) return null;
		return lines.get(lines.size() - 1);
	}

	public void newLine() {
		LineBox oldLine = currentLine();
		LineBox newLine = new LineBox(lineDirection);
		if (oldLine != null) {
			newLine.addAllEntries(oldLine.splitRemainingSections());
		}
		lines.add(newLine);
	}

    public LineContainer map(Function<LineBox, LineBox> mapper) {
		List<LineBox> mappedLines = new ArrayList<>();
		for (LineBox line : lines) {
			mappedLines.add(mapper.apply(line));
		}
		
		return new LineContainer(lineDirection, mappedLines);
    }

}
