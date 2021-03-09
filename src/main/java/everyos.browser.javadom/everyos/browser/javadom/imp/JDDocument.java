package everyos.browser.javadom.imp;

import everyos.browser.javadom.intf.Document;

public class JDDocument extends JDNode implements Document {
	public JDDocument() {
		super(null);
		super.setNodeDocument(this);
	}
	
	@Override
	public Document getOwnerDocument() {
		return null;
	}
}
