package everyos.browser.javadom.imp;

import everyos.browser.javadom.intf.Element;
import everyos.browser.jhtml.parser.ElementFactory;

public class JDElement extends JDNode implements Element {
	private final String tagName;
	private final String localName;
	private final String namespace;

	public JDElement(ElementFactory factory) {
		super(factory.getDocument());
		this.tagName = factory.getTagName();
		this.localName = factory.getLocalName();
		this.namespace = factory.getNamespaceURL();
	}

	@Override
	public String getTagName() {
		return this.tagName;
	}

	@Override
	public String getLocalName() {
		return this.localName;
	}

	@Override
	public String getNamespaceURI() {
		return namespace;
	}

}
