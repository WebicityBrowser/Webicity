package com.github.webicitybrowser.threadyweb.context.image;

import com.github.webicitybrowser.spec.dom.node.Element;

/**
 * The image engine is responsible for loading images.
 * It should attempt to adhere to the images section of the HTML spec
 * as much as possible.
 */
public interface ImageEngine {
	
	/**
	 * Create an image state managed by this image engine.
	 */
	ImageState createImageState();


	/**
	 * Update image data for the given element, using imageState to hold
	 * any persistant state.
	 * @param imageState The image state to use
	 * @param element The element to update the image data for
	 */
	void updateImageData(ImageState imageState, Element element);

}
