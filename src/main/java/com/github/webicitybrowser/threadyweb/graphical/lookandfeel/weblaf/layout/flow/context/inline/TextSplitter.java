package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline;

import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;


public class TextSplitter {
	
	private final String text;
	private final Font2D font;
	private final float letterSpacing;
	private final float[] charWidths;

	private int windowEnd = 0;

	public TextSplitter(String text, Font2D font, float letterSpacing) {
		this.text = text;
		this.font = font;
		this.letterSpacing = letterSpacing;
		this.charWidths = generateCharWidths();
	}

	public String getFittingText(boolean forceFit, float maxWidth) {
		if (completed()) {
			throw new IllegalStateException("Already completed");
		}

		float currentWidth = 0;
		int windowStart = windowEnd;
		while (!completed() && ((forceFit && currentWidth == 0) || !nextCharWillOverflow(currentWidth, maxWidth))) {
			if (currentWidth != 0) {
				currentWidth += letterSpacing;
			}
			
			currentWidth += charWidths[windowEnd];
			windowEnd++;
		}

		if (windowStart == windowEnd) {
			return null;
		}

		return text.substring(windowStart, windowEnd);
	}

	public boolean completed() {
		return windowEnd >= text.length();
	}

	private float[] generateCharWidths() {
		FontMetrics metrics = font.getMetrics();
		float[] sizes = new float[text.length()];
		for (int i = 0; i < sizes.length; i++) {
			sizes[i] = metrics.getCharacterWidth(text.codePointAt(i));
		}
		
		return sizes;
	}

	private boolean nextCharWillOverflow(float currentWidth, float maxWidth) {
		return maxWidth == RelativeDimension.UNBOUNDED ?
			false :
			currentWidth + charWidths[windowEnd] > maxWidth;
	}

}
