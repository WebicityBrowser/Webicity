package everyos.browser.spec.javadom.imp;

import everyos.browser.spec.javadom.intf.Document;
import everyos.browser.spec.jhtml.browsing.Origin;
import everyos.browser.webicity.net.URL;

public class JDDocumentBuilder {
	private String type = "xml";
	private String encoding = "utf-8";
	private String mode = "no-quirks";
	private URL url = URL.ABOUT_BLANK;
	private Origin origin = new Origin();
	private String contentType;
	
	public JDDocumentBuilder setQuirksMode(String quirksMode) {
		this.mode = quirksMode;
		return this;
	}
	
	public JDDocumentBuilder setType(String type) {
		this.type = type;
		return this;
	}
	
	public JDDocumentBuilder setOrigin(Origin origin) {
		this.origin = origin;
		return this;
	}
	
	public JDDocumentBuilder setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}
	
	public JDDocumentBuilder setReadyForPostLoadTasks(boolean b) {
		//TODO
		return this;
	}
	
	public Document build() {
		return new JDDocument(this);
	}

	public Origin getOrigin() {
		return origin;
	}
	
	public URL getURL() {
		return url;
	}
	
	public String getType() {
		return type;
	}
	
	public String getQuirksMode() {
		return mode;
	}
	
	public String getContentType() {
		return contentType;
	}

	public String getEncoding() {
		return encoding;
	}
}
