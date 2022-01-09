package everyos.browser.webicity.webribbon.ui.webui.display.outer;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebBoxContext;
import everyos.browser.webicity.webribbon.gui.box.stage.BoxingStageBox;

public interface DisplayMode {
	
	void recalculateCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap properties, WebUIManager manager);
	void box(BoxingStageBox parent, WebBoxContext context);
	Content getContent();

}
