package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.imp;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;

public record InnerDisplayUnitImp(
	UIDisplay<?, ?, ?> display, AbsoluteSize preferredSize, ChildLayoutResult[] childLayoutResults
) implements InnerDisplayUnit {
	
}
