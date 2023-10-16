package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebFontUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class ImageAltRenderer {

	private ImageAltRenderer() {}

	public static ImageUnit render(ImageBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, String altText) {
		SizeCalculationContext calculationContext = LayoutSizeUtils.createSizeCalculationContext(globalRenderContext, localRenderContext, true);
		Font2D font = WebFontUtil.getFont(box.styleDirectives(), calculationContext, globalRenderContext);

		float width = font.getMetrics().getStringWidth(altText);
		float height = font.getMetrics().getCapHeight();

		AbsoluteSize fitSize = new AbsoluteSize(width, height);

		return new ImageAltUnit(box.display(), fitSize, box.styleDirectives(), altText, font);
	}

}
