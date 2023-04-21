package com.github.webicitybrowser.spec.css.parser.selectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelector;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.css.selectors.selector.TypeSelector;

public class ComplexSelectorParserTest {

	private ComplexSelectorParser complexSelectorParser;

	@BeforeEach
	public void beforeEach() {
		this.complexSelectorParser = new ComplexSelectorParser();
	}
	
	@Test
	@DisplayName("Empty input returns no complex selectors")
	public void emptyInputReturnsNoComplexSelectors() {
		TokenLike[] tokens = new TokenLike[0];
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens);
		Assertions.assertArrayEquals(new ComplexSelector[0], selectors);
	}
	
	@Test
	@DisplayName("Ident token creates type selector")
	public void identTokenCreatesTypeSelector() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "hi"
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens);
		Assertions.assertEquals(1, selectors.length);
		ComplexSelectorPart[] parts = selectors[0].getParts();
		Assertions.assertEquals(1, parts.length);
		Assertions.assertInstanceOf(TypeSelector.class, parts[0]);
		TypeSelector selector = (TypeSelector) parts[0];
		Assertions.assertEquals("hi", selector.getQualifiedName().getName());
	}
	
	@Test
	@DisplayName("Two subsequent ident tokens discarded")
	public void twoSubsequentIdentTokensDiscarded() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "hi", (IdentToken) () -> "hi"
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens);
		Assertions.assertEquals(0, selectors.length);
	}
	
	@Test
	@DisplayName("Two delimited ident tokens create type selectors")
	public void twoDelimitedIdentTokensCreateTypeSelectors() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "hi", new CommaToken() {}, (IdentToken) () -> "bye"
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens);
		Assertions.assertEquals(2, selectors.length);
		{
			ComplexSelectorPart[] parts = selectors[0].getParts();
			Assertions.assertEquals(1, parts.length);
			Assertions.assertInstanceOf(TypeSelector.class, parts[0]);
			TypeSelector selector = (TypeSelector) parts[0];
			Assertions.assertEquals("hi", selector.getQualifiedName().getName());
		} {
			ComplexSelectorPart[] parts = selectors[1].getParts();
			Assertions.assertEquals(1, parts.length);
			Assertions.assertInstanceOf(TypeSelector.class, parts[0]);
			TypeSelector selector = (TypeSelector) parts[0];
			Assertions.assertEquals("bye", selector.getQualifiedName().getName());
		}
	}
	
	@Test
	@DisplayName("Ident token with whitespace creates type selector")
	public void identTokenWithWhitespaceCreatesTypeSelector() {
		TokenLike[] tokens = new TokenLike[] {
			new WhitespaceToken() {}, (IdentToken) () -> "hi", new WhitespaceToken() {}
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens);
		Assertions.assertEquals(1, selectors.length);
		ComplexSelectorPart[] parts = selectors[0].getParts();
		Assertions.assertEquals(1, parts.length);
		Assertions.assertInstanceOf(TypeSelector.class, parts[0]);
		TypeSelector selector = (TypeSelector) parts[0];
		Assertions.assertEquals("hi", selector.getQualifiedName().getName());
	}
	
}
