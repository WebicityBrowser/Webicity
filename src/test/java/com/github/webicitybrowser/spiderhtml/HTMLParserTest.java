package com.github.webicitybrowser.spiderhtml;

import java.io.StringReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.dom.node.Comment;
import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.dom.node.DocumentType;
import com.github.webicitybrowser.spec.dom.node.Element;
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
	@DisplayName("Ignores whitespace before head")
	public void ignoresWhitespaceBeforeHead() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("\n<!doctype html>\n<html>\n<head></head><body></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		testToBody(document, 0);
	}
	
	@Test
	@DisplayName("Properly parses minimal tree with indentation")
	public void properlyParsesMinimalTreeWithIndentation() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader(
			"\n<!doctype html>\n\t<html>\n\t\t<head>\n\t\t</head>\n\t\t<body>\n\t\t</body>\n\t</html>\n");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		HTMLElement htmlNode = testToHtml(document, 3);
		NodeList htmlChildren = htmlNode.getChildNodes();
		Element headElement = testElement(htmlChildren.get(0), "head", 1);
		NodeList headChildren = headElement.getChildNodes();
		assertText("\n\t\t", headChildren.get(0));
		assertText("\n\t\t", htmlChildren.get(1));
		Element bodyElement = testElement(htmlChildren.get(2), "body", 1);
		NodeList bodyChildren = bodyElement.getChildNodes();
		assertText("\n\t\t\n\t\n", bodyChildren.get(0));
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
	
	@Test
	@DisplayName("Can parse empty div")
	public void canParseEmptyDiv() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body><div></div></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		HTMLElement bodyLeaf = testToBody(document, 1);
		NodeList childNodes = bodyLeaf.getChildNodes();
		testElement(childNodes.get(0), "div", 0);
	}
	
	@Test
	@DisplayName("Can parse ordinary element")
	public void canParseOrdinaryElement() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body><span></span></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		HTMLElement bodyLeaf = testToBody(document, 1);
		NodeList childNodes = bodyLeaf.getChildNodes();
		testElement(childNodes.get(0), "span", 0);
	}
	
	@Test
	@DisplayName("Can parse self-closing element")
	public void canParseSelfClosingElement() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body><br><br/></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		HTMLElement bodyLeaf = testToBody(document, 2);
		NodeList childNodes = bodyLeaf.getChildNodes();
		testElement(childNodes.get(0), "br", 0);
		testElement(childNodes.get(1), "br", 0);
	}
	
	@Test
	@DisplayName("Can parse element with quoted attributes")
	public void canParseElementWithAttibutes() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body><span a='b' c=\"d\"></span></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		HTMLElement bodyNode = testToBody(document, 1);
		NodeList childNodes = bodyNode.getChildNodes();
		HTMLElement spanElement = testElement(childNodes.get(0), "span", 0);
		Assertions.assertArrayEquals(new String[] { "a", "c" }, spanElement.getAttributeNames());
		Assertions.assertEquals("b", spanElement.getAttribute("a"));
		Assertions.assertEquals("d", spanElement.getAttribute("c"));
	}
	
	@Test
	@DisplayName("Can parse style element")
	public void canParseStyleElement() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head><style>a < b {}</style></head><body></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		HTMLElement headNode = testToHead(document, 1);
		NodeList headChildren = headNode.getChildNodes();
		HTMLElement styleElement = testElement(headChildren.get(0), "style", 1);
		NodeList styleChildren = styleElement.getChildNodes();
		Assertions.assertInstanceOf(Text.class, styleChildren.get(0));
		Text text = (Text) styleChildren.get(0);
		Assertions.assertEquals("a < b {}", text.getData());
	}
	
	@Test
	@DisplayName("Can parse script element")
	public void canParseScriptElement() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head><script>alert(a < b)</script></head><body></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		HTMLElement headNode = testToHead(document, 1);
		NodeList headChildren = headNode.getChildNodes();
		HTMLElement scriptElement = testElement(headChildren.get(0), "script", 1);
		NodeList scriptChildren = scriptElement.getChildNodes();
		Assertions.assertInstanceOf(Text.class, scriptChildren.get(0));
		Text text = (Text) scriptChildren.get(0);
		Assertions.assertEquals("alert(a < b)", text.getData());
	}
	
	@Test
	@DisplayName("Can parse title element")
	public void canParseTitleElement() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head><title>Hello, World!</title></head><body></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		HTMLElement headNode = testToHead(document, 1);
		NodeList headChildren = headNode.getChildNodes();
		HTMLElement titleElement = testElement(headChildren.get(0), "title", 1);
		NodeList titleChildren = titleElement.getChildNodes();
		Assertions.assertInstanceOf(Text.class, titleChildren.get(0));
		Text text = (Text) titleChildren.get(0);
		Assertions.assertEquals("Hello, World!", text.getData());
	}
	
	@Test
	@DisplayName("Can parse a comment")
	public void canParseAComment() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body><!--Hello, World--></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		HTMLElement bodyNode = testToBody(document, 1);
		NodeList bodyChildren = bodyNode.getChildNodes();
		Assertions.assertInstanceOf(Comment.class, bodyChildren.get(0));
		Comment comment = (Comment) bodyChildren.get(0);
		Assertions.assertEquals("Hello, World", comment.getData());
	}
	
	private HTMLElement testToBody(Document document, int numBodyChildren) {
		HTMLElement htmlNode = testToHtml(document, 2);
		NodeList htmlChildren = htmlNode.getChildNodes();
		testElement(htmlChildren.get(0), "head", 0);
		return testElement(htmlChildren.get(1), "body", numBodyChildren);
	}
	
	private HTMLElement testToHead(Document document, int numHeadChildren) {
		HTMLElement htmlNode = testToHtml(document, 2);
		NodeList htmlChildren = htmlNode.getChildNodes();
		testElement(htmlChildren.get(1), "body", 0);
		return testElement(htmlChildren.get(0), "head", numHeadChildren);
	}
	
	private HTMLElement testToHtml(Document document, int numHtmlChildren) {
		NodeList documentChildren = document.getChildNodes();
		Assertions.assertEquals(2, documentChildren.getLength());
		Node doctypeNode = documentChildren.get(0);
		Assertions.assertInstanceOf(DocumentType.class, doctypeNode);
		Assertions.assertEquals("html", ((DocumentType) doctypeNode).getName());
		return testElement(documentChildren.get(1), "html", numHtmlChildren);
	}
	
	private HTMLElement testElement(Node node, String name, int children) {
		Assertions.assertInstanceOf(HTMLElement.class, node);
		HTMLElement element = (HTMLElement) node;
		Assertions.assertEquals(name, element.getLocalName());
		Assertions.assertEquals(children, element.getChildNodes().getLength());
		
		return element;
	}
	
	private void assertText(String string, Node node) {
		Assertions.assertInstanceOf(Text.class, node);
		Text text = (Text) node;
		Assertions.assertEquals(string, text.getData());
	}
	
}
