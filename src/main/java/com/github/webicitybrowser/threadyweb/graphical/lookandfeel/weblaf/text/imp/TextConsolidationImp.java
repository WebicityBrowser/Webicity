package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.imp;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedCollapsibleTextView;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidation;

public class TextConsolidationImp implements TextConsolidation {
	
	private static final Logger logger = LoggerFactory.getLogger(TextConsolidationImp.class);

	private final List<String> textLines = new ArrayList<>();
	private final List<Object> textOwners = new ArrayList<>();
	
	private int readCursor = 0;
	
	@Override
	public void addText(Object textOwner, String text) {
		textLines.add(text);
		textOwners.add(textOwner);
	}

	@Override
	public ConsolidatedCollapsibleTextView getTextView() {
		return new ConsolidatedCollapsibleTextViewImp(textLines);
	}

	@Override
	public String readNextText(Object textOwner) {
		ensureReadOrderConsistent(textOwner);
		
		String text = textLines.get(readCursor);
		readCursor++;
		
		return text;
	}

	private void ensureReadOrderConsistent(Object textOwner) {
		if (textOwner != textOwners.get(readCursor)) {
			logger.error(
				"Consistency check failed! Input was given by " + textOwners.get(readCursor) + " but read by " + textOwner + "!" +
				"To prevent further issues, the renderer will crash.");
			throw new RuntimeException("fed/read mismatch (consistency check failed)");
		}
	}
	
}
