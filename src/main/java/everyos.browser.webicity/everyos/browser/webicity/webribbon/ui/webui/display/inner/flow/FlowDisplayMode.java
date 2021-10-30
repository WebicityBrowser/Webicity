package everyos.browser.webicity.webribbon.ui.webui.display.inner.flow;

import everyos.browser.spec.jcss.cssom.CSSOMNode;
import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.core.ui.WebUIManager;
import everyos.browser.webicity.webribbon.gui.Content;
import everyos.browser.webicity.webribbon.gui.WebBoxContext;
import everyos.browser.webicity.webribbon.gui.box.MutableBox;
import everyos.browser.webicity.webribbon.ui.webui.display.outer.DisplayMode;
import everyos.browser.webicity.webribbon.ui.webui.helper.ComputedChildrenHelper;

public class FlowDisplayMode implements DisplayMode {
	
	private final WebComponentUI ui;
	private final ComputedChildrenHelper computedChildrenHelper;
	
	public FlowDisplayMode(WebComponent component, WebComponentUI ui) {
		this.ui = ui;
		
		this.computedChildrenHelper = new ComputedChildrenHelper(component);
	}
	
	@Override
	public void recalculateCSSOM(CSSOMNode cssomNode, WebUIManager manager) {
		computedChildrenHelper.recompute(c->manager.get(c, ui));
		
		for (WebComponentUI c: computedChildrenHelper.getChildren()) {
			c.recalculateCSSOM(cssomNode, manager);
		}
	}

	@Override
	public void box(MutableBox box, WebBoxContext context) {
		for (WebComponentUI ui: computedChildrenHelper.getChildren()) {
			ui.box(box, context);
		}
	}

	@Override
	public Content getContent() {
		return new FlowContent();
	}

}
