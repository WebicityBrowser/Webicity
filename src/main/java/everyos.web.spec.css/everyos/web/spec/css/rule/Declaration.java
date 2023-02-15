package everyos.web.spec.css.rule;

import everyos.web.spec.css.parser.TokenLike;

public interface Declaration extends CSSRule {

	String getName();

	TokenLike[] getValue();

	boolean isImportant();

}
