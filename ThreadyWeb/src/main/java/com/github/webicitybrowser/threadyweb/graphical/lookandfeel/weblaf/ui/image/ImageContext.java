package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image;

import com.github.webicitybrowser.codec.image.ImageData;
import com.github.webicitybrowser.codec.image.ImageFrame;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.drawing.core.image.ImageSource;
import com.github.webicitybrowser.thready.drawing.core.image.RasterBytesImageSource;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.context.GenericContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;

public class ImageContext extends GenericContext {

	private Image[] imageFrames;

	public ImageContext(UIDisplay<?, ?, ?> display, ComponentUI componentUI) {
		super(display, componentUI);
	}

	public Image[] cachedLoadedImageFrames(ResourceLoader resourceLoader, ImageData imageData) {
		if (imageFrames == null) {
			// TODO: Invalidate
			imageFrames = loadImageFrames(resourceLoader, imageData);
		}

		return imageFrames;
	}

	private static Image[] loadImageFrames(ResourceLoader resourceLoader, ImageData imageData) {
		Image[] imageFrames = new Image[imageData.frames().length];
		for (int i = 0; i < imageData.frames().length; i++) {
			ImageFrame frame = imageData.frames()[i];
			ImageSource imageSource = new RasterBytesImageSource(frame.width(), frame.height(), frame.bitmap());
			imageFrames[i] = resourceLoader.loadImage(imageSource);
		}

		return imageFrames;
	}

}
