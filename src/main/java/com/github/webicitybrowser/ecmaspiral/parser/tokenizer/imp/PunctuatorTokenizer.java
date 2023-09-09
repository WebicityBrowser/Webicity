package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp;

import java.io.IOException;
import java.util.List;

import com.github.webicitybrowser.ecmaspiral.parser.exception.ParseException;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.PunctuatorToken;

public final class PunctuatorTokenizer {

	private static final List<String> PUNCTUATORS = List.of(
		"{", "(", ")", "[", "]", ".", "...", ";", ",", "<", ">", "<=", ">=", "==", "!=",
		"===", "!==", "+", "-", "*", "%", "**", "++", "--", "<<", ">>", ">>>", "$", "|", "^",
		"!", "~", "&&", "||", "??", "?", ":", "=", "+=", "-=", "*=", "%=", "**=", "<<=", ">>=",
		">>>=", "&=", "|=", "^=", "&&=", "||=", "??=", "=>");
	
	private PunctuatorTokenizer() {}

	public static PunctuatorToken consumePunctuatorToken(TokenizerStream stream) throws IOException, ParseException {
		String nextChars = stream.peek(4);
		String punctuator = PUNCTUATORS.stream()
			.filter(p -> p.startsWith(nextChars))
			.reduce((a, b) -> a.length() > b.length() ? a : b)
			.orElseThrow(() -> new ParseException("Tokeize Error: No punctuator found", stream.meta()));
		stream.read(punctuator.length());

		return new PunctuatorToken(punctuator, stream.meta());
	}

	public static boolean isPunctuatorStart(TokenizerStream stream) throws IOException {
		String nextChars = stream.peek(4);
		return PUNCTUATORS.stream().anyMatch(p -> p.startsWith(nextChars));
	}

}
