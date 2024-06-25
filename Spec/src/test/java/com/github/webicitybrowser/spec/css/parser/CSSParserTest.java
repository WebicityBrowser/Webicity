package com.github.webicitybrowser.spec.css.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.github.webicitybrowser.spec.css.componentvalue.FunctionValue;
import com.github.webicitybrowser.spec.css.componentvalue.SimpleBlock;
import com.github.webicitybrowser.spec.css.parser.imp.CSSParserImp;
import com.github.webicitybrowser.spec.css.parser.tokens.AtKeywordToken;
import com.github.webicitybrowser.spec.css.parser.tokens.EOFToken;
import com.github.webicitybrowser.spec.css.parser.tokens.FunctionToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LCBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.PercentageToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RCBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RParenToken;
import com.github.webicitybrowser.spec.css.parser.tokens.SemicolonToken;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.rule.AtRule;
import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.QualifiedRule;

public class CSSParserTest {

	private CSSParser parser;
	
	@BeforeEach
	public void beforeEach() {
		parser = new CSSParserImp();
	}
	
	@Test
	@DisplayName("Can parse a simple at rule")
	public void canParseASimpleAtRule() {
		AtKeywordToken atKeywordToken = Mockito.mock(AtKeywordToken.class);
		Mockito.when(atKeywordToken.getValue()).thenReturn("test");
		Token semicolonToken = Mockito.mock(SemicolonToken.class);
		Token eofToken = Mockito.mock(EOFToken.class);
		Token[] tokens = new Token[] {
			atKeywordToken, semicolonToken, eofToken
		};
		CSSRule[] rules = parser.parseAListOfRules(tokens);
		Assertions.assertEquals(1, rules.length);
		Assertions.assertInstanceOf(AtRule.class, rules[0]);
		AtRule atRule = ((AtRule) rules[0]);
		Assertions.assertEquals("test", atRule.getName());
		Assertions.assertEquals(0, atRule.getPrelude().length);
		Assertions.assertNull(atRule.getValue());
	}
	
	@Test
	@DisplayName("Can parse an at rule with a raw block")
	public void canParseAnAtRuleWithARawBlock() {
		AtKeywordToken atKeywordToken = Mockito.mock(AtKeywordToken.class);
		Mockito.when(atKeywordToken.getValue()).thenReturn("test");
		LCBracketToken lcBracketToken = Mockito.mock(LCBracketToken.class);
		RCBracketToken rcBracketToken = Mockito.mock(RCBracketToken.class);
		Token eofToken = Mockito.mock(EOFToken.class);
		Token[] tokens = new Token[] {
			atKeywordToken, lcBracketToken, rcBracketToken, eofToken
		};
		CSSRule[] rules = parser.parseAListOfRules(tokens);
		Assertions.assertEquals(1, rules.length);
		Assertions.assertInstanceOf(AtRule.class, rules[0]);
		AtRule atRule = ((AtRule) rules[0]);
		Assertions.assertEquals("test", atRule.getName());
		Assertions.assertNotNull(atRule.getValue());
	}
	
	@Test
	@DisplayName("Can parse an at rule with a preparsed block")
	public void canParseAnAtRuleWithAPreparsedBlock() {
		AtKeywordToken atKeywordToken = Mockito.mock(AtKeywordToken.class);
		Mockito.when(atKeywordToken.getValue()).thenReturn("test");
		LCBracketToken lcBracketToken = Mockito.mock(LCBracketToken.class);
		SimpleBlock simpleBlock = Mockito.mock(SimpleBlock.class);
		Mockito.when(simpleBlock.getType()).thenReturn(lcBracketToken);
		Token eofToken = Mockito.mock(EOFToken.class);
		Token[] tokens = new Token[] {
			atKeywordToken, simpleBlock, eofToken
		};
		CSSRule[] rules = parser.parseAListOfRules(tokens);
		Assertions.assertEquals(1, rules.length);
		Assertions.assertInstanceOf(AtRule.class, rules[0]);
		AtRule atRule = ((AtRule) rules[0]);
		Assertions.assertEquals("test", atRule.getName());
		Assertions.assertEquals(simpleBlock, atRule.getValue());
	}
	
	@Test
	@DisplayName("Can parse an at rule with a prelude")
	public void canParseAnAtRuleWithAPrelude() {
		AtKeywordToken atKeywordToken = Mockito.mock(AtKeywordToken.class);
		Mockito.when(atKeywordToken.getValue()).thenReturn("test");
		IdentToken identToken = Mockito.mock(IdentToken.class);
		LCBracketToken lcBracketToken = Mockito.mock(LCBracketToken.class);
		SimpleBlock simpleBlock = Mockito.mock(SimpleBlock.class);
		Mockito.when(simpleBlock.getType()).thenReturn(lcBracketToken);
		Token eofToken = Mockito.mock(EOFToken.class);
		Token[] tokens = new Token[] {
			atKeywordToken, identToken, simpleBlock, eofToken
		};
		CSSRule[] rules = parser.parseAListOfRules(tokens);
		Assertions.assertEquals(1, rules.length);
		Assertions.assertInstanceOf(AtRule.class, rules[0]);
		AtRule atRule = ((AtRule) rules[0]);
		Assertions.assertEquals("test", atRule.getName());
		Assertions.assertEquals(simpleBlock, atRule.getValue());
		Assertions.assertEquals(1, atRule.getPrelude().length);
		Assertions.assertEquals(identToken, atRule.getPrelude()[0]);
	}
	
	@Test
	@DisplayName("Can parse a qualified rule with a raw block")
	public void canParseAQualifiedRuleWithARawBlock() {
		LCBracketToken lcBracketToken = Mockito.mock(LCBracketToken.class);
		RCBracketToken rcBracketToken = Mockito.mock(RCBracketToken.class);
		Token eofToken = Mockito.mock(EOFToken.class);
		Token[] tokens = new Token[] {
			lcBracketToken, rcBracketToken, eofToken
		};
		CSSRule[] rules = parser.parseAListOfRules(tokens);
		Assertions.assertEquals(1, rules.length);
		Assertions.assertInstanceOf(QualifiedRule.class, rules[0]);
		QualifiedRule qualifiedRule = ((QualifiedRule) rules[0]);
		Assertions.assertEquals(0, qualifiedRule.getPrelude().length);
		Assertions.assertNotNull(qualifiedRule.getValue());
	}
	
	@Test
	@DisplayName("Can parse a qualified rule with a preparsed block")
	public void canParseAQualifiedRuleWithAPreparsedBlock() {
		LCBracketToken lcBracketToken = Mockito.mock(LCBracketToken.class);
		SimpleBlock simpleBlock = Mockito.mock(SimpleBlock.class);
		Mockito.when(simpleBlock.getType()).thenReturn(lcBracketToken);
		Token eofToken = Mockito.mock(EOFToken.class);
		Token[] tokens = new Token[] {
			simpleBlock, eofToken
		};
		CSSRule[] rules = parser.parseAListOfRules(tokens);
		Assertions.assertEquals(1, rules.length);
		Assertions.assertInstanceOf(QualifiedRule.class, rules[0]);
		QualifiedRule qualifiedRule = ((QualifiedRule) rules[0]);
		Assertions.assertEquals(simpleBlock, qualifiedRule.getValue());
	}
	
	@Test
	@DisplayName("Can parse a qualified rule with a prelude")
	public void canParseAQualifiedRuleWithAPrelude() {
		IdentToken identToken = Mockito.mock(IdentToken.class);
		LCBracketToken lcBracketToken = Mockito.mock(LCBracketToken.class);
		SimpleBlock simpleBlock = Mockito.mock(SimpleBlock.class);
		Mockito.when(simpleBlock.getType()).thenReturn(lcBracketToken);
		Token eofToken = Mockito.mock(EOFToken.class);
		Token[] tokens = new Token[] {
			identToken, simpleBlock, eofToken
		};
		CSSRule[] rules = parser.parseAListOfRules(tokens);
		Assertions.assertEquals(1, rules.length);
		Assertions.assertInstanceOf(QualifiedRule.class, rules[0]);
		QualifiedRule qualifiedRule = ((QualifiedRule) rules[0]);
		Assertions.assertEquals(simpleBlock, qualifiedRule.getValue());
		Assertions.assertEquals(1, qualifiedRule.getPrelude().length);
		Assertions.assertEquals(identToken, qualifiedRule.getPrelude()[0]);
	}
	
	@Test
	@DisplayName("Can parse a function")
	public void canParseAFunction() {
		LCBracketToken lcBracketToken = Mockito.mock(LCBracketToken.class);
		RCBracketToken rcBracketToken = Mockito.mock(RCBracketToken.class);
		FunctionToken functionToken = Mockito.mock(FunctionToken.class);
		Mockito.when(functionToken.getValue()).thenReturn("calc");
		PercentageToken percentageToken = Mockito.mock(PercentageToken.class);
		RParenToken rParenToken = Mockito.mock(RParenToken.class);
		Token eofToken = Mockito.mock(EOFToken.class);
		Token[] tokens = new Token[] {
			lcBracketToken, functionToken, percentageToken,
			rParenToken, rcBracketToken, eofToken
		};
		CSSRule[] rules = parser.parseAListOfRules(tokens);
		Assertions.assertEquals(1, rules.length);
		Assertions.assertInstanceOf(QualifiedRule.class, rules[0]);
		QualifiedRule qualifiedRule = ((QualifiedRule) rules[0]);
		Assertions.assertEquals(0, qualifiedRule.getPrelude().length);
		Assertions.assertNotNull(qualifiedRule.getValue());
		SimpleBlock innerBlock = qualifiedRule.getValue();
		Assertions.assertInstanceOf(FunctionValue.class, innerBlock.getValue()[0]);
		FunctionValue function = (FunctionValue) innerBlock.getValue()[0];
		Assertions.assertEquals("calc", function.getName());
		Assertions.assertEquals(1, function.getValue().length);
		Assertions.assertEquals(percentageToken, function.getValue()[0]);
	}
	
	// TODO: Add test for "consume a list of declarations"
	
}
