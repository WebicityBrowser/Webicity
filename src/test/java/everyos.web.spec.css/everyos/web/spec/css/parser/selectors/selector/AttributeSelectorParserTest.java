package everyos.web.spec.css.parser.selectors.selector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import everyos.web.spec.css.parser.tokens.DelimToken;
import everyos.web.spec.css.parser.tokens.IdentToken;
import everyos.web.spec.css.parser.tokens.LSBrackToken;
import everyos.web.spec.css.parser.tokens.RSBrackToken;
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
		LSBrackToken leftBracketToken = Mockito.mock(LSBrackToken.class);
		RSBrackToken rightBracketToken = Mockito.mock(RSBrackToken.class);
		IdentToken attrNameToken = Mockito.mock(IdentToken.class);
		Mockito.when(attrNameToken.getValue()).thenReturn("attr");
		Token[] tokens = new Token[] {
			leftBracketToken, attrNameToken, rightBracketToken	
		};
		AttributeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 3));
		Assertions.assertEquals("attr", selector.getAttributeName());
		Assertions.assertEquals(AttributeSelectorOperation.NOT_EQUALS, selector.getOperation());
		Assertions.assertEquals("", selector.getComparisonValue());
	}
	
	@Test
	@DisplayName("Can parse attribute equals selector")
	public void canParseAttributeEqualsSelector() {
		LSBrackToken leftBracketToken = Mockito.mock(LSBrackToken.class);
		RSBrackToken rightBracketToken = Mockito.mock(RSBrackToken.class);
		IdentToken attrNameToken = Mockito.mock(IdentToken.class);
		Mockito.when(attrNameToken.getValue()).thenReturn("attr");
		IdentToken attrValueToken = Mockito.mock(IdentToken.class);
		Mockito.when(attrValueToken.getValue()).thenReturn("val");
		DelimToken equalsToken = Mockito.mock(DelimToken.class);
		Mockito.when(equalsToken.getValue()).thenReturn('=');
		Token[] tokens = new Token[] {
			leftBracketToken, attrNameToken, equalsToken, attrValueToken, rightBracketToken	
		};
		AttributeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 5));
		Assertions.assertEquals("attr", selector.getAttributeName());
		Assertions.assertEquals(AttributeSelectorOperation.EQUALS, selector.getOperation());
		Assertions.assertEquals("val", selector.getComparisonValue());
	}
	
	@Test
	@DisplayName("Can parse attribute comparison selector")
	public void canParseAttributeComparisonSelector() {
		LSBrackToken leftBracketToken = Mockito.mock(LSBrackToken.class);
		RSBrackToken rightBracketToken = Mockito.mock(RSBrackToken.class);
		IdentToken attrNameToken = Mockito.mock(IdentToken.class);
		Mockito.when(attrNameToken.getValue()).thenReturn("attr");
		StringToken attrValueToken = Mockito.mock(StringToken.class);
		Mockito.when(attrValueToken.getValue()).thenReturn("val");
		DelimToken equalsToken = Mockito.mock(DelimToken.class);
		Mockito.when(equalsToken.getValue()).thenReturn('=');
		DelimToken comparisonToken = Mockito.mock(DelimToken.class);
		Mockito.when(comparisonToken.getValue()).thenReturn('~');
		Token[] tokens = new Token[] {
			leftBracketToken, attrNameToken, comparisonToken, equalsToken, attrValueToken, rightBracketToken	
		};
		AttributeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 6));
		Assertions.assertEquals("attr", selector.getAttributeName());
		Assertions.assertEquals(AttributeSelectorOperation.NOT_EQUALS, selector.getOperation());
		Assertions.assertEquals("val", selector.getComparisonValue());
	}
	
}
