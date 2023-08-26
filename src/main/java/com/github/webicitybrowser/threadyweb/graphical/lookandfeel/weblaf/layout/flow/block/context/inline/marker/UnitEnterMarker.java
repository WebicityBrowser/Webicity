package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;

public record UnitEnterMarker(boolean isStart, DirectivePool styleDirectives) implements UnitMarker {
	
	public UnitEnterMarker split() {
		return new UnitEnterMarker(false, styleDirectives);
	}

}
