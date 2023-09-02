package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record StyledUnitContext(Box box, RenderedUnit innerUnit, AbsoluteSize size, float[] padding) {

	public AbsoluteSize innerUnitSize() {
		return new AbsoluteSize(
			size.width() - padding[0] - padding[1],
			size.height() - padding[2] - padding[3]);
	}

	public AbsolutePosition innerUnitPosition() {
		return new AbsolutePosition(padding[0], padding[2]);
	}

	public DirectivePool styleDirectives() {
		return box.styleDirectives();
	}

}
