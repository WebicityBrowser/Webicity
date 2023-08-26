package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker;

public interface UnitMarker extends LineMarker {
	
	default float leftEdgeSize() {
		return 0;
	}

	default float topEdgeSize() {
		return 0;
	}

	default float rightEdgeSize() {
		return 0;
	}

	default float bottomEdgeSize() {
		return 0;
	}

}
