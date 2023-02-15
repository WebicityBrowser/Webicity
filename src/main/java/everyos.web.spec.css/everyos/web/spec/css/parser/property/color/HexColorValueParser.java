package everyos.web.spec.css.parser.property.color;

import everyos.web.spec.css.parser.TokenLike;
import everyos.web.spec.css.parser.property.PropertyValueParseResult;
import everyos.web.spec.css.parser.property.PropertyValueParser;
import everyos.web.spec.css.parser.property.imp.PropertyValueParseResultImp;
import everyos.web.spec.css.parser.tokens.HashToken;
import everyos.web.spec.css.property.color.ColorValue;

public class HexColorValueParser implements PropertyValueParser<ColorValue> {

	@Override
	public PropertyValueParseResult<ColorValue> parse(TokenLike[] tokens, int offset, int length) {
		if (!checkSelectorFormat(tokens, offset, length)) {
			return PropertyValueParseResultImp.empty();
		}
		
		String colorHash = ((HashToken) tokens[offset]).getValue();
		try {
			ColorValue color = parseColor(colorHash);
			return PropertyValueParseResultImp.of(color, 1);
		} catch (NumberFormatException e) {
			return PropertyValueParseResultImp.empty();
		}
	}
	
	private ColorValue parseColor(String colorHash) {
		switch (colorHash.length()) {
		case 6:
			return parse6DigitColor(colorHash);
		case 8:
			return parse8DigitColor(colorHash);
		case 3:
			return parse3DigitColor(colorHash);
		case 4:
			return parse4DigitColor(colorHash);
		default:
			return null;
		}
	}

	private ColorValue parse6DigitColor(String colorHash) {
		return new RGBColorValueImp(
			extractHexNumber(colorHash, 0, 2),
			extractHexNumber(colorHash, 2, 2),
			extractHexNumber(colorHash, 4, 2),
			255);
	}
	
	private ColorValue parse8DigitColor(String colorHash) {
		return new RGBColorValueImp(
			extractHexNumber(colorHash, 0, 2),
			extractHexNumber(colorHash, 2, 2),
			extractHexNumber(colorHash, 4, 2),
			extractHexNumber(colorHash, 6, 2));
	}
	
	private ColorValue parse3DigitColor(String colorHash) {
		return new RGBColorValueImp(
			extractHexNumber(colorHash, 0, 1),
			extractHexNumber(colorHash, 1, 1),
			extractHexNumber(colorHash, 2, 1),
			255);
	}
	
	private ColorValue parse4DigitColor(String colorHash) {
		return new RGBColorValueImp(
			extractHexNumber(colorHash, 0, 1),
			extractHexNumber(colorHash, 1, 1),
			extractHexNumber(colorHash, 2, 1),
			extractHexNumber(colorHash, 3, 1));
	}
	
	private int extractHexNumber(String hash, int position, int length) {
		int total = 0;
		for (int i = position; i < position + length; i++) {
			total <<= 4;
			total += fromHex(hash.charAt(i));
		}
		
		return total;
	}
	
	private int fromHex(char ch) {
		if (ch >= '0' && ch <= '9') {
			return ch - '0';
		} else if (ch >= 'a' && ch <= 'f') {
			return ch - 'a' + 10;
		} else if (ch >= 'A' && ch <= 'F') {
			return ch - 'A' + 10;
		}
		
		throw new NumberFormatException("Could not convert character '" + ch + "' from hex!");
	}

	private boolean checkSelectorFormat(TokenLike[] tokens, int offset, int length) {
		return (length == 1 && tokens[offset] instanceof HashToken);
	}

}
