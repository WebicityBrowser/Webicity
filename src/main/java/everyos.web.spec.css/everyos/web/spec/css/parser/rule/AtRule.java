package everyos.web.spec.css.parser.rule;

import everyos.web.spec.css.parser.componentvalue.ComponentValue;
import everyos.web.spec.css.parser.componentvalue.SimpleBlock;
import everyos.web.spec.css.rule.CSSRule;

public interface AtRule extends CSSRule {

	String getName();
	
	ComponentValue[] getPrelude();
	
	SimpleBlock getValue();
	
}
