package everyos.web.spec.css.parser.selectors.selector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import everyos.web.spec.css.parser.TokenStream;
import everyos.web.spec.css.parser.imp.TokenStreamImp;
import everyos.web.spec.css.parser.tokens.DelimToken;
import everyos.web.spec.css.parser.tokens.IdentToken;
import everyos.web.spec.css.parser.tokens.LSBracketToken;
import everyos.web.spec.css.parser.tokens.RSBracketToken;
import everyos.web.spec.css.parser.tokens.StringToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.selectors.selector.AttributeSelector;
import everyos.web.spec.css.selectors.selector.AttributeSelector.AttributeSelectorOperation;

public class AttributeSelectorParserTest {

	private AttributeSelectorParser parser;
	
	@BeforeEach
	public void beforeEach() {
		parser = new AttributeSelectorParser();
	}
	
	@Test
	@DisplayName("Can parse attribute present selector")
	public void canParseAttributePresentSelector() {
		LSBracketToken leftBracketToken = Mockito.mock(LSBracketToken.class);
		RSBracketToken rightBracketToken = Mockito.mock(RSBracketToken.class);
		IdentToken attrNameToken = Mockito.mock(IdentToken.class);
		Mockito.when(attrNameToken.getValue()).thenReturn("attr");
		TokenStream tokenStream = new TokenStreamImp(new Token[] {
			leftBracketToken, attrNameToken, rightBracketToken	
		});
		AttributeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));
		Assertions.assertEquals("attr", selector.getAttributeName().getName());
		Assertions.assertEquals(AttributeSelectorOperation.PRESENT, selector.getOperation());
		Assertions.assertEquals("", selector.getComparisonValue());
	}
	
	@Test
	@DisplayName("Can parse attribute equals selector")
	public void canParseAttributeEqualsSelector() {
		LSBracketToken leftBracketToken = Mockito.mock(LSBracketToken.class);
		RSBracketToken rightBracketToken = Mockito.mock(RSBracketToken.class);
		IdentToken attrNameToken = Mockito.mock(IdentToken.class);
		Mockito.when(attrNameToken.getValue()).thenReturn("attr");
		IdentToken attrValueToken = Mockito.mock(IdentToken.class);
		Mockito.when(attrValueToken.getValue()).thenReturn("val");
		DelimToken equalsToken = Mockito.mock(DelimToken.class);
		Mockito.when(equalsToken.getValue()).thenReturn((int) '=');
		TokenStream tokenStream = new TokenStreamImp(new Token[] {
			leftBracketToken, attrNameToken, equalsToken, attrValueToken, rightBracketToken	
		});
		AttributeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));
		Assertions.assertEquals("attr", selector.getAttributeName().getName());
		Assertions.assertEquals(AttributeSelectorOperation.EQUALS, selector.getOperation());
		Assertions.assertEquals("val", selector.getComparisonValue());
	}
	
	@Test
	@DisplayName("Can parse attribute comparison selector")
	public void canParseAttributeComparisonSelector() {
		LSBracketToken leftBracketToken = Mockito.mock(LSBracketToken.class);
		RSBracketToken rightBracketToken = Mockito.mock(RSBracketToken.class);
		IdentToken attrNameToken = Mockito.mock(IdentToken.class);
		Mockito.when(attrNameToken.getValue()).thenReturn("attr");
		StringToken attrValueToken = Mockito.mock(StringToken.class);
		Mockito.when(attrValueToken.getValue()).thenReturn("val");
		DelimToken equalsToken = Mockito.mock(DelimToken.class);
		Mockito.when(equalsToken.getValue()).thenReturn((int) '=');
		DelimToken comparisonToken = Mockito.mock(DelimToken.class);
		Mockito.when(comparisonToken.getValue()).thenReturn((int) '~');
		TokenStream tokenStream = new TokenStreamImp(new Token[] {
			leftBracketToken, attrNameToken, comparisonToken, equalsToken, attrValueToken, rightBracketToken	
		});
		AttributeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));
		Assertions.assertEquals("attr", selector.getAttributeName().getName());
		Assertions.assertEquals(AttributeSelectorOperation.ONE_OF, selector.getOperation());
		Assertions.assertEquals("val", selector.getComparisonValue());
	}
	
}
