package everyos.browser.jcss.parser;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import everyos.browser.webicity.net.protocol.http.NIOReader;

public class CSSParser {
	public CSSToken[] createFromString(String input) {
		//TODO: Switch to a proper input stream
		input = input
			.replace("/r/n", "/n")
			.replace("/f",   "/n")
			.replace("/r",   "/n");
		
		return createFromStream(new StringReader(input));
	}
	
	public CSSToken[] createFromStream(Reader istream) {
		//TODO: Replace code points
		
		NIOReader stream = new NIOReader(istream, 32);
		
		ArrayList<CSSToken> tokens = new ArrayList<>();
		
		while (!stream.finished()) {
			//TODO: Make NIO be actually useful
			CSSToken result = null;
			try {
				result = consumeAToken(stream);
			} catch (IOException e) {
				e.printStackTrace();
			}
			tokens.add(result);
		}
		
		return tokens.toArray(new CSSToken[tokens.size()]);
	}

	private static CSSToken consumeAToken(NIOReader reader) throws IOException {
		//TODO: Consume comments
		
		char ch = (char) reader.read();
		if (Character.isWhitespace(ch)) {
			while (Character.isWhitespace(reader.read()));
			return new WhitespaceToken();
		} else if (ch=='"') {
			return consumeAStringToken(reader, '"');
		} else if (ch=='#') {
			if (isANameCodepoint(reader.peek(1)[0]) || isAValidEscape(String.valueOf(reader.peek(2)))) {
				
				HashToken token = new HashToken();
				if (wouldStartAnIdentifier(String.valueOf(reader.peek(3)))) {
					token.setType(TokenType.ID);
				}
				String name = consumeAName(reader);
				token.setValue(name);
				return token;
			} else {
				DelimToken token = new DelimToken();
				token.setValue(String.valueOf(ch));
				return token;
			}
		} else if (isNameStartCodepoint(ch)) { //TODO: Skipped a few
			
		}
		return null;
	}
	
	private static boolean isNameStartCodepoint(char ch) {
		return Character.isAlphabetic(ch) || ch=='_';
	}

	private static String consumeAName(NIOReader reader) {
		// TODO Auto-generated method stub
		return null;
	}

	private static boolean wouldStartAnIdentifier(String substring) {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean isAValidEscape(String substring) {
		return substring.charAt(0)=='\\' && substring.charAt(1)!='\n';
	}

	private static boolean isANameCodepoint(char ch) {
		return isANameStartCodepoint(ch) || Character.isDigit(ch) || ch=='-';
	}

	private static boolean isANameStartCodepoint(char ch) {
		return Character.isLetter(ch) || ch>='\u0080' || ch=='_';
	}

	private static StringToken consumeAStringToken(NIOReader reader, char endingCodePoint) {
		// TODO Auto-generated method stub
		return null;
	}
}
