package everyos.web.spec.css.parser;

import java.util.ArrayList;
import java.util.List;

import everyos.web.spec.css.componentvalue.FunctionValue;
import everyos.web.spec.css.componentvalue.SimpleBlock;
import everyos.web.spec.css.parser.tokens.AtKeywordToken;
import everyos.web.spec.css.parser.tokens.CDCToken;
import everyos.web.spec.css.parser.tokens.CDOToken;
import everyos.web.spec.css.parser.tokens.ColonToken;
import everyos.web.spec.css.parser.tokens.EOFToken;
import everyos.web.spec.css.parser.tokens.FunctionToken;
import everyos.web.spec.css.parser.tokens.IdentToken;
import everyos.web.spec.css.parser.tokens.LCBracketToken;
import everyos.web.spec.css.parser.tokens.LParenToken;
import everyos.web.spec.css.parser.tokens.LSBracketToken;
import everyos.web.spec.css.parser.tokens.RCBracketToken;
import everyos.web.spec.css.parser.tokens.RParenToken;
import everyos.web.spec.css.parser.tokens.RSBracketToken;
import everyos.web.spec.css.parser.tokens.SemicolonToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.parser.tokens.WhitespaceToken;
import everyos.web.spec.css.rule.AtRule;
import everyos.web.spec.css.rule.CSSRule;
import everyos.web.spec.css.rule.Declaration;
import everyos.web.spec.css.rule.QualifiedRule;

public class CSSParserImp implements CSSParser {

	@Override
	public CSSRule[] parseAListOfRules(Token[] tokens) {
		TokenStream stream = new TokenStreamImp(tokens);
		return consumeAListOfRules(stream, false);
	}
	
	@Override
	public CSSRule[] parseAListOfDeclarations(Token[] tokens) {
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
			TokenLike token = stream.read();
			if (token instanceof WhitespaceToken || token instanceof SemicolonToken) {
				// Do nothing
			} else if (token instanceof EOFToken) {
				return declarations.toArray(new CSSRule[declarations.size()]);
			} else if (token instanceof AtKeywordToken) {
				stream.unread();
				declarations.add(consumeAnAtRule(stream));
			} else if (token instanceof IdentToken) {
				List<TokenLike> tokens = new ArrayList<>();
				while (true) {
					TokenLike token2 = stream.read();
					if (token2 instanceof SemicolonToken || token2 instanceof EOFToken) {
						break;
					}
					tokens.add(consumeAComponentValue(stream));
				}
				stream.unread();
				Token[] tokensArr = tokens.toArray(new Token[tokens.size()]);
				Declaration declaration = consumeADeclaration(new TokenStreamImp(tokensArr));
				if (declaration != null) {
					declarations.add(declaration);
				}
			} else {
				// TODO: Parse Error
				stream.unread();
				while (true) {
					TokenLike token2 = stream.read();
					if (token2 instanceof SemicolonToken || token2 instanceof EOFToken) {
						break;
					}
					consumeAComponentValue(stream);
				}
				stream.unread();
			}
		}
	}

	private Declaration consumeADeclaration(TokenStream stream) {
		String name = ((IdentToken) stream.read()).getValue();
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
		while (value.get(value.size()-1) instanceof WhitespaceToken) {
			value.remove(value.size()-1);
		}
		return createDeclaration(name, value, important);
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
				return createAtRule(atToken.getValue(), prelude, null);
			} else if (token instanceof EOFToken) {
				// TODO: Parse Error
				return createAtRule(atToken.getValue(), prelude, null);
			} else if (token instanceof LCBracketToken) {
				SimpleBlock value = consumeASimpleBlock(stream, (Token) token);
				return createAtRule(atToken.getValue(), prelude, value);
			} else if (token instanceof SimpleBlock && ((SimpleBlock) token).getType() instanceof LCBracketToken) {
				SimpleBlock value = (SimpleBlock) token;
				return createAtRule(atToken.getValue(), prelude, value);
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
				return createQualifiedRule(prelude, value);
			} else if (token instanceof SimpleBlock && ((SimpleBlock) token).getType() instanceof LCBracketToken) {
				SimpleBlock value = (SimpleBlock) token;
				return createQualifiedRule(prelude, value);
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
				return createSimpleBlock(type, value);
			} else if (token instanceof EOFToken) {
				// TODO: Parse Error
				return createSimpleBlock(type, value);
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
				return createFunction(functionToken.getValue(), value);
			} else if (token instanceof EOFToken) {
				// TODO: Parse Error
				return createFunction(functionToken.getValue(), value);
			} else {
				stream.unread();
				value.add(consumeAComponentValue(stream));
			}
		}
	}
	
	private Declaration createDeclaration(String name, List<TokenLike> valueList, boolean important) {
		TokenLike[] value = valueList.toArray(new TokenLike[valueList.size()]);
		
		return new Declaration() {
			@Override
			public String getName() {
				return name;
			}
			
			@Override
			public TokenLike[] getValue() {
				return value;
			}
			
			@Override
			public boolean isImportant() {
				return important;
			}
		};
	}
	
	private CSSRule createAtRule(String name, List<TokenLike> preludeList, SimpleBlock value) {
		TokenLike[] prelude = preludeList.toArray(new TokenLike[preludeList.size()]);
		
		return new AtRule() {
			@Override
			public String getName() {
				return name;
			}
			
			@Override
			public TokenLike[] getPrelude() {
				return prelude;
			}
			
			@Override
			public SimpleBlock getValue() {
				return value;
			}
		};
	}
	
	private CSSRule createQualifiedRule(List<TokenLike> preludeList, SimpleBlock value) {
		TokenLike[] prelude = preludeList.toArray(new TokenLike[preludeList.size()]);
		
		return new QualifiedRule() {
			@Override
			public TokenLike[] getPrelude() {
				return prelude;
			}
			
			@Override
			public SimpleBlock getValue() {
				return value;
			}
		};
	}
	
	private SimpleBlock createSimpleBlock(Token type, List<TokenLike> valueList) {
		TokenLike[] value = valueList.toArray(new TokenLike[valueList.size()]);
		
		return new SimpleBlock() {
			@Override
			public Token getType() {
				return type;
			}

			@Override
			public TokenLike[] getValue() {
				return value;
			}
		};
	}
	
	private TokenLike createFunction(String name, List<TokenLike> valueList) {
		TokenLike[] value = valueList.toArray(new TokenLike[valueList.size()]);
		
		return new FunctionValue() {
			@Override
			public TokenLike[] getValue() {
				return value;
			}
			
			@Override
			public String getName() {
				return name;
			}
		};
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
