package everyos.browser.webicity.webribbon.ui.webui.display.outer;

import everyos.browser.spec.jcss.cssom.ApplicablePropertyMap;
import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebBoxContext;
import everyos.browser.webicity.webribbon.gui.box.MutableBox;
import everyos.browser.webicity.webribbon.ui.webui.display.inner.flow.FlowDisplayMode;
import everyos.browser.webicity.webribbon.ui.webui.display.inner.none.EmptyContent;

public class ContentsDisplayMode implements DisplayMode {
	
	private final DisplayMode innerDisplayMode;
	
	public ContentsDisplayMode(WebComponent component, WebComponentUI ui) {
		this.innerDisplayMode = new FlowDisplayMode(component, ui);
	}
	
	@Override
	public void recalculateCSSOM(CSSOMNode cssomNode,ApplicablePropertyMap parent,  WebUIManager manager) {
		innerDisplayMode.recalculateCSSOM(cssomNode, parent, manager);
	}

	@Override
	public void box(MutableBox parent, WebBoxContext context) {
		innerDisplayMode.box(parent, context);
	}

	@Override
	public Content getContent() {
		return new EmptyContent();
	}
	
}
