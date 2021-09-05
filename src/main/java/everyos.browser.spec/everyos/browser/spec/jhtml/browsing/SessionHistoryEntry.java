package everyos.browser.spec.jhtml.browsing;

import everyos.browser.spec.javadom.intf.Document;

public interface SessionHistoryEntry {
	Document getDocument();

	static SessionHistoryEntry of(Document document) {
		return new SessionHistoryEntry() {
			@Override
			public Document getDocument() {
				return document;
			}
		};
	}
}
