package everyos.browser.webicity.renderer.html.dom.impl;

import everyos.browser.webicity.renderer.html.dom.Attr;
import everyos.browser.webicity.renderer.html.dom.DOMTokenList;
import everyos.browser.webicity.renderer.html.dom.Element;
import everyos.browser.webicity.renderer.html.dom.HTMLCollection;
import everyos.browser.webicity.renderer.html.dom.NamedNodeMap;
import everyos.browser.webicity.renderer.html.dom.ShadowRoot;

public class ElementImpl extends NodeImpl implements Element {
	private String namespaceURI;
	private String localName;
	private String prefix;
	private String is;
	private String tagName;

	public ElementImpl(DocumentImpl nodeDocument) {
		super(nodeDocument);
	}
	
	///

	@Override public Element getParentElement() {
		return null;
	}

	@Override public String getNamespaceURI() {
		return namespaceURI;
	}

	@Override public String getPrefix() {
		return prefix;
	}

	@Override public String getLocalName() {
		return localName;
	}

	@Override public String getTagName() {
		return tagName;
	}

	@Override public String getId() {
		return null;
	}

	@Override public String getClassName() {
		return null;
	}

	@Override public DOMTokenList getClassList() {
		return null;
	}

	@Override public String getSlot() {
		return null;
	}

	@Override public String setSlot() {
		return null;
	}

	@Override public boolean hasAttributes() {
		return false;
	}

	@Override public NamedNodeMap getAttributes() {
		return null;
	}

	@Override public String[] getAttributeNames() {
		return null;
	}

	@Override public String getAttribute(String qualifiedName) {
		return null;
	}

	@Override public String getAttributeNS(String namespace, String localName) {
		return null;
	}

	@Override public void setAttribute(String qualifiedName, String value) {
		
	}

	@Override public void setAttributeNS(String namespace, String qualifiedName, String value) {
		
	}

	@Override public void removeAttribute(String qualifiedName) {
		
	}

	@Override public void removeAttributeNS(String namespace, String localName) {
		
	}

	@Override public boolean toggleAttribute(String qualifiedName, boolean force) {
		return false;
	}

	@Override public boolean hasAttribute(String qualifiedName) {
		return false;
	}

	@Override public boolean hasAttributeNS(String namespace, String localName) {
		return false;
	}

	@Override public Attr getAttributeNode(String qualifiedName) {
		return null;
	}

	@Override public Attr getAttributeNodeNS(String namespace, String localName) {
		return null;
	}

	@Override public Attr setAttributeNode(Attr attr) {
		return null;
	}

	@Override public Attr setAttributeNodeNS(Attr attr) {
		return null;
	}

	@Override public Attr removeAttributeNode(Attr attr) {
		return null;
	}

	@Override public ShadowRoot attachShadow() {
		return null;
	}

	@Override public ShadowRoot getShadowRoot() {
		return null;
	}

	@Override public Element closest(String selectors) {
		return null;
	}

	@Override public boolean matches(String selectors) {
		return false;
	}

	@Override public boolean webkitMatchesSelector(String selectors) {
		return false;
	}

	@Override public HTMLCollection getElementsByTagName(String qualifiedName) {
		return null;
	}

	@Override public HTMLCollection getElementsByTagNameNS(String namespace, String localName) {
		return null;
	}

	@Override public HTMLCollection getElementsByClassName(String classNames) {
		return null;
	}

	@Override public Element insertAdjacentElement(String where, Element element) {
		return null;
	}

	@Override public void insertAdjacentText(String where, String data) {
		
	}

	
	//Implementation specific
	@Override public void setNamespaceURI(String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}

	@Override public void setLocalName(String localName) {
		this.localName = localName;
	}

	@Override public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	@Override public void setIs(String is) {
		this.is = is;
	}

	@Override public void setTagName(String tagName) {
		this.tagName = tagName;
	}	
}
