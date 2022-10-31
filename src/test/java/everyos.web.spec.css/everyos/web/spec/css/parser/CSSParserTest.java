package everyos.web.spec.css.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import everyos.web.spec.css.parser.componentvalue.SimpleBlock;
import everyos.web.spec.css.parser.rule.AtRule;
import everyos.web.spec.css.parser.rule.QualifiedRule;
import everyos.web.spec.css.parser.tokens.AtKeywordToken;
import everyos.web.spec.css.parser.tokens.EOFToken;
import everyos.web.spec.css.parser.tokens.LCBracketToken;
import everyos.web.spec.css.parser.tokens.RCBracketToken;
import everyos.web.spec.css.parser.tokens.SemicolonToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.rule.CSSRule;

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
	
	// TODO: At rule with prelude
	
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
	
}
