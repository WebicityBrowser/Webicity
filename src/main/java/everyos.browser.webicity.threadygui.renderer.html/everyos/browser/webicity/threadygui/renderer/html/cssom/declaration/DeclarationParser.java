package everyos.browser.webicity.threadygui.renderer.html.cssom.declaration;

import everyos.desktop.thready.core.gui.directive.Directive;
import everyos.web.spec.css.parser.TokenLike;
import everyos.web.spec.css.parser.tokens.WhitespaceToken;
import everyos.web.spec.css.rule.Declaration;

public final class DeclarationParser {

	private DeclarationParser() {}

	public static Directive parseDeclaration(Declaration declaration) {
		TokenLike[] tokens = removeExtraWhitespace(declaration.getValue());
		
		// TODO: Handle inherit, etc
		switch (declaration.getName()) {
		case "color":
			return ColorDeclarationParser.parse(tokens);
		default:
			return null;
		}
	}

	private static TokenLike[] removeExtraWhitespace(TokenLike[] value) {
		int readPos = 0;
		int resultLength = 0;
		boolean ignoreWhitespace = true;
		
		for (int insertPos = 0; readPos < value.length;) {
			if (value[readPos] instanceof WhitespaceToken && ignoreWhitespace) {
				readPos++;
			} else {
				ignoreWhitespace = value[readPos] instanceof WhitespaceToken;
				if (!ignoreWhitespace) {
					resultLength = insertPos + 1;
				}
				
				value[insertPos++] = value[readPos++];
			}
		}
		
		TokenLike[] finalTokens = new TokenLike[resultLength];
		System.arraycopy(value, 0, finalTokens, 0, resultLength);
		
		return finalTokens;
	}
	
}
