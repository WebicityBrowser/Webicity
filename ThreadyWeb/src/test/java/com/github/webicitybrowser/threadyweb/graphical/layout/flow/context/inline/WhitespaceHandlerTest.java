package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowTestUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextUnit;
import com.github.webicitybrowser.threadyweb.graphical.value.WhiteSpaceCollapse;

public class WhitespaceHandlerTest {
	
	@Test
	@DisplayName("Collapsible spaces are collapsed in COLLAPSE mode")
	public void collapseModeCollapsesSpaces() {
		LineBox lineBox = box(unit("Hello   World"));
		LineBox collapsed = WhitespaceHandler.collapse(lineBox, WhiteSpaceCollapse.COLLAPSE);
		Assertions.assertEquals("Hello World", text(collapsed));
	}

	@Test
	@DisplayName("Collapsible spaces and tabs around segment break are collapsed in COLLAPSE mode")
	public void collapseModeCollapsesSpacesAndTabsAroundSegmentBreak() {
		LineBox lineBox = box(unit("Hello \t  \n  World"));
		LineBox collapsed = WhitespaceHandler.collapse(lineBox, WhiteSpaceCollapse.COLLAPSE);
		// The segment break later gets replaced with a space
		Assertions.assertEquals("Hello World", text(collapsed));
	}

	@Test
	@DisplayName("Collapsible tabs are turned into spaces in COLLAPSE mode")
	public void collapseModeCollapsesTabs() {
		LineBox lineBox = box(unit("Hello\tWorld"));
		LineBox collapsed = WhitespaceHandler.collapse(lineBox, WhiteSpaceCollapse.COLLAPSE);
		Assertions.assertEquals("Hello World", text(collapsed));
	}

	@Test
	@DisplayName("Collapsible spaces are collapsed across multiple text units in COLLAPSE mode")
	public void collapseModeCollapsesSpacesAcrossMultipleTextUnits() {
		LineBox lineBox = box(unit("Hello  "), unit("   "), unit(" World"));
		LineBox collapsed = WhitespaceHandler.collapse(lineBox, WhiteSpaceCollapse.COLLAPSE);
		Assertions.assertEquals("Hello World", text(collapsed));
	}

	private TextUnit unit(String text) {
		return new TextUnit(null, null, text, FlowTestUtils.createTestFont(), 0);
	}

	private LineBox box(TextUnit... units) {
		LineBox box = new LineBox(null);
		for (TextUnit unit : units) {
			box.addEntry(new LineEntry.Text(unit));
		}

		return box;
	}

	private String text(LineBox box) {
		StringBuilder builder = new StringBuilder();
		for (LineEntry entry : box.entries()) {
			if (entry instanceof LineEntry.Text textEntry) {
				builder.append(textEntry.textUnit().text());
			}
		}

		return builder.toString();
	}

}
