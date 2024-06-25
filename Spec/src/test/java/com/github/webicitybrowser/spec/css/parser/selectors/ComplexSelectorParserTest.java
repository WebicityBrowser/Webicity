package com.github.webicitybrowser.spec.css.parser.selectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.tokens.ColonToken;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.HashToken;
import com.github.webicitybrowser.spec.css.parser.tokens.HashToken.HashTypeFlag;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LSBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RSBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelector;
import com.github.webicitybrowser.spec.css.selectors.ComplexSelectorPart;
import com.github.webicitybrowser.spec.css.selectors.combinator.ChildCombinator;
import com.github.webicitybrowser.spec.css.selectors.combinator.DescendantCombinator;
import com.github.webicitybrowser.spec.css.selectors.selector.AttributeSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.IDSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.TypeSelector;
import com.github.webicitybrowser.spec.css.selectors.selector.psuedo.RootSelector;

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
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens, 0);
		Assertions.assertArrayEquals(new ComplexSelector[0], selectors);
	}
	
	@Test
	@DisplayName("Ident token creates type selector")
	public void identTokenCreatesTypeSelector() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "hi"
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens, 0);
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
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens, 0);
		Assertions.assertEquals(0, selectors.length);
	}
	
	@Test
	@DisplayName("Two comma delimited ident tokens create type selectors")
	public void twoCommaDelimitedIdentTokensCreateTypeSelectors() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "hi", new CommaToken() {}, (IdentToken) () -> "bye"
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens, 0);
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
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens, 0);
		Assertions.assertEquals(1, selectors.length);
		ComplexSelectorPart[] parts = selectors[0].getParts();
		Assertions.assertEquals(1, parts.length);
		Assertions.assertInstanceOf(TypeSelector.class, parts[0]);
		TypeSelector selector = (TypeSelector) parts[0];
		Assertions.assertEquals("hi", selector.getQualifiedName().getName());
	}
	
	@Test
	@DisplayName("Dot delim token creates class selector")
	public void dotDelimTokenCreatesClassSelector() {
		TokenLike[] tokens = new TokenLike[] {
			(DelimToken) () -> '.', (IdentToken) () -> "hi"
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens, 0);
		Assertions.assertEquals(1, selectors.length);
		ComplexSelectorPart[] parts = selectors[0].getParts();
		Assertions.assertEquals(1, parts.length);
		Assertions.assertInstanceOf(AttributeSelector.class, parts[0]);
		AttributeSelector selector = (AttributeSelector) parts[0];
		Assertions.assertEquals("hi", selector.getComparisonValue());
	}
	
	@Test
	@DisplayName("Pound delim token creates ID selector")
	public void poundDelimTokenCreatesIDSelector() {
		TokenLike[] tokens = new TokenLike[] {
			HashToken.create("hi", HashTypeFlag.ID)
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens, 0);
		Assertions.assertEquals(1, selectors.length);
		ComplexSelectorPart[] parts = selectors[0].getParts();
		Assertions.assertEquals(1, parts.length);
		Assertions.assertInstanceOf(IDSelector.class, parts[0]);
		IDSelector selector = (IDSelector) parts[0];
		Assertions.assertEquals("hi", selector.getId());
	}

	@Test
	@DisplayName("Colon creates psuedo selector")
	public void colonCreatesPsuedoSelector() {
		TokenLike[] tokens = new TokenLike[] {
			new ColonToken() {}, (IdentToken) () -> "root"
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens, 0);
		Assertions.assertEquals(1, selectors.length);
		ComplexSelectorPart[] parts = selectors[0].getParts();
		Assertions.assertEquals(1, parts.length);
		Assertions.assertInstanceOf(RootSelector.class, parts[0]);
	}

	@Test
	@DisplayName("Left square bracket token creates attribute selector")
	public void leftSquareBracketTokenCreatesAttributeSelector() {
		TokenLike[] tokens = new TokenLike[] {
			new LSBracketToken() {}, (IdentToken) () -> "hi", new RSBracketToken() {}
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens, 0);
		Assertions.assertEquals(1, selectors.length);
		ComplexSelectorPart[] parts = selectors[0].getParts();
		Assertions.assertEquals(1, parts.length);
		Assertions.assertInstanceOf(AttributeSelector.class, parts[0]);
		AttributeSelector selector = (AttributeSelector) parts[0];
		Assertions.assertEquals("hi", selector.getAttributeName().getName());
	}
	
	@Test
	@DisplayName("Two subsequent dot delim tokens creates multi-class selector")
	public void twoSubsequentDotDelimTokensCreatesMultiClassSelector() {
		TokenLike[] tokens = new TokenLike[] {
			(DelimToken) () -> '.', (IdentToken) () -> "hi", (DelimToken) () -> '.', (IdentToken) () -> "bye"
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens, 0);
		Assertions.assertEquals(1, selectors.length);
		ComplexSelectorPart[] parts = selectors[0].getParts();
		Assertions.assertEquals(2, parts.length);

		Assertions.assertInstanceOf(AttributeSelector.class, parts[0]);
		AttributeSelector selector = (AttributeSelector) parts[0];
		Assertions.assertEquals("hi", selector.getComparisonValue());

		Assertions.assertInstanceOf(AttributeSelector.class, parts[1]);
		selector = (AttributeSelector) parts[1];
		Assertions.assertEquals("bye", selector.getComparisonValue());
	}

	@Test
	@DisplayName("Two subsequent left square bracket tokens creates multi-attribute selector")
	public void twoSubsequentLeftSquareBracketTokensCreatesMultiAttributeSelector() {
		TokenLike[] tokens = new TokenLike[] {
			new LSBracketToken() {}, (IdentToken) () -> "hi", new RSBracketToken() {},
			new LSBracketToken() {}, (IdentToken) () -> "bye", new RSBracketToken() {}
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens, 0);
		Assertions.assertEquals(1, selectors.length);
		ComplexSelectorPart[] parts = selectors[0].getParts();
		Assertions.assertEquals(2, parts.length);

		Assertions.assertInstanceOf(AttributeSelector.class, parts[0]);
		AttributeSelector selector = (AttributeSelector) parts[0];
		Assertions.assertEquals("hi", selector.getAttributeName().getName());

		Assertions.assertInstanceOf(AttributeSelector.class, parts[1]);
		selector = (AttributeSelector) parts[1];
		Assertions.assertEquals("bye", selector.getAttributeName().getName());
	}

	@Test
	@DisplayName("Can parse complex selector with basic combinators")
	public void canParseComplexSelectorWithBasicCombinators() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "hi", (DelimToken) () -> '>', (IdentToken) () -> "bye"
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens, 0);
		Assertions.assertEquals(1, selectors.length);
		ComplexSelectorPart[] parts = selectors[0].getParts();
		Assertions.assertEquals(3, parts.length);

		Assertions.assertInstanceOf(TypeSelector.class, parts[0]);
		TypeSelector selector = (TypeSelector) parts[0];
		Assertions.assertEquals("hi", selector.getQualifiedName().getName());

		Assertions.assertInstanceOf(ChildCombinator.class, parts[1]);

		Assertions.assertInstanceOf(TypeSelector.class, parts[2]);
		selector = (TypeSelector) parts[2];
		Assertions.assertEquals("bye", selector.getQualifiedName().getName());
	}

	@Test
	@DisplayName("Can parse complex selector with descendant combinator")
	public void canParseComplexSelectorWithDescendantCombinator() {
		TokenLike[] tokens = new TokenLike[] {
			(IdentToken) () -> "hi", new WhitespaceToken() {}, (IdentToken) () -> "bye"
		};
		ComplexSelector[] selectors = complexSelectorParser.parseMany(tokens, 0);
		Assertions.assertEquals(1, selectors.length);
		ComplexSelectorPart[] parts = selectors[0].getParts();
		Assertions.assertEquals(3, parts.length);

		Assertions.assertInstanceOf(TypeSelector.class, parts[0]);
		TypeSelector selector = (TypeSelector) parts[0];
		Assertions.assertEquals("hi", selector.getQualifiedName().getName());

		Assertions.assertInstanceOf(DescendantCombinator.class, parts[1]);

		Assertions.assertInstanceOf(TypeSelector.class, parts[2]);
		selector = (TypeSelector) parts[2];
		Assertions.assertEquals("bye", selector.getQualifiedName().getName());
	}

}
