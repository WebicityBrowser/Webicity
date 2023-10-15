package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.component.image;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.threadyweb.context.image.ImageEngine;
import com.github.webicitybrowser.threadyweb.context.image.ImageState;
import com.github.webicitybrowser.webicity.core.renderer.RendererContext;

public class ImageEngineImp implements ImageEngine {

	private final RendererContext rendererContext;

	public ImageEngineImp(RendererContext rendererContext) {
		this.rendererContext = rendererContext;
	}

	@Override
	public ImageState createImageState() {
		return new ImageStateImp();
	}

	@Override
	public void updateImageData(ImageState imageState, Element element) {
		
	}
	
}
