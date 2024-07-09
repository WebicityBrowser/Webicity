package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextUnit;
import com.github.webicitybrowser.threadyweb.graphical.value.WhiteSpaceCollapse;

public final class WhitespaceHandler {

	private WhitespaceHandler() {}

	public static LineBox collapse(LineBox lineBox, WhiteSpaceCollapse whiteSpaceCollapse) {
		List<LineEntry> entries = new ArrayList<>(lineBox.entries());
		boolean allowSpace = true;
		for (int i = 0; i < entries.size(); i++) {
			LineEntry entry = entries.get(i);
			if (entry instanceof LineEntry.Text textEntry) {
				String collapsed = collapseText(textEntry.textUnit().text(), whiteSpaceCollapse);
				if (whiteSpaceCollapse == WhiteSpaceCollapse.COLLAPSE) {
					if (!allowSpace && collapsed.startsWith(" ")) {
						collapsed = collapsed.substring(1);
					}
					allowSpace = (collapsed.isEmpty() && allowSpace) || !(collapsed.endsWith(" ") || collapsed.isEmpty());
				}
				entries.set(i, createTextEntry(textEntry, collapsed));
			}
		}

		return new LineBox(lineBox.direction(), entries, null);
	}

	private static String collapseText(String text, WhiteSpaceCollapse whiteSpaceCollapse) {
		StringBuilder builder = new StringBuilder(text);
		if (whiteSpaceCollapse == WhiteSpaceCollapse.COLLAPSE) {
			// Without using regex, find segment breaks and delete spaces before them
			collapseAroundSegments(builder);
			collapseCharToSpace(builder, '\n');
			collapseCharToSpace(builder, '\t');
			collapseSpaces(builder);
		}

		return builder.toString();
	}

	private static LineEntry createTextEntry(LineEntry.Text originalEntry, String collapseText) {
		TextUnit old = originalEntry.textUnit();
		return new LineEntry.Text(InlineTextRenderer.renderNewText(old, collapseText));
	}

	private static void collapseAroundSegments(StringBuilder builder) {
		for (int i = 0; i < builder.length(); i++) {
			if (builder.charAt(i) == '\n') {
				while (
					i - 1 >= 0
					&& (i > 0 && (builder.codePointAt(i - 1) == ' ' || builder.codePointAt(i - 1) == '\t'))) {
					builder.deleteCharAt(i - 1);
					i--;
				}
				while (
					i + 1 < builder.length()
					&& (builder.codePointAt(i + 1) == ' ' || builder.codePointAt(i + 1) == '\t')) {
					builder.deleteCharAt(i + 1);
				}
			}
		}
	}

	private static void collapseCharToSpace(StringBuilder builder, int ch) {
		for (int i = 0; i < builder.length(); i++) {
			if (builder.codePointAt(i) == ch) {
				builder.setCharAt(i, ' ');
			}
		}
	}

	private static void collapseSpaces(StringBuilder builder) {
		for (int i = 0; i < builder.length(); i++) {
			if (builder.codePointAt(i) == ' ') {
				int j = i + 1;
				while (j < builder.length() && builder.codePointAt(j) == ' ') {
					builder.deleteCharAt(j);
				}
			}
		}
	}

}
