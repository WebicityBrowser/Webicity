package everyos.web.spec.css.parser.selectors.combinator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import everyos.web.spec.css.parser.tokens.DelimToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.parser.tokens.WhitespaceToken;
import everyos.web.spec.css.selectors.combinator.ChildCombinator;
import everyos.web.spec.css.selectors.combinator.Combinator;
import everyos.web.spec.css.selectors.combinator.DescendantCombinator;
import everyos.web.spec.css.selectors.combinator.NextSiblingCombinator;
import everyos.web.spec.css.selectors.combinator.SubsequentSiblingCombinator;

public class CombinatorParserTest {

	private CombinatorParser parser;

	@BeforeEach
	public void beforeEach() {
		this.parser = new CombinatorParser();
	}
	
	@Test
	@DisplayName("Can parse descendant combinator")
	public void canParseDescendantCombinator() {
		WhitespaceToken spaceToken = Mockito.mock(WhitespaceToken.class);
		Token[] tokens = new Token[] { spaceToken };
		Combinator combinator = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 1));
		Assertions.assertInstanceOf(DescendantCombinator.class, combinator);
	}
	
	@Test
	@DisplayName("Can parse child combinator")
	public void canParseChildCombinator() {
		DelimToken delimToken = Mockito.mock(DelimToken.class);
		Mockito.when(delimToken.getValue()).thenReturn('>');
		Token[] tokens = new Token[] { delimToken };
		Combinator combinator = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 1));
		Assertions.assertInstanceOf(ChildCombinator.class, combinator);
	}
	
	@Test
	@DisplayName("Can parse next sibling combinator")
	public void canParseNextSiblingCombinator() {
		DelimToken delimToken = Mockito.mock(DelimToken.class);
		Mockito.when(delimToken.getValue()).thenReturn('+');
		Token[] tokens = new Token[] { delimToken };
		Combinator combinator = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 1));
		Assertions.assertInstanceOf(NextSiblingCombinator.class, combinator);
	}
	
	@Test
	@DisplayName("Can parse sebsequent sibling combinator")
	public void canParseSubsequentSiblingCombinator() {
		DelimToken delimToken = Mockito.mock(DelimToken.class);
		Mockito.when(delimToken.getValue()).thenReturn('~');
		Token[] tokens = new Token[] { delimToken };
		Combinator combinator = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 1));
		Assertions.assertInstanceOf(SubsequentSiblingCombinator.class, combinator);
	}
	
	@Test
	@DisplayName("Can parse space wrapped combinator")
	public void canParseSpaceWrappedCombinator() {
		WhitespaceToken spaceToken = Mockito.mock(WhitespaceToken.class);
		DelimToken delimToken = Mockito.mock(DelimToken.class);
		Mockito.when(delimToken.getValue()).thenReturn('~');
		Token[] tokens = new Token[] { spaceToken, delimToken, spaceToken };
		Combinator combinator = Assertions.assertDoesNotThrow(() -> parser.parse(tokens, 0, 3));
		Assertions.assertInstanceOf(SubsequentSiblingCombinator.class, combinator);
	}
	
}
