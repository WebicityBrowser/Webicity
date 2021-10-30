package everyos.browser.webicity.webribbon.ui.webui.ui.text;

import everyos.browser.spec.javadom.intf.Text;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.WebBoxContext;
import everyos.browser.webicity.webribbon.gui.box.MutableBox;
import everyos.browser.webicity.webribbon.ui.webui.ui.WebUIWebComponentUIBase;

public class WebUIWebTextComponentUI extends WebUIWebComponentUIBase {
	
	//TODO: A variety of specs

	public WebUIWebTextComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
	}

	@Override
	public void recalculateCSSOM(CSSOMNode cssomNode, WebUIManager manager) {
		
	}

	@Override
	public void box(MutableBox parent, WebBoxContext context) {
		String text = ((Text) getComponent().getNode()).getWholeText();
		
		parent.add(new TextBox(text));
	}
	
}
