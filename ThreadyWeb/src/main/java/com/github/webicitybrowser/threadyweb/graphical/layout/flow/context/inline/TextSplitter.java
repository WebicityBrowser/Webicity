package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LineBreakDirective.LineBreak;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextUnit;

public class TextSplitter {

	private final TextUnit originalTextUnit;
	private final LineBreak lineBreak;
	private final float[] charWidths;

	private int windowEnd = 0;
	
	public TextSplitter(TextUnit textUnit, LineBreak lineBreak) {
		this.originalTextUnit = textUnit;
		this.lineBreak = lineBreak;
		this.charWidths = generateCharWidths();
	}

	public TextUnit getFittingText(float inlineSize, boolean forceFit) {
		if (completed()) {
			throw new IllegalStateException("Already completed");
		}

		String text = originalTextUnit.text();
		float letterSpacing = originalTextUnit.letterSpacing();

		float currentWidth = 0;
		int windowStart = windowEnd;
		int spacePosition = -1;
		while (
			!completed()
			&& ((forceFit && !canExitForceFit(currentWidth, spacePosition))
			|| !nextCharWillOverflow(currentWidth, inlineSize))
		) {
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

		String newText = text.substring(windowStart, spacePosition);
		return InlineTextRenderer.renderNewText(originalTextUnit, newText);
	}

	private boolean canExitForceFit(float currentWidth, int spacePosition) {
		switch (lineBreak) {
			case ANYWHERE:
				return currentWidth != 0;
			default:
				return currentWidth != 0 && spacePosition != -1;
		}
	}

	public boolean completed() {
		return windowEnd >= charWidths.length;
	}

	private float[] generateCharWidths() {
		String text = originalTextUnit.text();
		FontMetrics metrics = originalTextUnit.font().getMetrics();
		float[] sizes = new float[text.length()];
		for (int i = 0; i < sizes.length; i++) {
			sizes[i] = metrics.getCharacterWidth(text.codePointAt(i));
		}
		
		return sizes;
	}

	private boolean nextCharWillOverflow(float currentWidth, float inlineSize) {
		return inlineSize == RelativeDimension.UNBOUNDED ?
			false :
			currentWidth + charWidths[windowEnd] > inlineSize;
	}

}
