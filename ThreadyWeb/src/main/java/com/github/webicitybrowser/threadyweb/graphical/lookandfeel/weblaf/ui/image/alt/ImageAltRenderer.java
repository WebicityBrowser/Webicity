package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.alt;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.ImageUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebFontUtil;

public final class ImageAltRenderer {

	private ImageAltRenderer() {}

	public static ImageUnit render(ImageBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, String altText) {
		Font2D font = WebFontUtil.getFont(box.styleDirectives(), globalRenderContext);

		float width = font.getMetrics().getStringWidth(altText);
		float height = font.getMetrics().getCapHeight();

		AbsoluteSize fitSize = new AbsoluteSize(width, height);

		return new ImageAltUnit(box.display(), fitSize, box.styleDirectives(), altText, font);
	}

}
