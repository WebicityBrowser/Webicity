package everyos.web.spec.css.parser;

import everyos.web.spec.css.parser.tokens.Token;
import everyos.web.spec.css.rule.CSSRule;

public interface CSSParser {

	// TODO: Other entry points
	
	CSSRule[] parseAListOfRules(Token[] tokens);
	
}
