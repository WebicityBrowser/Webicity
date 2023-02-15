package everyos.browser.webicity.threadygui.renderer.html.cssom;

import everyos.browser.webicity.threadygui.renderer.html.component.WebComponent;
import everyos.desktop.thready.core.gui.directive.DirectivePool;

public interface CSSOMNode {
	
	CSSOMNode getChild(CSSOMFilter filter);

	CSSOMNode[] getApplicableNodes(WebComponent component, int position);

	DirectivePool getDirectivePool();

}
