package everyos.browser.webicity.webribbon.ui.webui.ui.content.text;

import everyos.browser.spec.javadom.intf.Text;
import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.WebBoxContext;
import everyos.browser.webicity.webribbon.gui.box.stage.BoxingStageBox;
import everyos.browser.webicity.webribbon.gui.box.stage.MultiBox;
import everyos.browser.webicity.webribbon.ui.webui.rendering.box.InlineLevelBoxImp;
import everyos.browser.webicity.webribbon.ui.webui.ui.WebUIWebComponentUIBase;

public class WebUIWebTextComponentUI extends WebUIWebComponentUIBase {
	
	//TODO: A variety of specs

	public WebUIWebTextComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
	}

	@Override
	public void recalculateCSSOM(CSSOMNode cssomNode, ApplicablePropertyMap parent, WebUIManager manager) {
		
	}

	@Override
	public void box(BoxingStageBox parent, WebBoxContext context) {
		String text = ((Text) getComponent().getNode()).getWholeText();
		
		//TODO: Are textboxes just inline-level boxes?
		MultiBox box = new InlineLevelBoxImp(parent);
		box.setContent(new TextBoxContent(text));
		box.finish();
	}
	
}
