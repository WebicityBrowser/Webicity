package com.github.webicitybrowser.thready.drawing.core.image;

/**
 * This interface represents a source of an image. It can be passed to a
 * {@link com.github.webicitybrowser.thready.drawing.core.ResourceLoader} to
 * load an {@link Image}. It cannot be passed to drawing methods directly,
 * and must be loaded first. This interface alone is not enough to load an
 * image - it should be extended by a class representing a specific type of
 * image source, such as a byte array or a file.
 */
public interface ImageSource {

}
