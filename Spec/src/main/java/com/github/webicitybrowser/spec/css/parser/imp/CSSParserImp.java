package com.github.webicitybrowser.spec.css.parser.imp;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.componentvalue.FunctionValue;
import com.github.webicitybrowser.spec.css.componentvalue.SimpleBlock;
import com.github.webicitybrowser.spec.css.parser.CSSParser;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.tokens.AtKeywordToken;
import com.github.webicitybrowser.spec.css.parser.tokens.CDCToken;
import com.github.webicitybrowser.spec.css.parser.tokens.CDOToken;
import com.github.webicitybrowser.spec.css.parser.tokens.ColonToken;
import com.github.webicitybrowser.spec.css.parser.tokens.EOFToken;
import com.github.webicitybrowser.spec.css.parser.tokens.FunctionToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LCBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LParenToken;
import com.github.webicitybrowser.spec.css.parser.tokens.LSBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RCBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RParenToken;
import com.github.webicitybrowser.spec.css.parser.tokens.RSBracketToken;
import com.github.webicitybrowser.spec.css.parser.tokens.SemicolonToken;
import com.github.webicitybrowser.spec.css.parser.tokens.Token;
import com.github.webicitybrowser.spec.css.parser.tokens.WhitespaceToken;
import com.github.webicitybrowser.spec.css.rule.AtRule;
import com.github.webicitybrowser.spec.css.rule.CSSRule;
import com.github.webicitybrowser.spec.css.rule.Declaration;
import com.github.webicitybrowser.spec.css.rule.QualifiedRule;

public class CSSParserImp implements CSSParser {

	@Override
	public CSSRule[] parseAListOfRules(Token[] tokens) {
		TokenStream stream = new TokenStreamImp(tokens);
		return consumeAListOfRules(stream, false);
	}
	
	@Override
	public CSSRule[] parseAListOfDeclarations(TokenLike[] tokens) {
		TokenStream stream = new TokenStreamImp(tokens);
		return consumeAListOfDeclarations(stream);
	}

	private CSSRule[] consumeAListOfRules(TokenStream stream, boolean topLevel) {
		List<CSSRule> rules = new ArrayList<>();
		
		while (true) {
			TokenLike token = stream.read();
			if (token instanceof WhitespaceToken) {
				// Do nothing
			} else if (token instanceof EOFToken) {
				break;
			} else if ((token instanceof CDCToken || token instanceof CDOToken) && topLevel) {
				// Do nothing
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
		
		return rules.toArray(new CSSRule[rules.size()]);
	}
	
	private CSSRule[] consumeAListOfDeclarations(TokenStream stream) {
		List<CSSRule> declarations = new ArrayList<>();
		while (true) {
			TokenLike token = stream.peek();
			if (token instanceof WhitespaceToken || token instanceof SemicolonToken) {
				stream.read();
			} else if (token instanceof EOFToken) {
				return declarations.toArray(CSSRule[]::new);
			} else if (token instanceof AtKeywordToken) {
				declarations.add(consumeAnAtRule(stream));
			} else if (token instanceof IdentToken) {
				handleIdentToken(stream, declarations);
			} else {
				// TODO: Parse Error
				while (true) {
					token = stream.peek();
					if (token instanceof SemicolonToken || token instanceof EOFToken) {
						break;
					}
					consumeAComponentValue(stream);
				}
			}
		}
	}

	private void handleIdentToken(TokenStream stream, List<CSSRule> declarations) {
		List<TokenLike> tokens = new ArrayList<>();
		while (true) {
			TokenLike token = stream.peek();
			if (token instanceof SemicolonToken || token instanceof EOFToken) {
				break;
			}
			tokens.add(consumeAComponentValue(stream));
		}
		Token[] tokensArr = tokens.toArray(Token[]::new);
		Declaration declaration = consumeADeclaration(new TokenStreamImp(tokensArr));
		if (declaration != null) {
			declarations.add(declaration);
		}
	}

	private Declaration consumeADeclaration(TokenStream stream) {
		String name = ((IdentToken) stream.read()).value();
		List<TokenLike> value = new ArrayList<>();
		boolean important = false;
		consumeWhitespace(stream);
		if (!(stream.read() instanceof ColonToken)) {
			// TODO: Parse Error
			return null;
		}
		while (!(stream.read() instanceof EOFToken)) {
			stream.unread();
			value.add(consumeAComponentValue(stream));
		}
		// TODO: !important
		while (!value.isEmpty() && value.get(value.size() - 1) instanceof WhitespaceToken) {
			value.remove(value.size() - 1);
		}
		return new Declaration(name, value.toArray(TokenLike[]::new), important);
	}

	private void consumeWhitespace(TokenStream stream) {
		while (true) {
			TokenLike token = stream.read();
			if (!(token instanceof WhitespaceToken)) {
				break;
			}
		}
		stream.unread();
	}

	private CSSRule consumeAnAtRule(TokenStream stream) {
		AtKeywordToken atToken = (AtKeywordToken) stream.read();
		List<TokenLike> prelude = new ArrayList<>();
		
		while (true) {
			TokenLike token = stream.read();
			if (token instanceof SemicolonToken) {
				return new AtRule(atToken.value(), prelude, null);
			} else if (token instanceof EOFToken) {
				// TODO: Parse Error
				return new AtRule(atToken.value(), prelude, null);
			} else if (token instanceof LCBracketToken) {
				SimpleBlock value = consumeASimpleBlock(stream, (Token) token);
				return new AtRule(atToken.value(), prelude, value);
			} else if (token instanceof SimpleBlock && ((SimpleBlock) token).type() instanceof LCBracketToken) {
				SimpleBlock value = (SimpleBlock) token;
				return new AtRule(atToken.value(), prelude, value);
			} else {
				stream.unread();
				prelude.add(consumeAComponentValue(stream));
			}
		}
	}
	
	private CSSRule consumeAQualifiedRule(TokenStream stream) {
		List<TokenLike> prelude = new ArrayList<>();
		
		while (true) {
			TokenLike token = stream.read();
			if (token instanceof EOFToken) {
				return null;
			} else if (token instanceof LCBracketToken) {
				SimpleBlock value = consumeASimpleBlock(stream, (Token) token);
				return new QualifiedRule(prelude, value);
			} else if (token instanceof SimpleBlock && ((SimpleBlock) token).type() instanceof LCBracketToken) {
				SimpleBlock value = (SimpleBlock) token;
				return new QualifiedRule(prelude, value);
			} else {
				stream.unread();
				prelude.add(consumeAComponentValue(stream));
			}
		}
	}

	private SimpleBlock consumeASimpleBlock(TokenStream stream, Token type) {
		List<TokenLike> value = new ArrayList<>();
		
		while (true) {
			TokenLike token = stream.read();
			if (isCloseToken(type, token)) {
				return new SimpleBlock(type, value);
			} else if (token instanceof EOFToken) {
				// TODO: Parse Error
				return new SimpleBlock(type, value);
			} else {
				stream.unread();
				value.add(consumeAComponentValue(stream));
			}
		}
	}

	private TokenLike consumeAComponentValue(TokenStream stream) {
		TokenLike token = stream.read();
		if (isOpenToken(token)) {
			return consumeASimpleBlock(stream, (Token) token);
		} else if (token instanceof FunctionToken) {
			return consumeAFunction(stream, (FunctionToken) token);
		} else {
			return token;
		}
	}

	private TokenLike consumeAFunction(TokenStream stream, FunctionToken functionToken) {
		List<TokenLike> value = new ArrayList<>();
		
		while (true) {
			TokenLike token = stream.read();
			if (token instanceof RParenToken) {
				return new FunctionValue(functionToken.value(), value.toArray(TokenLike[]::new));
			} else if (token instanceof EOFToken) {
				// TODO: Parse Error
				return new FunctionValue(functionToken.value(), value.toArray(TokenLike[]::new));
			} else {
				stream.unread();
				value.add(consumeAComponentValue(stream));
			}
		}
	}


	private boolean isOpenToken(TokenLike token) {
		return 
			token instanceof LCBracketToken ||
			token instanceof LSBracketToken ||
			token instanceof LParenToken;
	}
	
	private boolean isCloseToken(Token startToken, TokenLike other) {
		return
			(startToken instanceof LCBracketToken && other instanceof RCBracketToken) ||
			(startToken instanceof LSBracketToken && other instanceof RSBracketToken) ||
			(startToken instanceof LParenToken && other instanceof RParenToken);
	}
	
}
