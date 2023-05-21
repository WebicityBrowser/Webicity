package com.github.webicitybrowser.spec.css.parser.selectors.selector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.imp.TokenStreamImp;
import com.github.webicitybrowser.spec.css.parser.tokens.HashToken;
import com.github.webicitybrowser.spec.css.parser.tokens.HashToken.HashTypeFlag;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.selectors.selector.IDSelector;

public class IDSelectorParserTest {

	private IDSelectorParser parser;
	
	@BeforeEach
	public void beforeEach() {
		parser = new IDSelectorParser();
	}
	
	@Test
	@DisplayName("Can parse ID selector")
	public void canParseIDSelector() {
		HashToken hashToken = HashToken.create("test", HashTypeFlag.ID);
		TokenStream tokenStream = new TokenStreamImp(new Token[] {
			hashToken
		});
		IDSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));
		Assertions.assertEquals("test", selector.getId());
	}
	
}
