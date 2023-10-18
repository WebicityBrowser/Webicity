package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.loaded;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageUnit;
import com.github.webicitybrowser.webicity.core.image.ImageData;
import com.github.webicitybrowser.webicity.core.image.ImageFrame;

public record LoadedImageUnit(UIDisplay<?, ?, ?> display, DirectivePool styleDirectives, ImageData imageData, Image[] imageFrames) implements ImageUnit {
	
	@Override
	public AbsoluteSize fitSize() {
		ImageFrame firstFrame = imageData.getFrames()[0];
		return new AbsoluteSize(firstFrame.getWidth(), firstFrame.getHeight());
	}

}
