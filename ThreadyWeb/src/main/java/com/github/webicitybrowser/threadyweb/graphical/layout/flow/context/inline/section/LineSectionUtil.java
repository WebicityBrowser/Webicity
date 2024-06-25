package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.section;

import java.util.List;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension.LineDirection;

public final class LineSectionUtil {
	
	private LineSectionUtil() {}

	public static LineDimension determineTotalLineSize(List<ChildEntry> childEntries, LineDirection lineDirection) {
		float run = 0;
		float depth = 0;

		for (ChildEntry childEntry: childEntries) {
			float runAfterChild = childEntry.position.run() + childEntry.adjustedSize.run();
			float depthAfterChild = childEntry.position.depth() + childEntry.adjustedSize.depth();
			run = Math.max(run, runAfterChild);
			depth = Math.max(depth, depthAfterChild);
		}	
	
		return new LineDimension(run, depth, lineDirection);
	}

	public static record ChildEntry(RenderedUnit unit, LineDimension adjustedSize, LineDimension position) {}

}
