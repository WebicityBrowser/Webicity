package everyos.browser.spec.jcss.cssom.property.width;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.cssvalue.sizing.SizeValue;
import everyos.browser.spec.jcss.cssvalue.sizing.Sizing;
import everyos.browser.spec.jcss.parser.CSSToken;

public class WidthPropertyParser {

	private WidthPropertyParser() {}
	
	public static WidthProperty parse(CSSToken[] cssTokens) {
		ValueParseInfo<Sizing> sizeParseInfo = SizeValue.parse(0, cssTokens);
			
		if (sizeParseInfo.failed() || sizeParseInfo.getNumberConsumedTokens() != cssTokens.length) {
			return null;
		}
		
		return new WidthProperty(sizeParseInfo.getValue().get());
	}
	
}
