package com.github.webicitybrowser.webicitybrowser.gui.ui.button;

import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.drawing.core.image.ImageSource;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;

public final class CircularButtonRenderer {

	private CircularButtonRenderer() {}
	
	public static CircularButtonUnit render(CircularButtonBox box, GlobalRenderContext renderContext) {
		ImageSource imageSource = box.owningComponent().getImageSource();
		Image image = renderContext.getResourceLoader().loadImage(imageSource);
		return new CircularButtonUnit(image, box);
	}

}
