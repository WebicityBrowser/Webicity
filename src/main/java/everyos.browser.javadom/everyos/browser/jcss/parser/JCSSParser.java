package everyos.browser.jcss.parser;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.jcss.imp.AtRule;
import everyos.browser.jcss.imp.Declaration;
import everyos.browser.jcss.imp.Function;
import everyos.browser.jcss.imp.QualifiedRule;
import everyos.browser.jcss.imp.SimpleBlock;
import everyos.browser.jcss.intf.CSSRule;

public class JCSSParser {
	//TODO: Allow use of ComponentValues instead of tokens
	
	public CSSRule[] parseAListOfRules(CSSToken[] tokens) {
		return consumeAListOfRules(tokens, false);
	}
	
	public Declaration[] parseAListOfDeclarations(CSSToken[] tokens) {
		return consumeAListOfDeclarations(new TokenStream(tokens));
	}

	private CSSRule[] consumeAListOfRules(CSSToken[] tokens, boolean topLevel) {
		List<CSSRule> rules = new ArrayList<>();
		TokenStream stream = new TokenStream(tokens);
		
		while (true) {
			CSSToken token = stream.read();
			
			if (token instanceof WhitespaceToken) {
				
			} else if (token instanceof EOFToken) {
				return rules.toArray(new CSSRule[rules.size()]);
			} else if (token instanceof CDOToken || token instanceof CDCToken) {
				if (topLevel) {
					continue;
				}
				stream.unread();
				CSSRule rule = consumeAQualifiedRule(stream);
				if (rule!=null) {
					rules.add(rule);
				}
			} else if (token instanceof AtKeywordToken) {
				stream.unread();
				rules.add(consumeAnAtRule(stream));
			} else {
				stream.unread();
				CSSRule rule = consumeAQualifiedRule(stream);
				if (rule!=null) {
					rules.add(rule);
				}
			}
		}
	}
	
	private AtRule consumeAnAtRule(TokenStream stream) {
		assert(stream.peek() instanceof AtKeywordToken);
		AtKeywordToken atToken = (AtKeywordToken) stream.read();
		
		AtRule atRule = new AtRule();
		atRule.setName(atToken.getValue());
		
		while (true) {
			CSSToken token = stream.read();
			if (token instanceof SemicolonToken || token instanceof EOFToken) {
				return atRule;
			} else if (token instanceof LCBrackToken) {
				atRule.setBlock(consumeASimpleBlock(stream));
				return atRule;
				// TODO
			} else {
				stream.unread();
				atRule.appendToPrelude(consumeAComponentValue(stream));
			}
		}
	}

	private QualifiedRule consumeAQualifiedRule(TokenStream stream) {
		QualifiedRule rule = new QualifiedRule();
		
		while (true) {
			CSSToken token = stream.read();
			if (token instanceof EOFToken) {
				stream.unread();
				return null;
			} else if (token instanceof LCBrackToken) {
				rule.setBlock(consumeASimpleBlock(stream));
				return rule;
				// TODO
			} else {
				stream.unread();
				rule.appendToPrelude(consumeAComponentValue(stream));
			}
		}
	}
	
	private Declaration[] consumeAListOfDeclarations(TokenStream tokenStream) {
		List<Declaration> declarations = new ArrayList<>();
		
		return declarations.toArray(new Declaration[declarations.size()]);
	}
	
	private Object consumeAComponentValue(TokenStream stream) {
		CSSToken token = stream.read();
		
		if (token instanceof LCBrackToken ||
			token instanceof LSBrackToken ||
			token instanceof LParenToken) {
			
			return consumeASimpleBlock(stream);
		} else if (token instanceof FunctionToken) {
			return consumeAFunction(stream);
		} else {
			return token;
		}
	}

	private SimpleBlock consumeASimpleBlock(TokenStream stream) {
		stream.unread();
		CSSToken startToken = stream.read();
		
		assert(startToken instanceof LCBrackToken ||
			startToken instanceof LSBrackToken ||
			startToken instanceof LParenToken);
		
		SimpleBlock block = new SimpleBlock();
		block.setAssociatedToken(startToken);
		
		
		while (true) {
			CSSToken token = stream.read();
			if (isEndingToken(token, startToken) || token instanceof EOFToken) {
				return block;
			} else {
				stream.unread();
				block.append(consumeAComponentValue(stream));
			}
		}
	}
	
	private boolean isEndingToken(CSSToken token, CSSToken startToken) {
		return
			(startToken instanceof LCBrackToken && token instanceof RCBrackToken) ||
			(startToken instanceof LSBrackToken && token instanceof RSBrackToken) ||
			(startToken instanceof LParenToken && token instanceof RParenToken);
	}

	private Function consumeAFunction(TokenStream stream) {
		stream.unread();
		assert(stream.peek() instanceof FunctionToken);
		FunctionToken funcToken = (FunctionToken) stream.read();
		
		Function function = new Function();
		function.setName(funcToken.getValue());
		
		while (true) {
			CSSToken token = stream.read();
			if (token instanceof RParenToken || token instanceof EOFToken) {
				return function;
			} else {
				stream.unread();
				function.append(consumeAComponentValue(stream));
			}
		}
	}
}
