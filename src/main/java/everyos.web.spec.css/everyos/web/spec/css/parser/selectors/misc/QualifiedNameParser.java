package everyos.web.spec.css.parser.selectors.misc;

import everyos.web.spec.css.QualifiedName;
import everyos.web.spec.css.parser.ParseFormatException;
import everyos.web.spec.css.parser.tokens.DelimToken;
import everyos.web.spec.css.parser.tokens.IdentToken;
import everyos.web.spec.css.parser.tokens.Token;

public class QualifiedNameParser {

	public QualifiedName parse(Token[] tokens, int offset, int length) throws ParseFormatException {
		checkSelectorFormat(tokens, offset, length);
		
		switch(length) {
		case 1:
			return parseDefaultQualifiedName(tokens, offset);
		case 2:
			return parseNoQualifiedName(tokens, offset);
		case 3:
			return parseNamespaceSelector(tokens, offset);
		default:
			throw new ParseFormatException("Invalid qualified name format", offset);
		}
	}

	private void checkSelectorFormat(Token[] tokens, int offset, int length) throws ParseFormatException {
		if (length == 0) {
			throw new ParseFormatException("Invalid qualified name format", offset);
		}
	}
	
	private QualifiedName parseDefaultQualifiedName(Token[] tokens, int offset) throws ParseFormatException {
		String name = parseElementName(tokens, offset);
		
		return createQualifiedName(QualifiedName.DEFAULT_NAMESPACE, name);
	}
	
	private QualifiedName parseNoQualifiedName(Token[] tokens, int offset) throws ParseFormatException {
		checkIsBarToken(tokens, offset);
		String name = parseElementName(tokens, offset + 1);
		
		return createQualifiedName(QualifiedName.NO_NAMESPACE, name);
	}
	
	private QualifiedName parseNamespaceSelector(Token[] tokens, int offset) throws ParseFormatException {
		if (tokens[offset] instanceof IdentToken) {
			return parseNamedNamespaceSelector(tokens, offset);
		} else if (isGlobToken(tokens, offset)) {
			return parseAnyNamespaceSelector(tokens, offset);
		} else {
			throw new ParseFormatException("Invalid qualified name format", offset);
		}
	}
	
	private QualifiedName parseNamedNamespaceSelector(Token[] tokens, int offset) throws ParseFormatException {
		checkIsBarToken(tokens, offset + 1);
		String namespace = ((IdentToken) tokens[offset]).getValue();
		String name = parseElementName(tokens, offset + 2);
		
		return createQualifiedName(namespace, name);
	}
	
	private QualifiedName parseAnyNamespaceSelector(Token[] tokens, int offset) throws ParseFormatException {
		checkIsBarToken(tokens, offset + 1);
		String name = parseElementName(tokens, offset + 2);
		
		return createQualifiedName(QualifiedName.ANY_NAMESPACE, name);
	}

	private void checkIsBarToken(Token[] tokens, int offset) throws ParseFormatException {
		if (!isBarToken(tokens, offset)) {
			throw new ParseFormatException("Invalid qualified name format", offset);
		}
	}

	private String parseElementName(Token[] tokens, int offset) throws ParseFormatException {
		if (tokens[offset] instanceof IdentToken) {
			return ((IdentToken) tokens[offset]).getValue();
		} else if (isGlobToken(tokens, offset)) {
			return QualifiedName.ANY_NAME;
		} else {
			throw new ParseFormatException("Invalid qualified name format", offset);
		}
	}
	
	private boolean isBarToken(Token[] tokens, int offset) {
		return
			tokens[offset] instanceof DelimToken &&
			((DelimToken) tokens[offset]).getValue() == '|';
	}
	
	private boolean isGlobToken(Token[] tokens, int offset) {
		return
			tokens[offset] instanceof DelimToken &&
			((DelimToken) tokens[offset]).getValue() == '*';
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
