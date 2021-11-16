package everyos.browser.webicity.webribbon.ui.webui.ui;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.WebBoxContext;
import everyos.browser.webicity.webribbon.gui.box.MutableBox;

public class WebUIWebIgnoredComponentUI extends WebUIWebComponentUIBase {

	public WebUIWebIgnoredComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
	}

	@Override
	public void recalculateCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap parent, WebUIManager manager) {
		
	}

	@Override
	public void box(MutableBox parent, WebBoxContext context) {
		
	}

}
