package everyos.browser.webicity.threadygui.renderer.html.cssom;

import everyos.browser.webicity.threadygui.renderer.html.component.WebComponent;

public interface CSSOMFilter {

	boolean isApplicable(WebComponent component, int index);
	
}
