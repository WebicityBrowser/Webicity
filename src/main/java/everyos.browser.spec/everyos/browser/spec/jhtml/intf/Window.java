package everyos.browser.spec.jhtml.intf;

import everyos.browser.spec.javadom.intf.Document;
import everyos.browser.spec.javadom.intf.EventTarget;

public interface Window extends EventTarget {
	void setAssociatedDocument(Document document);
}
