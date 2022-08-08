package everyos.browser.webicity.lacewebextensions.webuilaf;

import com.github.webicity.lace.core.laf.LookAndFeel;
import com.github.webicity.lace.imputils.laf.LookAndFeelImp;

import everyos.browser.webicity.lacewebextensions.core.component.WebComponent;
import everyos.browser.webicity.lacewebextensions.webuilaf.ui.WebUIWebComponentUI;

public class WebUILookAndFeel {

	private WebUILookAndFeel() {}
	
	public static LookAndFeel createLookAndFeel(WebUIPalette palette) {
		LookAndFeel laf = LookAndFeelImp.create();
		
		return extendLookAndFeel(laf, palette);
	}
	
	public static LookAndFeel extendLookAndFeel(LookAndFeel laf, WebUIPalette palette) {
		laf.put(WebComponent.class, WebUIWebComponentUI::new);
		
		return laf;
	}
	
}
