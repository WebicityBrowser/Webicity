package everyos.browser.spec.javadom.imp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import everyos.browser.spec.javadom.intf.DOMTokenList;
import everyos.browser.spec.javadom.intf.Element;
import everyos.browser.spec.jhtml.parser.ElementFactory;

public class JDElement extends JDNode implements Element {
	private final String tagName;
	private final String localName;
	private final String namespace;
	private final Map<String, String> attributes;
	
	private List<String> classes;

	public JDElement(ElementFactory factory) {
		super(factory.getDocument());
		this.tagName = factory.getTagName();
		this.localName = factory.getLocalName();
		this.namespace = factory.getNamespaceURL();
		this.attributes = new HashMap<>();
	}
	
	@Override
	public String getNamespaceURI() {
		return namespace;
	}

	@Override
	public String getLocalName() {
		return this.localName;
	}
	
	@Override
	public String getTagName() {
		return this.tagName;
	}
	
	@Override
	public String getId() {
		return getAttribute("id");
	}

	@Override
	public DOMTokenList getClassList() {
		if (this.classes == null) {
			String fullClass = getAttribute("class");
			if (fullClass == null) {
				fullClass = "";
			}
			
			this.classes = new ArrayList<>();
			for (String val: fullClass.split(" ")) {
				this.classes.add(val);
			}
		}
		
		return new JDDOMTokenList(this.classes);
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
