package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.section;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension;

public interface LineSectionBuilder {
	
	void addUnit(RenderedUnit unit, LineDimension adjustedSize, LineDimension itemPosition);

	void finalize(AbsoluteSize sectionSize);

}
