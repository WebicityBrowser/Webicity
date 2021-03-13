package everyos.browser.javadom.imp;

import java.util.HashMap;
import java.util.Map;

import everyos.browser.javadom.intf.Element;
import everyos.browser.jhtml.parser.ElementFactory;

public class JDElement extends JDNode implements Element {
	private final String tagName;
	private final String localName;
	private final String namespace;
	private Map<String, String> attributes;

	public JDElement(ElementFactory factory) {
		super(factory.getDocument());
		this.tagName = factory.getTagName();
		this.localName = factory.getLocalName();
		this.namespace = factory.getNamespaceURL();
		this.attributes = new HashMap<>();
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

	@Override
	public String getAttribute(String name) {
		return attributes.get(name);
	}

	//TODO: Spec
	@Override
	public void setAttribute(String n, String v) {
		attributes.put(n, v);
	}
}
