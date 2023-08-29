package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.imp.BuildableRenderedUnitImp;

public interface BuildableRenderedUnit extends RenderedUnit {
	
	void addChildUnit(RenderedUnit childUnit, Rectangle relativeRect);

	ChildLayoutResult[] childLayoutResults();

	DirectivePool styleDirectives();

	void setFitSize(AbsoluteSize fitSize);

	void markFinished();

	boolean wasMarkedFinished();

	static BuildableRenderedUnit create(UIDisplay<?, ?, ?> display, DirectivePool styleDirectives) {
		return new BuildableRenderedUnitImp(display, styleDirectives);
	}

}
