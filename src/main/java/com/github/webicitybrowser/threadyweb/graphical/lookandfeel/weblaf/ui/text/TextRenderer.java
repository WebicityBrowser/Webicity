package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontDecoration;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.TextAdjustContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;

public final class TextRenderer {

	public static TextUnit createTextUnit(TextBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		FontSettings fontSettings = getFontSettings(box);
		Font2D font = renderContext.getResourceLoader().loadFont(fontSettings);
		String text = getAdjustedText(box, localRenderContext.getContextSwitches());

		float width = font.getMetrics().getStringWidth(text);
		float height = font.getMetrics().getCapHeight();
		AbsoluteSize preferredSize = new AbsoluteSize(width, height);

		return new TextUnit(preferredSize, box, text, font);
	}

	private static String getAdjustedText(TextBox box, ContextSwitch[] contextSwitches) {
		TextComponent owningComponent = box.owningComponent();
		for (ContextSwitch contextSwitch : contextSwitches) {
			if (contextSwitch instanceof TextAdjustContextSwitch textAdjustContextSwitch) {
				return textAdjustContextSwitch.getNextText(owningComponent);
			}
		}

		return owningComponent.getText();
	}

	private static FontSettings getFontSettings(TextBox box) {
		return new FontSettings(
			WebDirectiveUtil.getFontSource(box.styleDirectives()),
			WebDirectiveUtil.getFontSize(box.styleDirectives()),
			WebDirectiveUtil.getFontWeight(box.styleDirectives()).getWeight(400),
			new FontDecoration[0]);
	}

}
