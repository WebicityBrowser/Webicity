package everyos.browser.spec.jcss.cssvalue.display;

import java.util.HashMap;
import java.util.Map;

import everyos.browser.spec.jcss.cssom.property.display.DisplayProperty;
import everyos.browser.spec.jcss.cssvalue.ValueParseInfo;
import everyos.browser.spec.jcss.parser.CSSToken;
import everyos.browser.spec.jcss.parser.IdentToken;

public class LegacyDisplayValue {
	
	//TODO: Finish
	private static Map<String, DisplayProperty> displayValues = new HashMap<>();
	
	public static ValueParseInfo<DisplayProperty> parse(int off, CSSToken[] cssTokens) {
		if (!(cssTokens[off] instanceof IdentToken)) {
			return ValueParseInfo.<DisplayProperty>empty();
		}
		
		String displayValueName = ((IdentToken) cssTokens[off]).getValue();
		
		DisplayProperty displayValue = displayValues.getOrDefault(displayValueName, null);
		
		if (displayValue == null) {
			return ValueParseInfo.<DisplayProperty>empty();
		} else {
			return new ValueParseInfo<DisplayProperty>(displayValue, 1);
		}
	}
	
	
	private static final String[] displayValueNames = new String[] {
		//"none",
		"contents",
		"block",
		"inline",
		"inline-block"
	};
	
	private static final DisplayProperty[] displayValueValues = new DisplayProperty[] {
		//new DisplayProperty(OuterDisplayType.NONE, InnerDisplayType.NONE),
		new DisplayProperty(OuterDisplayType.CONTENTS, InnerDisplayType.NONE),
		new DisplayProperty(OuterDisplayType.BLOCK, InnerDisplayType.FLOW),
		new DisplayProperty(OuterDisplayType.INLINE, InnerDisplayType.FLOW),
		new DisplayProperty(OuterDisplayType.INLINE, InnerDisplayType.FLOW_ROOT),
	};
	
	static {
		for (int i = 0; i < displayValueNames.length; i++) {
			displayValues.put(displayValueNames[i], displayValueValues[i]);
		}
	}
}
