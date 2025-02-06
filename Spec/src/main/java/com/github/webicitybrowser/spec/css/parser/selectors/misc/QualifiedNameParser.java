package com.github.webicitybrowser.spec.css.parser.selectors.misc;

import com.github.webicitybrowser.spec.css.QualifiedName;
import com.github.webicitybrowser.spec.css.parser.ParseFormatException;
import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.tokens.DelimToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;

public class QualifiedNameParser {

	public QualifiedName parse(TokenStream stream) throws ParseFormatException {
		TokenLike nextToken = stream.peek();
		if (isBarToken(nextToken)) {
			return parseNoNamespaceQualifiedName(stream);
		} else if (isGlobToken(nextToken)) {
			return parseAnyNamespaceQualifiedName(stream);
		} else if (nextToken instanceof IdentToken) {
			return parseNamespaceQualifiedToken(stream);
		}
		
		fail(stream);
		return null;
	}

	private QualifiedName parseNoNamespaceQualifiedName(TokenStream stream) throws ParseFormatException {
		if (!isBarToken(stream.read())) {
			fail(stream);
		}
		
		return parseSecondQualifiedPart(QualifiedName.NO_NAMESPACE, stream);
	}
	
	private QualifiedName parseAnyNamespaceQualifiedName(TokenStream stream) throws ParseFormatException {
		if (!isGlobToken(stream.read())) {
			fail(stream);
		}

		if (!isBarToken(stream.peek())) {
			return QualifiedName.create(QualifiedName.DEFAULT_NAMESPACE, QualifiedName.ANY_NAME);
		}
		stream.read();
		
		return parseSecondQualifiedPart(QualifiedName.ANY_NAMESPACE, stream);
	}
	
	private QualifiedName parseNamespaceQualifiedToken(TokenStream stream) throws ParseFormatException {
		String namespace = QualifiedName.DEFAULT_NAMESPACE;
		
		TokenLike token = stream.read();
		if (!(token instanceof IdentToken)) {
			fail(stream);
		}
		
		String elementName = ((IdentToken) token).value();
		
		if (isBarToken(stream.peek())) {
			stream.read();
			namespace = elementName;
			
			return parseSecondQualifiedPart(namespace, stream);
		}

		return QualifiedName.create(namespace, elementName);
	}

	private QualifiedName parseSecondQualifiedPart(String namespace, TokenStream stream) throws ParseFormatException {
		TokenLike token = stream.read();
		if (token instanceof IdentToken identToken) {
			return QualifiedName.create(namespace, identToken.value());
		} else if (isGlobToken(token)) {
			return QualifiedName.create(namespace, QualifiedName.ANY_NAME);
		} else {
			fail(stream);
			return null;
		}
	}

	private boolean isBarToken(TokenLike token) {
		return
			token instanceof DelimToken &&
			((DelimToken) token).value() == '|';
	}
	
	private boolean isGlobToken(TokenLike token) {
		return
			token instanceof DelimToken &&
			((DelimToken) token).value() == '*';
	}
	
	private void fail(TokenStream stream) throws ParseFormatException {
		throw new ParseFormatException("Invalid qualified name format", stream.position());
	}
	
}
