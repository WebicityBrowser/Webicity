package everyos.browser.javadom.imp;

import everyos.browser.javadom.intf.Document;
import everyos.browser.javadom.intf.Node;
import everyos.browser.jhtml.intf.HTMLElement;
import everyos.browser.jhtml.intf.HTMLTitleElement;

public class JDDocument extends JDNode implements Document {
	private String title;

	public JDDocument() {
		super(null);
		super.setNodeDocument(this);
	}
	
	@Override
	public Document getOwnerDocument() {
		return null;
	}

	@Override
	public void setTitle(String text) {
		this.title = text;
		
	}

	@Override
	public String getTitle() {
		if (title != null) return title;
		HTMLElement html = getElement(this, "html");
		HTMLElement head = getElement(html, "head");
		if (head!=null) {
			for (Node child: head.getChildNodes()) {
				if (child instanceof HTMLTitleElement) {
					return ((HTMLTitleElement) child).getText();
				}
			}
		}
		return null;
	}
	
	private HTMLElement getElement(Node parent, String name) {
		if (parent==null) return null;
		for (Node child: parent.getChildNodes()) {
			if (child instanceof HTMLElement && ((HTMLElement) child).getTagName().equals(name)) {
				return (HTMLElement) child;
			}
		}
		return null;
	}
}
