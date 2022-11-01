package everyos.web.spec.css.rule;

import everyos.web.spec.css.componentvalue.SimpleBlock;
import everyos.web.spec.css.parser.TokenLike;

public interface AtRule extends CSSRule {

	String getName();
	
	TokenLike[] getPrelude();
	
	SimpleBlock getValue();
	
}
