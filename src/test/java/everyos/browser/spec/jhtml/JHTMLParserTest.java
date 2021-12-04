package everyos.browser.spec.jhtml;

import java.io.StringReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.html.HTMLBodyElement;

import everyos.browser.spec.javadom.intf.Document;
import everyos.browser.spec.jhtml.intf.HTMLElement;
import everyos.browser.spec.jhtml.intf.HTMLHeadElement;
import everyos.browser.spec.jhtml.parser.JHTMLParser;

public class JHTMLParserTest {

	@Test
	@DisplayName("Empty document generates empty DOM")
	public void emptyDocumentIsEmpty() {
		try {
			Document document = new JHTMLParser(new StringReader("")).parse();
			Assertions.assertEquals(document.getChildNodes().getLength(), 1);
			Assertions.assertInstanceOf(HTMLElement.class, document.getChildNodes().item(0));
			HTMLElement htmlElement = (HTMLElement) document.getChildNodes().item(0);
			Assertions.assertEquals(htmlElement.getChildNodes().getLength(), 2);
			Assertions.assertInstanceOf(HTMLHeadElement.class, htmlElement.getChildNodes().item(0));
			Assertions.assertInstanceOf(HTMLBodyElement.class, htmlElement.getChildNodes().item(1));
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}
	
	@Test
	@DisplayName("Title parses correctly")
	public void titleParsesCorrectly() {
		try {
			Document document = new JHTMLParser(new StringReader("<!doctype html><html><head><title>Hello, World!</title></head><body></body></html>")).parse();
			Assertions.assertEquals(document.getTitle(), "Hello, World!");
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}
	
}
