package everyos.browser.javadom.imp;

import everyos.browser.javadom.intf.Element;
import everyos.browser.jhtml.ElementFactory;

public class JDElement extends JDNode implements Element {
	private String tagName;
	private String localName;

	public JDElement(ElementFactory factory) {
		super(factory.getDocument());
		this.tagName = factory.getTagName();
		this.localName = factory.getLocalName();
	}

	@Override
	public String getTagName() {
		return this.tagName;
	}

	@Override
	public String getLocalName() {
		return this.localName;
	}

}
