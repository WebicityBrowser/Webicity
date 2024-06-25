package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.marker;

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
