package com.github.webicitybrowser.spec.css.parser.selectors.selector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.imp.TokenStreamImp;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector.AttributeSelectorOperation;

public class ClassSelectorParserTest {

	private ClassSelectorParser parser;
	
	@BeforeEach
	public void beforeEach() {
		parser = new ClassSelectorParser();
	}
	
	@Test
	@DisplayName("Can parse class selector")
	public void canParseClassSelector() {
		DelimToken delimToken = Mockito.mock(DelimToken.class);
		Mockito.when(delimToken.value()).thenReturn((int) '.');
		IdentToken identToken = Mockito.mock(IdentToken.class);
		Mockito.when(identToken.value()).thenReturn("test");
		TokenStream tokenStream = new TokenStreamImp(new Token[] {
			delimToken, identToken	
		});
		AttributeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));
		Assertions.assertEquals("class", selector.attributeName().name());
		Assertions.assertEquals(AttributeSelectorOperation.ONE_OF, selector.operation());
		Assertions.assertEquals("test", selector.comparisonValue());
	}
	
}
