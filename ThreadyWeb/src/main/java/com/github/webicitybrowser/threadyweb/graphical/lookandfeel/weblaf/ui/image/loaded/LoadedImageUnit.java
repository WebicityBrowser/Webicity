package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.loaded;

import com.github.webicitybrowser.codec.image.ImageData;
import com.github.webicitybrowser.codec.image.ImageFrame;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageUnit;

public record LoadedImageUnit(UIDisplay<?, ?, ?> display, DirectivePool styleDirectives, ImageData imageData, Image[] imageFrames) implements ImageUnit {
	
	@Override
	public AbsoluteSize fitSize() {
		ImageFrame firstFrame = imageData.frames()[0];
		return new AbsoluteSize(firstFrame.width(), firstFrame.height());
	}

}
