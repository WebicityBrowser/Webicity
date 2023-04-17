package com.github.webicitybrowser.spiderhtml;

import java.io.StringReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.DocumentType;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.spec.dom.node.Text;
import com.github.webicitybrowser.spec.dom.node.imp.DocumentImp;
import com.github.webicitybrowser.spec.dom.node.support.NodeList;
import com.github.webicitybrowser.spec.html.binding.BindingHTMLTreeBuilder;
import com.github.webicitybrowser.spec.html.node.HTMLElement;
import com.github.webicitybrowser.spec.html.parse.HTMLParser;
import com.github.webicitybrowser.spec.html.parse.HTMLTreeBuilder;

public class HTMLParserTest {

	@Test
	@DisplayName("Empty input generates minimal tree")
	public void emptyInputGeneratesMinimalTree() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		NodeList documentChildren = document.getChildNodes();
		Assertions.assertEquals(1, documentChildren.getLength());
		HTMLElement htmlLeaf = testElement(documentChildren.get(0), "html", 2);
		NodeList htmlChildren = htmlLeaf.getChildNodes();
		testElement(htmlChildren.get(0), "head", 0);
		testElement(htmlChildren.get(1), "body", 0);
	}
	
	@Test
	@DisplayName("Minimal input generates minimal tree")
	public void minimalInputGeneratesMinimalTree() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		testToBody(document, 0);
	}
	
	@Test
	@DisplayName("Can parse basic text")
	public void canParseBasicText() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body>text</body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		HTMLElement bodyLeaf = testToBody(document, 1);
		Text textLeaf = (Text) bodyLeaf.getChildNodes().get(0);
		Assertions.assertEquals("text", textLeaf.getData());
	}
	
	private HTMLElement testToBody(Document document, int numBodyChildren) {
		NodeList documentChildren = document.getChildNodes();
		Assertions.assertEquals(2, documentChildren.getLength());
		Node doctypeNode = documentChildren.get(0);
		Assertions.assertInstanceOf(DocumentType.class, doctypeNode);
		Assertions.assertEquals("html", ((DocumentType) doctypeNode).getName());
		HTMLElement htmlNode = testElement(documentChildren.get(1), "html", 2);
		NodeList htmlChildren = htmlNode.getChildNodes();
		testElement(htmlChildren.get(0), "head", 0);
		return testElement(htmlChildren.get(1), "body", numBodyChildren);
	}
	
	private HTMLElement testElement(Node node, String name, int children) {
		Assertions.assertInstanceOf(HTMLElement.class, node);
		HTMLElement element = (HTMLElement) node;
		Assertions.assertEquals(name, element.getLocalTag());
		Assertions.assertEquals(children, element.getChildNodes().getLength());
		
		return element;
	}
	
}
