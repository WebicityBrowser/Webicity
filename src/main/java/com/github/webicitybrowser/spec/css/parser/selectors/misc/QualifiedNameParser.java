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
		
		TokenLike token = stream.read();
		if (!(token instanceof IdentToken)) {
			fail(stream);
		}
		String name = ((IdentToken) token).getValue();
		
		return createQualifiedName(QualifiedName.NO_NAMESPACE, name);
	}
	
	private QualifiedName parseAnyNamespaceQualifiedName(TokenStream stream) throws ParseFormatException {
		if (!isGlobToken(stream.read())) {
			fail(stream);
		}
		
		if (!isBarToken(stream.read())) {
			fail(stream);
		}
		
		TokenLike token = stream.read();
		if (!(token instanceof IdentToken)) {
			fail(stream);
		}
		String elementName = ((IdentToken) token).getValue();
		
		return createQualifiedName(QualifiedName.ANY_NAMESPACE, elementName);
	}
	
	private QualifiedName parseNamespaceQualifiedToken(TokenStream stream) throws ParseFormatException {
		String namespace = QualifiedName.DEFAULT_NAMESPACE;
		
		TokenLike token = stream.read();
		if (!(token instanceof IdentToken)) {
			fail(stream);
		}
		
		String elementName = ((IdentToken) token).getValue();
		
		if (isBarToken(stream.peek())) {
			stream.read();
			namespace = elementName;
			
			token = stream.read();
			if (!(token instanceof IdentToken)) {
				fail(stream);
			}
			elementName = ((IdentToken) token).getValue();
		}
		
		return createQualifiedName(namespace, elementName);
	}

	private boolean isBarToken(TokenLike token) {
		return
			token instanceof DelimToken &&
			((DelimToken) token).getValue() == '|';
	}
	
	private boolean isGlobToken(TokenLike token) {
		return
			token instanceof DelimToken &&
			((DelimToken) token).getValue() == '*';
	}
	
	private void fail(TokenStream stream) throws ParseFormatException {
		throw new ParseFormatException("Invalid qualified name format", stream.position());
	}
	
	private QualifiedName createQualifiedName(String namespace, String name) {
		return new QualifiedName() {	
			@Override
			public String getNamespace() {
				return namespace;
			}
			
			@Override
			public String getName() {
				return name;
			}
		};
	}
	
}
