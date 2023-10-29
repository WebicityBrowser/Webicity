package com.github.webicitybrowser.spec.css.parser.property.text;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.tokens.IdentToken;
import com.github.webicitybrowser.spec.css.property.text.LineBreakValue;

public class LineBreakValueParser implements PropertyValueParser<LineBreakValue> {

	@Override
	public PropertyValueParseResult<LineBreakValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkValueFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}

		return switch (((IdentToken) tokens[offset]).getValue()) {
			case "auto" -> PropertyValueParseResultImp.of(LineBreakValue.AUTO, 1);
			case "loose" -> PropertyValueParseResultImp.of(LineBreakValue.LOOSE, 1);
			case "normal" -> PropertyValueParseResultImp.of(LineBreakValue.NORMAL, 1);
			case "strict" -> PropertyValueParseResultImp.of(LineBreakValue.STRICT, 1);
			case "anywhere" -> PropertyValueParseResultImp.of(LineBreakValue.ANYWHERE, 1);
			default -> PropertyValueParseResultImp.empty();
		};
	}
	
	private boolean checkValueFormat(TokenLike[] tokens, int offset, int length) {
		return length > 0 && tokens[offset] instanceof IdentToken;
	}
	
}