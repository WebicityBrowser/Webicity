package everyos.browser.javadom.imp;

import java.util.ArrayList;
import java.util.List;

import everyos.browser.javadom.intf.Document;
import everyos.browser.javadom.intf.Node;
import everyos.browser.jcss.intf.CSSStyleSheet;
import everyos.browser.jhtml.browsing.BrowsingContext;
import everyos.browser.jhtml.browsing.Origin;
import everyos.browser.jhtml.intf.HTMLElement;
import everyos.browser.jhtml.intf.HTMLTitleElement;
import everyos.browser.jhtml.intf.Window;
import everyos.browser.webicity.net.URL;

public class JDDocument extends JDNode implements Document {
	private Origin origin;
	private URL url;
	private BrowsingContext bc; // Thanks, I hate this variable name
	
	private String title;
	
	private Window window;
	private List<CSSStyleSheet> cssStylesheets = new ArrayList<>();

	public JDDocument(JDDocumentBuilder documentFactory) {
		super(null);
		super.setNodeDocument(this);
		
		//TODO
		this.window = new JDWindow(this);
		
		this.origin = documentFactory.getOrigin();
		this.url = documentFactory.getURL();
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
		if (title != null) {
			return title;
		}
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
		if (parent==null) {
			return null;
		}
		for (Node child: parent.getChildNodes()) {
			if (child instanceof HTMLElement && ((HTMLElement) child).getTagName().equals(name)) {
				return (HTMLElement) child;
			}
		}
		return null;
	}

	
	//TODO: These methods *might* belong elsewhere
	@Override
	public Origin getOrigin() {
		return origin;
	}

	@Override
	public URL getURLAsURL() {
		return url;
	}

	@Override
	public BrowsingContext getTopLevelContext() {
		return bc;
	}

	@Override
	public Window getRelevantGlobalObject() {
		return null;
	}

	@Override
	public List<CSSStyleSheet> getDocumentOrShadowRootCSSStyleSheets() {
		return cssStylesheets;
	}
}
