package com.github.webicitybrowser.spec.css.parser.property.background;

import java.util.List;

import com.github.webicitybrowser.spec.css.parser.TokenLike;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParseResult;
import com.github.webicitybrowser.spec.css.parser.property.PropertyValueParser;
import com.github.webicitybrowser.spec.css.parser.property.imp.PropertyValueParseResultImp;
import com.github.webicitybrowser.spec.css.parser.property.shared.EnumParser;
import com.github.webicitybrowser.spec.css.parser.property.shared.ListParser;
import com.github.webicitybrowser.spec.css.property.background.BackgroundAttachmentValue;
import com.github.webicitybrowser.spec.css.property.background.BackgroundAttachmentLayersValue;

public class BackgroundAttachmentValueParser implements PropertyValueParser<BackgroundAttachmentLayersValue> {

	private static final ListParser<BackgroundAttachmentValue> listParser = new ListParser<>(new BgAttachmentValueParser());

	@Override
	public PropertyValueParseResult<BackgroundAttachmentLayersValue> parse(TokenLike[] tokens, int offset, int length) {
		List<BackgroundAttachmentValue> result = listParser.parseList(tokens, offset, length);
		if (result.isEmpty()) {
			return PropertyValueParseResultImp.empty();
		}

		return PropertyValueParseResultImp.of(new BackgroundAttachmentLayersValue(result), result.size());
	}
	
	public static class BgAttachmentValueParser implements PropertyValueParser<BackgroundAttachmentValue> {
		
		private final EnumParser<BackgroundAttachmentValue> enumParser = new EnumParser<>(
			new String[] { "scroll", "fixed", "local" },
			BackgroundAttachmentValue.values());

		@Override
		public PropertyValueParseResult<BackgroundAttachmentValue> parse(TokenLike[] tokens, int offset, int length) {
			return enumParser.parse(tokens, offset, length);
		}
		
	}

}
