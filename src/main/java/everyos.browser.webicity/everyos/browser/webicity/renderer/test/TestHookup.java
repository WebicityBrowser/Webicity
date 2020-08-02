package everyos.browser.webicity.renderer.test;

import everyos.browser.webicity.WebicityFrame;
import everyos.browser.webicity.dom.Document;
import everyos.browser.webicity.dom.TextNode;
import everyos.browser.webicity.webribbon.component.WebComponent;

public class TestHookup {
	public WebComponent run(WebicityFrame frame) {
		//Create a simple DOM which displays some stuff
		Document doc = new Document();
		doc.appendChild(new TextNode("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa b"));
		
		return doc.component();
	}
}
