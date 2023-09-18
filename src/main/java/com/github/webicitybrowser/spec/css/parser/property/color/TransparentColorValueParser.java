package com.github.webicitybrowser.spec.css.parser.property.color;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.property.color.ColorValue;

public class TransparentColorValueParser implements PropertyValueParser<ColorValue> {

	@Override
	public PropertyValueParseResult<ColorValue> parse(TokenLike[] tokens, int offset, int length) {
		if (length > 0 && tokens[offset].toString().equals("transparent")) {
			return PropertyValueParseResultImp.of(new RGBColorValueImp(0, 0, 0, 0), 1);
		}
		
		return PropertyValueParseResultImp.empty();
	}

}
