package everyos.browser.webicity.webribbon.core.component;

import everyos.browser.spec.javadom.intf.Document;
import everyos.browser.webicity.renderer.html.HTMLRenderer;

public class WebDocumentComponent extends WebComponent {
	@SuppressWarnings("unused")
	private Document document;

	public WebDocumentComponent(HTMLRenderer renderer, Document node) {
		super(renderer, node);
		this.document = node;
	}
}
