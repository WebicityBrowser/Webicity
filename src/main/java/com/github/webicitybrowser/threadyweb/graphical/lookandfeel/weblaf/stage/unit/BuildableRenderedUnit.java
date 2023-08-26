package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.imp.BuildableRenderedUnitImp;

public interface BuildableRenderedUnit extends InnerDisplayUnit {
	
	void addChildUnit(RenderedUnit childUnit, Rectangle relativeRect);

	void setPreferredSize(AbsoluteSize preferredSize);

	void markFinished();

	boolean wasMarkedFinished();

	static BuildableRenderedUnit create(UIDisplay<?, ?, ?> display) {
		return new BuildableRenderedUnitImp(display);
	}

}
