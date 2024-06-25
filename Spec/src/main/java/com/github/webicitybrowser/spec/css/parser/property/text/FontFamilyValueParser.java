package com.github.webicitybrowser.spec.css.parser.property.text;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.TokenStream;
import com.github.webicitybrowser.spec.css.parser.imp.TokenStreamImp;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.CommaToken;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.parser.tokens.StringToken;
import com.github.webicitybrowser.spec.css.property.fontfamily.FontFamilyValue;
import com.github.webicitybrowser.spec.css.property.fontfamily.FontFamilyValue.FontFamilyEntry;
import com.github.webicitybrowser.spec.css.property.fontfamily.NamedFontFamilyEntry;

public class FontFamilyValueParser implements PropertyValueParser<FontFamilyValue> {

	@Override
	public PropertyValueParseResult<FontFamilyValue> parse(TokenLike[] tokens, int offset, int length) {
		if (length == 0) return PropertyValueParseResultImp.empty();
		TokenStream tokenStream = new TokenStreamImp(tokens, offset);
		List<FontFamilyEntry> entries = new ArrayList<>();

		FontFamilyEntry fontFamilyEntry = parseFontFamilyEntry(tokenStream);
		if (fontFamilyEntry == null) return PropertyValueParseResultImp.empty();
		entries.add(fontFamilyEntry);

		while (tokenStream.peek() instanceof CommaToken) {
			tokenStream.read();
			fontFamilyEntry = parseFontFamilyEntry(tokenStream);
			if (fontFamilyEntry == null) return PropertyValueParseResultImp.empty();
			entries.add(fontFamilyEntry);
		}

		if (tokenStream.position() != offset + length) {
			return PropertyValueParseResultImp.empty();
		}

		return PropertyValueParseResultImp.of(
			(FontFamilyValue) () -> entries.toArray(FontFamilyEntry[]::new), length);
	}

	private FontFamilyEntry parseFontFamilyEntry(TokenStream tokenStream) {
		TokenLike token = tokenStream.read();
		if (token instanceof StringToken stringToken) {
			return (NamedFontFamilyEntry) () -> stringToken.getValue();
		} else if (token instanceof IdentToken identToken) {
			return (NamedFontFamilyEntry) () -> identToken.getValue();
		} else {
			return null;
		}
	}

}
