package com.github.webicitybrowser.spec.css.parser.selectors.selector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.imp.TokenStreamImp;
import com.github.webicitybrowser.spec.css.parser.tokens.ColonToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.selectors.selector.psuedo.PsuedoSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.psuedo.RootSelector;

public class PsuedoSelectorParserTest {

	private PsuedoSelectorParser parser;

	@BeforeEach
	public void beforeEach() {
		parser = new PsuedoSelectorParser();
	}

	@Test
	@DisplayName("Can parse root psuedo selector")
	public void canParseRootPsuedoSelector() {
		TokenStream tokenStream = new TokenStreamImp(new Token[] {
			new ColonToken() {}, (IdentToken) () -> "root"
		});
		PsuedoSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));

		Assertions.assertEquals("root", selector.getType());
		Assertions.assertInstanceOf(RootSelector.class, selector);
	}

}
