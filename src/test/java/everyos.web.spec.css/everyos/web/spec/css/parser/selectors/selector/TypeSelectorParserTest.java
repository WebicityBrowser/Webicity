package everyos.web.spec.css.parser.selectors.selector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import everyos.web.spec.css.parser.tokens.IdentToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.selectors.selector.ElementNameSelector;
import everyos.web.spec.css.selectors.selector.TypeSelector;

public class TypeSelectorParserTest {

	private TypeSelectorParser parser;
	
	@BeforeEach
	public void beforeEach() {
		parser = new TypeSelectorParser();
	}
	
	@Test
	@DisplayName("Can delegate to element name selector")
	public void canDelegateToElementNameSelector() {
		IdentToken nameToken = Mockito.mock(IdentToken.class);
		Mockito.when(nameToken.getValue()).thenReturn("name");
		Token[] tokens = new Token[] { nameToken };
		TypeSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 1));
		Assertions.assertEquals(TypeSelector.DEFAULT_NAMESPACE, selector.getNamespace());
		Assertions.assertInstanceOf(ElementNameSelector.class, selector.getInnerSelector());
	}
	
}
