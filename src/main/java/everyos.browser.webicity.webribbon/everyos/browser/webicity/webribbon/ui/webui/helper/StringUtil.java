package everyos.browser.webicity.webribbon.ui.webui.helper;

import everyos.engine.ribbon.core.graphics.font.RibbonFontMetrics;

public class StringUtil {
	
	public static int fastStringWidth(RibbonFontMetrics font, String str) {
		int maxWidth = 0;
		for (byte ch: str.getBytes()) {
			maxWidth += font.getCharWidth((char) ch); 
		}
		
		return maxWidth;
	}
	
	public static int stringWidth(RibbonFontMetrics font, String str) {
		int width = 0;
		for (String spl: str.split("\n")) {
			int currentLineWidth = 0;
			for (byte ch: spl.getBytes()) {
				currentLineWidth += font.getCharWidth((char) ch); 
			}
			width = Math.max(currentLineWidth, width);
		}
		return width;
	}
	
}
