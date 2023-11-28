package com.github.webicitybrowser.thready.drawing.core;

/**
 * This record contains settings for creating a child canvas.
 * @param preservePosition If true, co-ordinates provided to operations
 *  called on the child canvas will draw at the same position as if those
 *  operations were called on the parent canvas. If false, co-ordinates
 *  provided to operations called on the child canvas will draw offset
 *  by the position of the child canvas.
 */
public record ChildCanvasSettings(boolean preservePosition) {

}
