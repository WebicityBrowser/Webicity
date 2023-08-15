package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;

public class TextUnitGenerator implements RenderedUnitGenerator<TextRenderedUnit> {

	private final TextBox box;
	private final String text;
	private final Font2D font;
	private final float[] charWidths;
	private final float height;
	
	private int windowStart = 0;
	private int windowEnd = 0;

	private TextRenderedUnit lastUnit;
	
	public TextUnitGenerator(TextBox box, String text, Font2D font, float[] charWidths) {
		this.box = box;
		this.text = text;
		this.font = font;
		this.charWidths = charWidths;
		this.height = font.getMetrics().getHeight();
	}
	
	@Override
	public GenerationResult generateNextUnit(AbsoluteSize preferredBounds, boolean forceFit) {
		float totalWidth = 0;
		windowStart = windowEnd;
		while (!completed() && ((forceFit && totalWidth == 0) || !nextCharWillOverflow(totalWidth, preferredBounds))) {
			totalWidth += charWidths[windowEnd];
			windowEnd++;
		}
		
		if (windowStart == windowEnd) {
			return GenerationResult.NO_FIT;
		}
		
		String subtext = text.substring(windowStart, windowEnd);
		AbsoluteSize textSize = new AbsoluteSize(totalWidth, height);
		lastUnit = new TextRenderedUnit(textSize, box.styleDirectives(), box, subtext, font);

		return GenerationResult.NORMAL;
	}

	@Override
	public TextRenderedUnit getLastGeneratedUnit() {
		return lastUnit;
	}

	@Override
	public boolean completed() {
		return windowEnd == text.length();
	}
	
	private boolean nextCharWillOverflow(float totalWidth, AbsoluteSize preferredBounds) {
		return preferredBounds.width() == RelativeDimension.UNBOUNDED ?
			false :
			totalWidth + charWidths[windowEnd] > preferredBounds.width();
	}
	
	// TODO: Support vertical orientation

}
