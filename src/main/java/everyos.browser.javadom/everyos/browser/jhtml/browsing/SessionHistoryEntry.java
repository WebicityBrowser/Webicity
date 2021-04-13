package everyos.browser.jhtml.browsing;

import everyos.browser.javadom.intf.Document;

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
