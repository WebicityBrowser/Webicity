package everyos.web.spec.css.parser;

import java.util.ArrayList;
import java.util.List;

import everyos.web.spec.css.parser.componentvalue.ComponentValue;
import everyos.web.spec.css.parser.componentvalue.SimpleBlock;
import everyos.web.spec.css.parser.rule.AtRule;
import everyos.web.spec.css.parser.rule.QualifiedRule;
import everyos.web.spec.css.parser.tokens.AtKeywordToken;
import everyos.web.spec.css.parser.tokens.CDCToken;
import everyos.web.spec.css.parser.tokens.EOFToken;
import everyos.web.spec.css.parser.tokens.LCBracketToken;
import everyos.web.spec.css.parser.tokens.LParenToken;
import everyos.web.spec.css.parser.tokens.LSBracketToken;
import everyos.web.spec.css.parser.tokens.RCBracketToken;
import everyos.web.spec.css.parser.tokens.RParenToken;
import everyos.web.spec.css.parser.tokens.RSBracketToken;
import everyos.web.spec.css.parser.tokens.SemicolonToken;
import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.parser.tokens.WhitespaceToken;
import everyos.web.spec.css.rule.CSSRule;

public class CSSParserImp implements CSSParser {

	@Override
	public CSSRule[] parseAListOfRules(Token[] tokens) {
		// TODO Normalize
		TokenStream stream = new TokenStreamImp(tokens);
		return consumeAListOfRules(stream, false);
	}

	private CSSRule[] consumeAListOfRules(TokenStream stream, boolean topLevel) {
		List<CSSRule> rules = new ArrayList<>();
		
		while (true) {
			TokenLike token = stream.read();
			if (token instanceof WhitespaceToken) {
				// Do nothing
			} else if (token instanceof EOFToken) {
				break;
			} else if ((token instanceof CDCToken || token instanceof CDCToken) && topLevel) {
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

	private CSSRule consumeAnAtRule(TokenStream stream) {
		AtKeywordToken atToken = (AtKeywordToken) stream.read();
		List<ComponentValue> prelude = new ArrayList<>();
		
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
				prelude.add(consumeAComponentValue());
			}
		}
	}
	
	private CSSRule consumeAQualifiedRule(TokenStream stream) {
		List<ComponentValue> prelude = new ArrayList<>();
		
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
				prelude.add(consumeAComponentValue());
			}
		}
	}

	private SimpleBlock consumeASimpleBlock(TokenStream stream, Token type) {
		List<ComponentValue> value = new ArrayList<>();
		
		while (true) {
			TokenLike token = stream.read();
			if (isCloseToken(type, token)) {
				return createSimpleBlock(type, value);
			} else if (token instanceof EOFToken) {
				// TODO: Parse Error
				return createSimpleBlock(type, value);
			} else {
				stream.unread();
				value.add(consumeAComponentValue());
			}
		}
	}

	private ComponentValue consumeAComponentValue() {
		// TODO Auto-generated method stub
		return null;
	}

	private CSSRule createAtRule(String name, List<ComponentValue> preludeList, SimpleBlock value) {
		ComponentValue[] prelude = preludeList.toArray(new ComponentValue[preludeList.size()]);
		
		return new AtRule() {
			@Override
			public String getName() {
				return name;
			}
			
			@Override
			public ComponentValue[] getPrelude() {
				return prelude;
			}
			
			@Override
			public SimpleBlock getValue() {
				return value;
			}
		};
	}
	
	private CSSRule createQualifiedRule(List<ComponentValue> preludeList, SimpleBlock value) {
		ComponentValue[] prelude = preludeList.toArray(new ComponentValue[preludeList.size()]);
		
		return new QualifiedRule() {
			@Override
			public ComponentValue[] getPrelude() {
				return prelude;
			}
			
			@Override
			public SimpleBlock getValue() {
				return value;
			}
		};
	}
	
	private SimpleBlock createSimpleBlock(Token type, List<ComponentValue> valueList) {
		ComponentValue[] value = valueList.toArray(new ComponentValue[valueList.size()]);
		
		return new SimpleBlock() {
			@Override
			public Token getType() {
				return type;
			}

			@Override
			public ComponentValue[] getValue() {
				return value;
			}
		};
	}

	private boolean isCloseToken(Token startToken, TokenLike other) {
		if (startToken instanceof LCBracketToken) {
			return other instanceof RCBracketToken;
		} else if (startToken instanceof LSBracketToken) {
			return other instanceof RSBracketToken;
		} else if (startToken instanceof LParenToken) {
			return other instanceof RParenToken;
		} else {
			return false;
		}
	}
	
}
