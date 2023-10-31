package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.ecmaspiral.parser.exception.ParseException;
import com.github.webicitybrowser.ecmaspiral.parser.tokenizer.Tokenizer;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.PunctuatorToken;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.Token;

public class TokenizerImp implements Tokenizer {

	@Override
	public List<Token> tokenize(Reader inputReader) throws IOException, ParseException {
		List<Token> tokens = new ArrayList<>();
		TokenizerStream stream = new TokenizerStream(inputReader);

		while (stream.peek() != -1) {
			int ch = stream.peek();
			if (WhitespaceTokenizer.isWhitespace(ch)) {
				tokens.add(WhitespaceTokenizer.consumeWhitespaceToken(stream));
			} else if (NewlineTokenizer.isNewline(ch)) {
				tokens.add(NewlineTokenizer.consumeNewlineToken(stream));
			} else if (ch == '/') {
				tokens.add(consumeTokenWithSlash(stream));
			} else if (IdentifierTokenizer.isIdentifierStart(ch)) {
				tokens.add(IdentifierTokenizer.consumeIdentifierToken(stream));
			} else if (ch == '"' || ch == '\'') {
				tokens.add(StringTokenizer.consumeStringToken(stream));
			} else if (PunctuatorTokenizer.isPunctuatorStart(stream)) {
				tokens.add(PunctuatorTokenizer.consumePunctuatorToken(stream));
			} else if (NumericTokenizer.isNumericStart(ch)) {
				tokens.add(NumericTokenizer.consumeNumericToken(stream));
			} else {
				throw new IllegalStateException("Unexpected character: " + stream.peek());
			}
		}

		// TODO: Other tokens

		return tokens;
	}

	private Token consumeTokenWithSlash(TokenizerStream stream) throws IOException, ParseException {
		stream.read();
		int ch = stream.peek();

		switch (ch) {
			case '/':
				stream.unread();
				return CommentTokenizer.consumeSingleLineCommentToken(stream);
			case '*':
				stream.unread();
				return CommentTokenizer.consumeMultiLineCommentToken(stream);
			default:
				return new PunctuatorToken("/", stream.meta());
		}
	}
	
}
