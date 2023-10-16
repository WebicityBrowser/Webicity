package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.component.image;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.threadyweb.context.image.ImageEngine;
import com.github.webicitybrowser.threadyweb.context.image.ImageState;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererContext;

public class ImageEngineImp implements ImageEngine {

	private final HTMLRendererContext htmlRendererContext;

	public ImageEngineImp(HTMLRendererContext rendererContext) {
		this.htmlRendererContext = rendererContext;
	}

	@Override
	public ImageState createImageState() {
		return new ImageStateImp();
	}

	@Override
	public void updateImageData(ImageState imageState, Element element) {
		
	}
	
}
