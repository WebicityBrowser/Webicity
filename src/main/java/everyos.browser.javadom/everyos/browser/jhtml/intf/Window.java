package everyos.browser.jhtml.intf;

import everyos.browser.javadom.intf.Document;
import everyos.browser.javadom.intf.EventTarget;

public interface Window extends EventTarget {
	void setAssociatedDocument(Document document);
}
