package everyos.browser.webicity.webribbon.ui.webui.helper;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;

public class ComputedChildrenHelper {
	private List<WebComponentUI> computedChildren;
	
	public ComputedChildrenHelper(WebComponent component, Function<WebComponent, WebComponentUI> func) {
		this.computedChildren = new LinkedList<>();
		for (WebComponent child: component.getChildren()) {
			computedChildren.add(func.apply(child));
		}
		
	}

	public WebComponentUI[] getChildren() {
		return computedChildren.toArray(new WebComponentUI[computedChildren.size()]);
	}
}
