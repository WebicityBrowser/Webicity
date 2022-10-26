package everyos.web.spec.css.parser.selectors.selector;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import everyos.web.spec.css.parser.tokens.IdentToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.selectors.selector.ElementNameSelector;

public class ElementNameSelectorTest {

	@Test
	@DisplayName("Can parse element name selector")
	public void canParseElementNameSelector() {
		IdentToken nameToken = Mockito.mock(IdentToken.class);
		Mockito.when(nameToken.getValue()).thenReturn("name");
		Token[] tokens = new Token[] { nameToken };
		ElementNameSelectorParser parser = new ElementNameSelectorParser();
		ElementNameSelector selector = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 1));
		Assertions.assertEquals("name", selector.getElementName());
	}
	
}
