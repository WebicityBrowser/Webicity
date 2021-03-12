package everyos.browser.jhtml.imp;

import everyos.browser.javadom.intf.Text;
import everyos.browser.jhtml.intf.HTMLTitleElement;
import everyos.browser.jhtml.parser.ElementFactory;

public class JHTMLHTMLTitleElement extends JHTMLHTMLElement implements HTMLTitleElement {

	public JHTMLHTMLTitleElement(ElementFactory factory) {
		super(factory);
	}

	@Override
	public String getText() {
		if (isEmpty(getChildren())) return null;
		return ((Text) getChildren().get(0)).getData();
	}

}
