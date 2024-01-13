package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.text;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedCollapsibleTextView;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedTextCollapser;
import com.github.webicitybrowser.threadyweb.graphical.value.WhiteSpaceCollapse;

public class ConsolidatedTextCollapserTest {
	
	@Test
	@DisplayName("Collapsible spaces are collapsed in COLLAPSE mode")
	public void collapseModeCollapsesSpaces() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder("Hello  World"));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		ConsolidatedTextCollapser.collapse(textView, WhiteSpaceCollapse.COLLAPSE);
		Assertions.assertEquals("Hello World", textView.toString());
	}

	@Test
	@DisplayName("Collapsible tabs are collapsed in COLLAPSE mode")
	public void collapseModeCollapsesTabs() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder("Hello\t\t\tWorld"));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		ConsolidatedTextCollapser.collapse(textView, WhiteSpaceCollapse.COLLAPSE);
		Assertions.assertEquals("Hello World", textView.toString());
	}

	@Test
	@DisplayName("Collapsible segment breaks are collapsed to a space in COLLAPSE mode")
	public void collapseModeCollapsesSegmentBreaks() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder("Hello\n\n\t\nWorld"));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		ConsolidatedTextCollapser.collapse(textView, WhiteSpaceCollapse.COLLAPSE);
		Assertions.assertEquals("Hello World", textView.toString());
	}

	@Test
	@DisplayName("String of just tabs and newlines collapsed in COLLAPSE mode")
	public void collapseModeCollapsesJustTabsAndNewlines() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder("\n\t"));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		ConsolidatedTextCollapser.collapse(textView, WhiteSpaceCollapse.COLLAPSE);
		Assertions.assertEquals(" ", textView.toString());
	}

}
