package everyos.browser.spec.jcss.cssom.property.height;

import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.cssvalue.common.Sizing;
import everyos.browser.spec.jcss.cssvalue.sizing.SizeValue;
import everyos.browser.spec.jcss.parser.CSSToken;

public class HeightPropertyParser {

	private HeightPropertyParser() {}
	
	public static HeightProperty parse(CSSToken[] cssTokens) {
		ValueParseInfo<Sizing> sizeParseInfo = SizeValue.parse(0, cssTokens);
			
		if (sizeParseInfo.failed() || sizeParseInfo.getNumberConsumedTokens() != cssTokens.length) {
			return null;
		}
		
		return new HeightProperty(sizeParseInfo.getValue().get());
	}
	
}
