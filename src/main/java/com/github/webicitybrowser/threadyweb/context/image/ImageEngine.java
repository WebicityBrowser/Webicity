package com.github.webicitybrowser.threadyweb.context.image;

import com.github.webicitybrowser.spec.dom.node.Element;

public interface ImageEngine {
	
	ImageState createImageState();

	void updateImageData(ImageState imageState, Element element);

}
