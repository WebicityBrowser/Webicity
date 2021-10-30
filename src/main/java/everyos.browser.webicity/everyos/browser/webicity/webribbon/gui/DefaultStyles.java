package everyos.browser.webicity.webribbon.gui;

import java.io.IOException;
import java.io.InputStream;

import everyos.browser.spec.jcss.intf.CSSRule;
import everyos.browser.spec.jcss.parser.JCSSParser;
import everyos.browser.spec.jcss.parser.JCSSTokenizer;

public final class DefaultStyles {
	
	private static final CSSRule[] instance;
	
	static {
		InputStream input = ClassLoader.getSystemClassLoader().getResourceAsStream("css/ua.css");
		try {
			String cssString = new String(input.readAllBytes());
			input.close();
			instance = JCSSParser.parseAListOfRules(new JCSSTokenizer().createFromString(cssString));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static CSSRule[] getCSSRules() {
		return instance;
	}
	
	private DefaultStyles() {}
	
}
