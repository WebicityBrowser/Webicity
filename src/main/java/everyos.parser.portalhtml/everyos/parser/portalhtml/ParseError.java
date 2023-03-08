package everyos.parser.portalhtml;

public enum ParseError {
	
	GENERIC,
	UNEXPECTED_NULL_CHARACTER,
	UNEXPECTED_QUESTION_MARK_INSTEAD_OF_TAG_NAME,
	EOF_BEFORE_TAG_NAME,
	INVALID_FIRST_CHARACTER_OF_TAG_NAME,
	MISSING_END_TAG_NAME,
	EOF_IN_TAG,
	EOF_IN_SCRIPT_HTML_COMMENT_LIKE_TEXT,
	UNEXPECTED_EQUALS_SIGN_BEFORE_ATTRIBUTE_NAME,
	UNEXPECTED_CHARACTER_IN_ATTRIBUTE_NAME,
	MISSING_ATTRIBUTE_VALUE,
	UNEXPECTED_CHARACTER_IN_UNQUOTED_ATTRIBUTE_VALUE,
	MISSING_WHITESPACE_BETWEEN_ATTRIBUTES,
	UNEXPECTED_SOLIDUS_IN_TAG,
	INCORRECTLY_OPENED_COMMENT,
	ABRUPT_CLOSING_OF_EMPTY_COMMENT,
	EOF_IN_COMMENT,
	NESTED_COMMENT,
	INCORRECTLY_CLOSED_COMMENT,
	EOF_IN_DOCTYPE,
	MISSING_WHITESPACE_BEFORE_DOCTYPE_NAME,
	MISSING_DOCTYPE_NAME,
	MISSING_SEMICOLON_AFTER_CHARACTER_REFERENCE,
	UNKNOWN_NAMED_CHARACTER_REFERENCE,
	ABSENCE_OF_DIGITS_IN_NUMERIC_CHARACTER_REFERENCE

}
