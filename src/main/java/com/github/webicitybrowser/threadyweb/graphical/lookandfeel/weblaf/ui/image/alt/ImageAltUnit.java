package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.alt;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageUnit;

public record ImageAltUnit(
	UIDisplay<?, ?, ?> display, AbsoluteSize fitSize, DirectivePool styleDirectives, String altText, Font2D font
) implements ImageUnit {

}
