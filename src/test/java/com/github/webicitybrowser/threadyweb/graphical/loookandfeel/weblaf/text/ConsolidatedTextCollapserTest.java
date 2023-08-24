package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.text;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.threadyweb.graphical.directive.WhiteSpaceCollapseDirective.WhiteSpaceCollapse;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedCollapsibleTextView;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedTextCollapser;

public class ConsolidatedTextCollapserTest {
	
	@Test
	@DisplayName("Collapsible spaces are collapsed in COLLAPSE mode")
	public void collapseModeCollapsesSpaces() {
		List<String> backingStrings = new ArrayList<>();
		backingStrings.add("Hello  World");
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		ConsolidatedTextCollapser.collapse(textView, WhiteSpaceCollapse.COLLAPSE);
		Assertions.assertEquals("Hello World", textView.toString());
	}

	@Test
	@DisplayName("Collapsible tabs are collapsed in COLLAPSE mode")
	public void collapseModeCollapsesTabs() {
		List<String> backingStrings = new ArrayList<>();
		backingStrings.add("Hello\t\t\tWorld");
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		ConsolidatedTextCollapser.collapse(textView, WhiteSpaceCollapse.COLLAPSE);
		Assertions.assertEquals("Hello World", textView.toString());
	}

	@Test
	@DisplayName("Collapsible segment breaks are collapsed to a space in COLLAPSE mode")
	public void collapseModeCollapsesSegmentBreaks() {
		List<String> backingStrings = new ArrayList<>();
		backingStrings.add("Hello\n\n\t\nWorld");
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		ConsolidatedTextCollapser.collapse(textView, WhiteSpaceCollapse.COLLAPSE);
		Assertions.assertEquals("Hello World", textView.toString());
	}

}
