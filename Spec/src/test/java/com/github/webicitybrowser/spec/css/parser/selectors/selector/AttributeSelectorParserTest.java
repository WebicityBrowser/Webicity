package com.github.webicitybrowser.spec.css.parser.selectors.selector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.imp.TokenStreamImp;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.StringToken;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector.AttributeSelectorOperation;

public class AttributeSelectorParserTest {

	private AttributeSelectorParser parser;
	
	@BeforeEach
	public void beforeEach() {
		parser = new AttributeSelectorParser();
	}
	
	@Test
	@DisplayName("Can parse attribute present selector")
	public void canParseAttributePresentSelector() {
		TokenStream tokenStream = new TokenStreamImp(new Token[] {
			new IdentToken("attr")
		});
		AttributeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));
		Assertions.assertEquals("attr", selector.getAttributeName().getName());
		Assertions.assertEquals(AttributeSelectorOperation.PRESENT, selector.getOperation());
		Assertions.assertEquals("", selector.getComparisonValue());
	}
	
	@Test
	@DisplayName("Can parse attribute equals selector")
	public void canParseAttributeEqualsSelector() {
		TokenStream tokenStream = new TokenStreamImp(new Token[] {
			new IdentToken("attr"), new DelimToken((int) '='), new IdentToken("val")
		});
		AttributeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));
		Assertions.assertEquals("attr", selector.getAttributeName().getName());
		Assertions.assertEquals(AttributeSelectorOperation.EQUALS, selector.getOperation());
		Assertions.assertEquals("val", selector.getComparisonValue());
	}
	
	@Test
	@DisplayName("Can parse attribute comparison selector")
	public void canParseAttributeComparisonSelector() {
		TokenStream tokenStream = new TokenStreamImp(new Token[] {
			new IdentToken("attr"), new DelimToken((int) '~'), new DelimToken((int) '='), new StringToken("val")
		});
		AttributeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));
		Assertions.assertEquals("attr", selector.getAttributeName().getName());
		Assertions.assertEquals(AttributeSelectorOperation.ONE_OF, selector.getOperation());
		Assertions.assertEquals("val", selector.getComparisonValue());
	}
	
}
