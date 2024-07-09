package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import java.util.LinkedList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.threadyweb.graphical.directive.text.LineBreakDirective.LineBreak;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.directive.WebTextDirectiveUtil;

public class LineSplitter {

	private final LineCursorTracker cursorTracker;
	private TextSplitter textSplitter;

	private final List<LineEntry> remainingEntries = new LinkedList<>();
	private final List<LineEntry> renderedEntries = new LinkedList<>();
	
	public LineSplitter(LineBox lineBox) {
		this.cursorTracker = new LineCursorTracker(lineBox.direction());
		remainingEntries.addAll(lineBox.entries());
	}

	public boolean isDone() {
		return remainingEntries.isEmpty() && (textSplitter == null || textSplitter.completed());
	}

	public LineBox nextLine() {
		LineBox finishedLine = new LineBox(cursorTracker.direction(), List.copyOf(renderedEntries), null);
		List<LineEntry> split = finishedLine.splitRemainingSections();

		cursorTracker.reset();
		renderedEntries.clear();
		// TODO: Account for the end sections in sizing
		renderedEntries.addAll(split);

		return finishedLine;
	}

	public void splitFits(float inlineSize) {
		boolean isFirst = true;
		while ((!remainingEntries.isEmpty() || textSplitter != null)) {
			setupTextSplitter();
			if (textSplitter != null) {
				TextResponse response = addTextFromSplitter(inlineSize, isFirst);
				if (response == TextResponse.TEXT_DOES_NOT_FIT) break;
				isFirst = isFirst && response == TextResponse.TEXT_EMPTY;
			} else if (
				!remainingEntries.isEmpty()
				&& (!cursorTracker.addWillOverflowLine(remainingEntries.get(0).getSize(), inlineSize)
				|| isFirst)
			) {
				renderNext();
				isFirst = false;
			} else break;
		}

		removeEmptyEnd();
	}

	private void renderNext() {
		renderedEntries.add(remainingEntries.get(0));
		cursorTracker.add(remainingEntries.remove(0).getSize());
	}


	private void setupTextSplitter() {
		while (
			(textSplitter == null || textSplitter.completed())
			&& !remainingEntries.isEmpty()
			&& remainingEntries.get(0) instanceof LineEntry.Text textEntry
		) {
			LineBreak lineBreak =  WebTextDirectiveUtil.getLineBreak(textEntry.textUnit().box().styleDirectives());
			textSplitter = new TextSplitter(textEntry.textUnit(), lineBreak);
			remainingEntries.remove(0);
		}
		if (textSplitter != null && textSplitter.completed()) {
			textSplitter = null;
		}
		
	}

	// TODO: We assume spaces cannot have repeat occurences - in the future, that may not be the case
	private TextResponse addTextFromSplitter(float inlineSize, boolean forceFit) {
		float remainingInlineSize = inlineSize == RelativeDimension.UNBOUNDED ?
			RelativeDimension.UNBOUNDED :
			inlineSize - cursorTracker.getNextPosition().run();
		TextUnit textUnit = textSplitter.getFittingText(remainingInlineSize, forceFit);

		// TODO: Would isBlank() work here?
		if (forceFit && textUnit.text().replace(" ", "").isEmpty()) return TextResponse.TEXT_EMPTY;
		if (textUnit == null) return TextResponse.TEXT_DOES_NOT_FIT;

		if (forceFit && textUnit.text().startsWith(" ")) {
			textUnit = InlineTextRenderer.renderNewText(textUnit, textUnit.text().substring(1));
		}
		cursorTracker.add(textUnit.fitSize());
		renderedEntries.add(new LineEntry.Text(textUnit));
		
		return TextResponse.TEXT_FITS;
	}

	private void removeEmptyEnd() {
		for (int i = renderedEntries.size() - 1; i >= 0; i--) {
			if (renderedEntries.get(i) instanceof LineEntry.Text textEntry) {
				TextUnit textUnit = textEntry.textUnit();
				if (textUnit.text().endsWith(" ")) {
					textUnit = InlineTextRenderer.renderNewText(
						textUnit, textUnit.text().substring(0, textUnit.text().length() - 1));
					renderedEntries.set(i, new LineEntry.Text(textUnit));
				}
				if (!textUnit.text().isEmpty()) break;
			}
		}
	}

	private static enum TextResponse {
		TEXT_DOES_NOT_FIT, TEXT_FITS, TEXT_EMPTY
	}

}
