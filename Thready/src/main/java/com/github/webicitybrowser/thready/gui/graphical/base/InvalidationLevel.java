package com.github.webicitybrowser.thready.gui.graphical.base;

/**
 * Represents stages of the rendering pipeline the must be reprocessed.
 */
public enum InvalidationLevel {

	NONE, PAINT_LAYERS, PAINT, COMPOSITE, RENDER, BOX, STYLE
	
}
