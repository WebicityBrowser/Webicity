package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LineBreakDirective.LineBreak;


public class TextSplitter {
	
	private final String text;
	private final Font2D font;
	private final float letterSpacing;
	private final LineBreak lineBreak;
	private final float[] charWidths;

	private int windowEnd = 0;

	public TextSplitter(String text, Font2D font, LineBreak lineBreak, float letterSpacing) {
		this.text = text;
		this.font = font;
		this.letterSpacing = letterSpacing;
		this.lineBreak = lineBreak;
		this.charWidths = generateCharWidths();
	}

	public String getFittingText(boolean forceFit, float maxWidth) {
		if (completed()) {
			throw new IllegalStateException("Already completed");
		}

		float currentWidth = 0;
		int windowStart = windowEnd;
		int spacePosition = -1;
		while (!completed() && ((forceFit && currentWidth == 0) || !nextCharWillOverflow(currentWidth, maxWidth))) {
			if (currentWidth != 0) {
				currentWidth += letterSpacing;
			}
			
			currentWidth += charWidths[windowEnd];
			windowEnd++;

			if (windowEnd >= text.length() || text.charAt(windowEnd) == ' ') {
				spacePosition = windowEnd;
			}
		}

		if (!forceFit && spacePosition == -1 && lineBreak != LineBreak.ANYWHERE) {
			windowEnd = windowStart;
			return null;
		}

		if (windowStart == windowEnd) {
			return null;
		}

		if (spacePosition == -1 || lineBreak == LineBreak.ANYWHERE) spacePosition = windowEnd;
		windowEnd = spacePosition;

		return text.substring(windowStart, spacePosition);
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
