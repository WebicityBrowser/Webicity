package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.loaded;

import com.github.webicitybrowser.codec.image.ImageData;
import com.github.webicitybrowser.codec.image.ImageFrame;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageUnit;

public record LoadedImageUnit(Box box, ImageData imageData, Image[] imageFrames) implements ImageUnit {
	
	@Override
	public AbsoluteSize fitSize() {
		ImageFrame firstFrame = imageData.frames()[0];
		return new AbsoluteSize(firstFrame.width(), firstFrame.height());
	}

}
