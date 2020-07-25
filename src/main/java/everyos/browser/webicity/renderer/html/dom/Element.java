package everyos.browser.webicity.renderer.html.dom;

public interface Element extends Node {
	String getNamespaceURI();
	String getPrefix();
	String getLocalName();
	String getTagName();

	String getId();
	String getClassName();
	DOMTokenList getClassList();
	String getSlot();
	String setSlot();

	boolean hasAttributes();
	NamedNodeMap getAttributes();
	String[] getAttributeNames();
	String getAttribute(String qualifiedName);
	String getAttributeNS(String namespace, String localName);
	void setAttribute(String qualifiedName, String value);
	void setAttributeNS(String namespace, String qualifiedName, String value);
	void removeAttribute(String qualifiedName);
	void removeAttributeNS(String namespace, String localName);
	boolean toggleAttribute(String qualifiedName, boolean force);
	boolean hasAttribute(String qualifiedName);
	boolean hasAttributeNS(String namespace, String localName);

	Attr getAttributeNode(String qualifiedName);
	Attr getAttributeNodeNS(String namespace, String localName);
	Attr setAttributeNode(Attr attr);
	Attr setAttributeNodeNS(Attr attr);
	Attr removeAttributeNode(Attr attr);

	ShadowRoot attachShadow(/*ShadowRootInit init*/);//TODO
	ShadowRoot getShadowRoot();

	Element closest(String selectors);
	boolean matches(String selectors);
	boolean webkitMatchesSelector(String selectors);

	HTMLCollection getElementsByTagName(String qualifiedName);
	HTMLCollection getElementsByTagNameNS(String namespace, String localName);
	HTMLCollection getElementsByClassName(String classNames);

	Element insertAdjacentElement(String where, Element element);
	void insertAdjacentText(String where, String data);
	
	//Implementation specific
	void setNamespaceURI(String namespace);
	void setLocalName(String localName);
	void setPrefix(String prefix);
	void setIs(String is);
	void setTagName(String localName);
}
