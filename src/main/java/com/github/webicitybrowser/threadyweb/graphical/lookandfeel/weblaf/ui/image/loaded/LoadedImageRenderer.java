package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.loaded;

import com.github.webicitybrowser.codec.image.ImageData;
import com.github.webicitybrowser.codec.image.ImageFrame;
import com.github.webicitybrowser.thready.drawing.core.ResourceLoader;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.drawing.core.image.ImageSource;
import com.github.webicitybrowser.thready.drawing.core.image.RasterBytesImageSource;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageUnit;

public class LoadedImageRenderer {

	public static ImageUnit render(ImageBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, ImageData imageData) {
		Image[] imageFrames = loadImageFrames(globalRenderContext.resourceLoader(), imageData);
		return new LoadedImageUnit(box.display(), box.styleDirectives(), imageData, imageFrames);
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
