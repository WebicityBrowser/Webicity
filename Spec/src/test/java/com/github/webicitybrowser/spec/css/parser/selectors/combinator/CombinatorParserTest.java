package com.github.webicitybrowser.spec.css.parser.selectors.combinator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.imp.TokenStreamImp;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.EOFToken;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.css.selectors.combinator.ChildCombinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.Combinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.DescendantCombinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.NextSiblingCombinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.SubsequentSiblingCombinator;

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
		TokenStream tokenStream = new TokenStreamImp(new Token[] { spaceToken });
		Combinator combinator = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));
		Assertions.assertInstanceOf(DescendantCombinator.class, combinator);
		Assertions.assertInstanceOf(EOFToken.class, tokenStream.read());
	}
	
	@Test
	@DisplayName("Can parse child combinator")
	public void canParseChildCombinator() {
		DelimToken delimToken = Mockito.mock(DelimToken.class);
		Mockito.when(delimToken.getValue()).thenReturn((int) '>');
		TokenStream tokenStream = new TokenStreamImp(new Token[] { delimToken });
		Combinator combinator = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));
		Assertions.assertInstanceOf(ChildCombinator.class, combinator);
		Assertions.assertInstanceOf(EOFToken.class, tokenStream.read());
	}
	
	@Test
	@DisplayName("Can parse next sibling combinator")
	public void canParseNextSiblingCombinator() {
		DelimToken delimToken = Mockito.mock(DelimToken.class);
		Mockito.when(delimToken.getValue()).thenReturn((int) '+');
		TokenStream tokenStream = new TokenStreamImp(new Token[] { delimToken });
		Combinator combinator = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));
		Assertions.assertInstanceOf(NextSiblingCombinator.class, combinator);
		Assertions.assertInstanceOf(EOFToken.class, tokenStream.read());
	}
	
	@Test
	@DisplayName("Can parse sebsequent sibling combinator")
	public void canParseSubsequentSiblingCombinator() {
		DelimToken delimToken = Mockito.mock(DelimToken.class);
		Mockito.when(delimToken.getValue()).thenReturn((int) '~');
		TokenStream tokenStream = new TokenStreamImp(new Token[] { delimToken });
		Combinator combinator = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));
		Assertions.assertInstanceOf(SubsequentSiblingCombinator.class, combinator);
		Assertions.assertInstanceOf(EOFToken.class, tokenStream.read());
	}
	
	@Test
	@DisplayName("Can parse space wrapped combinator")
	public void canParseSpaceWrappedCombinator() {
		WhitespaceToken spaceToken = Mockito.mock(WhitespaceToken.class);
		DelimToken delimToken = Mockito.mock(DelimToken.class);
		Mockito.when(delimToken.getValue()).thenReturn((int) '~');
		TokenStream tokenStream = new TokenStreamImp(new Token[] { spaceToken, delimToken, spaceToken });
		Combinator combinator = Assertions.assertDoesNotThrow(() -> parser.parse(tokenStream));
		Assertions.assertInstanceOf(SubsequentSiblingCombinator.class, combinator);
		Assertions.assertInstanceOf(EOFToken.class, tokenStream.read());
	}
	
}
