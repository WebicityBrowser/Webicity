package everyos.parser.portalhtml.emit.util;

import everyos.parser.portalhtml.tokens.CharacterToken;
import everyos.parser.portalhtml.tokens.EndTagToken;
import everyos.parser.portalhtml.tokens.StartTagToken;
import everyos.parser.portalhtml.tokens.TagToken;
import everyos.parser.portalhtml.tokens.Token;
import everyos.parser.portalhtml.util.CharUtil;

public final class TokenUtil {

	private TokenUtil() {}
	
	public static boolean isWhiteSpaceToken(Token token) {
		return
			token instanceof CharacterToken &&
			CharUtil.isASCIIWhiteSpace(((CharacterToken) token).getCharacter());
	}

	public static boolean isStartTag(Token token, String... names) {
		if (!(token instanceof StartTagToken)) {
			return false;
		}
		
		return nameMatches((TagToken) token, names);
	}

	public static boolean isEndTag(Token token, String... names) {
		if (!(token instanceof EndTagToken)) {
			return false;
		}
		
		return nameMatches((TagToken) token, names);
	}
	
	private static boolean nameMatches(TagToken token, String[] names) {
		String tokenName = ((StartTagToken) token).getName();
		for (String name: names) {
			if (tokenName.hashCode() == name.hashCode() && tokenName.equals(name)) {
				return true;
			}
		}
		
		return false;
	}
	
}
