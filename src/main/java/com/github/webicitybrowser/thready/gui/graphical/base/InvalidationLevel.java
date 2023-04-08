package com.github.webicitybrowser.thready.gui.graphical.base;

/**
 * Represents stages of the rendering pipeline the must be reprocessed.
 */
public enum InvalidationLevel {

	NONE, PAINT, COMPOSITE, RENDER, BOX
	
}
