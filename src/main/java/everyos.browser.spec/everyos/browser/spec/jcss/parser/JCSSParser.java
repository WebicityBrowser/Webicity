package everyos.browser.spec.jcss.parser;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.spec.jcss.imp.AtRule;
import everyos.browser.spec.jcss.imp.Function;
import everyos.browser.spec.jcss.imp.QualifiedRule;
import everyos.browser.spec.jcss.imp.SimpleBlock;
import everyos.browser.spec.jcss.intf.CSSRule;

public final class JCSSParser {
	
	private JCSSParser() {};
	
	public static CSSRule[] parseAListOfRules(CSSToken[] tokens) {
		return consumeAListOfRules(tokens, false);
	}
	
	public static CSSRule[] parseAListOfDeclarations(CSSToken[] tokens) {
		return consumeAListOfDeclarations(new TokenStream(tokens));
	}

	private static CSSRule[] consumeAListOfRules(CSSToken[] tokens, boolean topLevel) {
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
				if (rule != null) {
					rules.add(rule);
				}
			} else if (token instanceof AtKeywordToken) {
				stream.unread();
				rules.add(consumeAnAtRule(stream));
			} else {
				stream.unread();
				CSSRule rule = consumeAQualifiedRule(stream);
				if (rule != null) {
					rules.add(rule);
				}
			}
		}
	}
	
	private static AtRule consumeAnAtRule(TokenStream stream) {
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

	private static QualifiedRule consumeAQualifiedRule(TokenStream stream) {
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
	
	private static CSSRule[] consumeAListOfDeclarations(TokenStream stream) {
		List<CSSRule> declarations = new ArrayList<>();
		
		while (true) {
			CSSToken token = stream.read();
			if (token instanceof WhitespaceToken || token instanceof SemicolonToken) {
				
			} else if (token instanceof EOFToken) {
				return declarations.toArray(new CSSRule[declarations.size()]);
			} else if (token instanceof AtKeywordToken) {
				stream.unread();
				declarations.add(consumeAnAtRule(stream));
			} else if (token instanceof IdentToken) {
				List<CSSToken> tempList = new ArrayList<>();
				tempList.add(token);
				while (!(stream.peek() instanceof SemicolonToken || stream.peek() instanceof EOFToken)) {
					tempList.add(consumeAComponentValue(stream));
				}
				
				TokenStream tempStream = new TokenStream(tempList.toArray(new CSSToken[tempList.size()]));
				Declaration declaration = consumeADeclaration(tempStream);
				if (declaration != null) {
					declarations.add(declaration);
				}
			} else {
				stream.unread();
				while (!(stream.peek() instanceof SemicolonToken || stream.peek() instanceof EOFToken)) {
					consumeAComponentValue(stream);
				}
			}
		}
	}
	
	private static Declaration consumeADeclaration(TokenStream stream) {
		Declaration declaration = new Declaration((IdentToken) stream.read());
		while (stream.peek() instanceof WhitespaceToken) {
			stream.read();
		}
		if (!(stream.peek() instanceof ColonToken)) {
			return null;
		}
		stream.read();
		while (stream.peek() instanceof WhitespaceToken) {
			stream.read();
		}
		while (!(stream.peek() instanceof EOFToken)) {
			declaration.append(consumeAComponentValue(stream));
		}
		List<CSSToken> value = declaration.getValueAsList();
		if (value.size() > 1) {
			CSSToken tok1 = value.get(value.size()-2);
			CSSToken tok2 = value.get(value.size()-1);
			if (
				tok1 instanceof DelimToken &&
				((DelimToken) tok1).getValue().equals("!") &&
				tok2 instanceof IdentToken &&
				((IdentToken) tok2).getValue().equalsIgnoreCase("important")) {
				
				declaration.setImportant(true);
				value.remove(value.size()-1);
				value.remove(value.size()-1);
			}
		}
		
		while (value.get(value.size()-1) instanceof WhitespaceToken) {
			value.remove(value.size()-1);
		}
		
		return declaration;
	}

	private static CSSToken consumeAComponentValue(TokenStream stream) {
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

	private static SimpleBlock consumeASimpleBlock(TokenStream stream) {
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
	
	private static boolean isEndingToken(CSSToken token, CSSToken startToken) {
		return
			(startToken instanceof LCBrackToken && token instanceof RCBrackToken) ||
			(startToken instanceof LSBrackToken && token instanceof RSBrackToken) ||
			(startToken instanceof LParenToken && token instanceof RParenToken);
	}

	private static Function consumeAFunction(TokenStream stream) {
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
