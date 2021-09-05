package everyos.browser.spec.jhtml.imp;

import everyos.browser.spec.javadom.intf.Text;
import everyos.browser.spec.jhtml.intf.HTMLTitleElement;
import everyos.browser.spec.jhtml.parser.ElementFactory;

public class JHTMLTitleElement extends JHTMLElement implements HTMLTitleElement {

	public JHTMLTitleElement(ElementFactory factory) {
		super(factory);
	}

	@Override
	public String getText() {
		if (isEmpty(getChildren())) return null;
		return ((Text) getChildren().get(0)).getData();
	}

}
