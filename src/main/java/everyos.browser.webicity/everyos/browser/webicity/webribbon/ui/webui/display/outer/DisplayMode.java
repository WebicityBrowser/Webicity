package everyos.browser.webicity.webribbon.ui.webui.display.outer;

import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebBoxContext;
import everyos.browser.webicity.webribbon.gui.box.MutableBox;

public interface DisplayMode {
	
	void recalculateCSSOM(CSSOMNode cssomNode, WebUIManager manager);
	void box(MutableBox parent, WebBoxContext context);
	Content getContent();

}
