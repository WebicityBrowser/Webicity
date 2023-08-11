package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;

public final class TextRenderer {

	public static RenderedUnitGenerator<TextRenderedUnit> createTextUnitGenerator(TextBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		FontSettings fontSettings = getFontSettings(box);
		Font2D font = renderContext.getResourceLoader().loadFont(fontSettings);
		
		String text = box.owningComponent().getText();
		float[] charWidths = calculateCharWidths(text, font.getMetrics());
		
		return new TextUnitGenerator(box, text, font, charWidths);
	}

	private static FontSettings getFontSettings(TextBox box) {
		return SimpleDirectiveUtil.getFontSettings(box.styleDirectives());
	}
	
	private static float[] calculateCharWidths(String text, FontMetrics metrics) {
		float[] sizes = new float[text.length()];
		for (int i = 0; i < sizes.length; i++) {
			sizes[i] = metrics.getCharacterWidth(text.codePointAt(i));
		}
		
		return sizes;
	}

}
