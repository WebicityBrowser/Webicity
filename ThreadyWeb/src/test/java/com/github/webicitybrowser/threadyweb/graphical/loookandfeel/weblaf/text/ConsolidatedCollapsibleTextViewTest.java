package com.github.webicitybrowser.threadyweb.graphical.loookandfeel.weblaf.text;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedCollapsibleTextView;

public class ConsolidatedCollapsibleTextViewTest {

	@Test
	@DisplayName("Cursor at both start and end of empty string")
	public void cursorAtBothStartAndEndOfEmptyString() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder(""));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		Assertions.assertTrue(textView.atStart());
		Assertions.assertTrue(textView.atEnd());
	}
	
	@Test
	@DisplayName("Cursor at start of small string")
	public void cursorAtStartOfSmallString() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder("a"));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		Assertions.assertTrue(textView.atStart());
		Assertions.assertFalse(textView.atEnd());
	}
	
	@Test
	@DisplayName("Cursor at end of small string after advance")
	public void cursorAtEndOfSmallStringAfterAdvance() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder("a"));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		textView.advance();
		Assertions.assertFalse(textView.atStart());
		Assertions.assertTrue(textView.atEnd());
	}
	
	@Test
	@DisplayName("Can regress on small string")
	public void canRegressOnSmallString() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder("a"));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		textView.advance();
		textView.regress();
		Assertions.assertTrue(textView.atStart());
		Assertions.assertFalse(textView.atEnd());
	}
	
	@Test
	@DisplayName("Can read codepoint at cursor in single string")
	public void canReadCodepointAtCursorInSingleString() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder("a"));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		Assertions.assertEquals('a', textView.getCurrentCodepoint());
	}
	
	@Test
	@DisplayName("Can read codepoint at cursor in multistring")
	public void canReadCodepointAtCursorInMultistring() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder("a"));
		backingStrings.add(new StringBuilder("b"));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		textView.advance();
		Assertions.assertEquals('b', textView.getCurrentCodepoint());
	}
	
	@Test
	@DisplayName("Can read codepoint at cursor in multistring after regress")
	public void canReadCodepointAtCursorInMultistringAfterRegress() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder("a"));
		backingStrings.add(new StringBuilder("b"));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		textView.advance();
		textView.regress();
		Assertions.assertEquals('a', textView.getCurrentCodepoint());
	}
	
	@Test
	@DisplayName("Can replace codepoint in multistring")
	public void canReplaceCodepointInMultistring() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder("a"));
		backingStrings.add(new StringBuilder("b"));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		textView.replace('c');
		Assertions.assertEquals('b', textView.getCurrentCodepoint());
		textView.regress();
		Assertions.assertEquals('c', textView.getCurrentCodepoint());
	}
	
	@Test
	@DisplayName("Can delete codepoint in multistring")
	public void canDeleteCodepointInMultistring() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder("a"));
		backingStrings.add(new StringBuilder("b"));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		textView.delete();
		Assertions.assertEquals('b', textView.getCurrentCodepoint());
		Assertions.assertTrue(textView.atStart());
	}

	@Test
	@DisplayName("Can move pointer to start")
	public void canMovePointerToStart() {
		List<StringBuilder> backingStrings = new ArrayList<>();
		backingStrings.add(new StringBuilder("a"));
		backingStrings.add(new StringBuilder("b"));
		ConsolidatedCollapsibleTextView textView = ConsolidatedCollapsibleTextView.create(backingStrings);
		textView.advance();
		textView.advance();
		Assertions.assertTrue(textView.atEnd());
		textView.restart();
		Assertions.assertTrue(textView.atStart());
		Assertions.assertFalse(textView.atEnd());
	}
	
}
