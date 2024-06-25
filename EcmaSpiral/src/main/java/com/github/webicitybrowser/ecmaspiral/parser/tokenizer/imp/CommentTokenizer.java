package com.github.webicitybrowser.ecmaspiral.parser.tokenizer.imp;

import java.io.IOException;

import com.github.webicitybrowser.ecmaspiral.parser.exception.ParseException;
import com.github.webicitybrowser.ecmaspiral.parser.tokens.CommentToken;

public final class CommentTokenizer {
	
	private CommentTokenizer() {}

	public static CommentToken consumeSingleLineCommentToken(TokenizerStream stream) throws IOException, ParseException {
		if (stream.read() != '/' || stream.read() != '/') {
			throw new ParseException("Tokenize Error: Expected //", stream.meta());
		}

		StringBuilder comment = new StringBuilder("//");
		while (stream.peek() != -1 && !NewlineTokenizer.isNewline(stream.peek())) {
			comment.appendCodePoint(stream.read());
		}

		return new CommentToken(comment.toString(), stream.meta());
	}

	public static CommentToken consumeMultiLineCommentToken(TokenizerStream stream) throws IOException, ParseException {
		if (stream.read() != '/' || stream.read() != '*') {
			throw new IllegalStateException("Tokenize Error: Expected /*");
		}

		StringBuilder comment = new StringBuilder("/*");
		while (stream.peek() != -1) {
			int ch = stream.read();
			if (ch == '*' && stream.peek() == '/') {
				stream.read();
				comment.append("*/");
				return new CommentToken(comment.toString(), stream.meta());
			}

			comment.appendCodePoint(ch);
		}

		throw new ParseException("Tokenize Error: Expected */", stream.meta());
	}

}
