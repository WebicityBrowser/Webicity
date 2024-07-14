package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.loaded;

import com.github.webicitybrowser.codec.image.ImageData;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageUnit;

public class LoadedImageRenderer {

	public static ImageUnit render(ImageBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, ImageData imageData) {
		Image[] imageFrames = box.displayContext().cachedLoadedImageFrames(globalRenderContext.resourceLoader(), imageData);
		return new LoadedImageUnit(box, imageData, imageFrames);
	}

}
