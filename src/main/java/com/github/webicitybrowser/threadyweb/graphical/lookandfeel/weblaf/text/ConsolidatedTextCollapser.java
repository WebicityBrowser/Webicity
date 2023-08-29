package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text;

import com.github.webicitybrowser.threadyweb.graphical.directive.WhiteSpaceCollapseDirective.WhiteSpaceCollapse;

public final class ConsolidatedTextCollapser {

	private static final char SEGMENT_BREAK = '\n';
	
	private ConsolidatedTextCollapser() {}

	public static void collapse(ConsolidatedCollapsibleTextView textView, WhiteSpaceCollapse collapseMode) {
		if (collapseMode == WhiteSpaceCollapse.COLLAPSE || collapseMode == WhiteSpaceCollapse.PRESERVE_BREAKS) {
			collapseSpacesTabs(textView);
		}
		textView.restart();
		if (collapseMode == WhiteSpaceCollapse.COLLAPSE) {
			collapseBreaks(textView);
		}
	}

	private static void collapseSpacesTabs(ConsolidatedCollapsibleTextView textView) {
		while (!textView.atEnd()) {
			replaceTabWithSpace(textView);
			removeExtraSpacesTabs(textView);
			removePreviousCharIfIsChar(textView, ' ', SEGMENT_BREAK);
			removePreviousCharIfIsChar(textView, SEGMENT_BREAK, ' ');
			textView.advance();
		}
	}

	private static void replaceTabWithSpace(ConsolidatedCollapsibleTextView textView) {
		int codepoint = textView.getCurrentCodepoint();
		if (codepoint == '\t') {
			textView.replace(' ');
			textView.regress();
		}
	}

	private static void removeExtraSpacesTabs(ConsolidatedCollapsibleTextView textView) {
		int codepoint = textView.getCurrentCodepoint();
		if (codepoint == ' ') {
			textView.advance();
			while (
				!textView.atEnd() && (
				textView.getCurrentCodepoint() == ' ' ||
				textView.getCurrentCodepoint() == '\t')
			) {
				textView.delete();
			}
			textView.regress();
		}
	}

	private static void removePreviousCharIfIsChar(ConsolidatedCollapsibleTextView textView, int prev, int next) {
		if (textView.atStart()) {
			return;
		}

		int codepoint = textView.getCurrentCodepoint();
		if (codepoint == next) {
			textView.regress();
			if (textView.getCurrentCodepoint() == prev) {
				textView.delete();
			} else {
				textView.advance();
			}
		}
	}

	private static void collapseBreaks(ConsolidatedCollapsibleTextView textView) {
		while (!textView.atEnd()) {
			int codepoint = textView.getCurrentCodepoint();
			if (codepoint == SEGMENT_BREAK) {
				textView.advance();
				while (!textView.atEnd() && textView.getCurrentCodepoint() == SEGMENT_BREAK) {
					textView.delete();
				}
				textView.regress();
				textView.replace(' ');
			} else {
				textView.advance();
			}
		}
	}

}
