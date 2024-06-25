package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.BoxOffsetDimensions;

public record StyledUnitContext(
	Box box, RenderedUnit innerUnit, AbsoluteSize size, BoxOffsetDimensions boxOffsetDimensions
) {

	public AbsoluteSize innerUnitSize() {
		float[] totalPadding = boxOffsetDimensions.totalPadding();
		return new AbsoluteSize(
			size.width() - totalPadding[0] - totalPadding[1],
			size.height() - totalPadding[2] - totalPadding[3]);
	}

	public AbsolutePosition innerUnitPosition() {
		float[] padding = boxOffsetDimensions.padding();
		float[] borders = boxOffsetDimensions.borders();
		return new AbsolutePosition(padding[0] + borders[0], padding[2] + borders[2]);
	}

	public DirectivePool styleDirectives() {
		return box.styleDirectives();
	}

}
