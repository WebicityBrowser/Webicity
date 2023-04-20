package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.stage.render;

import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.FluidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.UnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.stage.render.unit.TextUnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;

public class TextComponentRenderer implements FluidRenderer {

	private final TextComponent component;
	private final Box box;

	public TextComponentRenderer(TextComponent component, Box box) {
		this.component = component;
		this.box = box;
	}

	@Override
	public UnitGenerator renderFluid(RenderContext renderContext, ContextSwitch[] switches) {
		FontSettings fontSettings = getFontSettings();
		Font2D font = renderContext.getResourceLoader().loadFont(fontSettings);
		
		String text = component.getText();
		float[] charWidths = calculateCharWidths(text, font.getMetrics());
		
		return new TextUnitGenerator(box, text, font, charWidths, switches);
	}

	private FontSettings getFontSettings() {
		return WebDirectiveUtil.getFontSettings(box.getStyleDirectives());
	}
	
	private float[] calculateCharWidths(String text, FontMetrics metrics) {
		float[] sizes = new float[text.length()];
		for (int i = 0; i < sizes.length; i++) {
			sizes[i] = metrics.getCharacterWidth(text.codePointAt(i));
		}
		
		return sizes;
	}

}
