package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.imp.InnerDisplayUnitImp;

public interface InnerDisplayUnit extends RenderedUnit {

	ChildLayoutResult[] childLayoutResults();

	static InnerDisplayUnit create(UIDisplay<?, ?, ?> display, AbsoluteSize preferredSize, ChildLayoutResult[] childLayoutResults) {
		return new InnerDisplayUnitImp(display, preferredSize, childLayoutResults);
	}

}
