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
import com.github.webicitybrowser.spiderhtml.test.TestParserSettings;

public class HTMLParserTest {

	@Test
	@DisplayName("Empty input generates minimal tree")
	public void emptyInputGeneratesMinimalTree() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
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
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		testToBody(document, 0);
	}
	
	@Test
	@DisplayName("Ignores whitespace before head")
	public void ignoresWhitespaceBeforeHead() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("\n<!doctype html>\n<html>\n<head></head><body></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
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
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
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
	@DisplayName("Can parse character reference text")
	public void canParseCharacterReferenceText() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body>&lt;</body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyLeaf = testToBody(document, 1);
		Text textLeaf = (Text) bodyLeaf.getChildNodes().get(0);
		Assertions.assertEquals("<", textLeaf.getData());
	}
	
	@Test
	@DisplayName("Can parse character reference digit")
	public void canParseCharacterReferenceDigit() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body>&#60;</body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyLeaf = testToBody(document, 1);
		Text textLeaf = (Text) bodyLeaf.getChildNodes().get(0);
		Assertions.assertEquals("<", textLeaf.getData());
	}
	
	@Test
	@DisplayName("Can parse character reference hex")
	public void canParseCharacterReferenceHex() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body>&#x3C;</body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyLeaf = testToBody(document, 1);
		Text textLeaf = (Text) bodyLeaf.getChildNodes().get(0);
		Assertions.assertEquals("<", textLeaf.getData());
	}
	
	@Test
	@DisplayName("Can parse basic text")
	public void canParseBasicText() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body>text</body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
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
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
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
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
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
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyLeaf = testToBody(document, 2);
		NodeList childNodes = bodyLeaf.getChildNodes();
		testElement(childNodes.get(0), "br", 0);
		testElement(childNodes.get(1), "br", 0);
	}
	
	@Test
	@DisplayName("Can parse element with quoted attributes")
	public void canParseElementWithQuotedAttributes() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body><span a='b' c=\"d\"></span></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyNode = testToBody(document, 1);
		NodeList childNodes = bodyNode.getChildNodes();
		HTMLElement spanElement = testElement(childNodes.get(0), "span", 0);
		Assertions.assertArrayEquals(new String[] { "a", "c" }, spanElement.getAttributeNames());
		Assertions.assertEquals("b", spanElement.getAttribute("a"));
		Assertions.assertEquals("d", spanElement.getAttribute("c"));
	}
	
	@Test
	@DisplayName("Can parse element with unquoted attributes")
	public void canParseElementWithUnquottedAttributes() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body><span a=boo></span></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyNode = testToBody(document, 1);
		NodeList childNodes = bodyNode.getChildNodes();
		HTMLElement spanElement = testElement(childNodes.get(0), "span", 0);
		Assertions.assertArrayEquals(new String[] { "a" }, spanElement.getAttributeNames());
		Assertions.assertEquals("boo", spanElement.getAttribute("a"));
	}
	
	@Test
	@DisplayName("Can parse style element")
	public void canParseStyleElement() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head><style>a < b {}</style></head><body></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
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
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement headNode = testToHead(document, 1);
		NodeList headChildren = headNode.getChildNodes();
		HTMLElement scriptElement = testElement(headChildren.get(0), "script", 1);
		NodeList scriptChildren = scriptElement.getChildNodes();
		Assertions.assertInstanceOf(Text.class, scriptChildren.get(0));
		Text text = (Text) scriptChildren.get(0);
		Assertions.assertEquals("alert(a < b)", text.getData());
	}
	
	@Test
	@DisplayName("Can parse escaped script element")
	public void canParseEscapedScriptElement() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head><script><!--test\n// hi\n//--></script></head><body></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement headNode = testToHead(document, 1);
		NodeList headChildren = headNode.getChildNodes();
		HTMLElement scriptElement = testElement(headChildren.get(0), "script", 1);
		NodeList scriptChildren = scriptElement.getChildNodes();
		Assertions.assertInstanceOf(Text.class, scriptChildren.get(0));
		Text text = (Text) scriptChildren.get(0);
		Assertions.assertEquals("<!--test\n// hi\n//-->", text.getData());
	}
	
	@Test
	@DisplayName("Can parse title element")
	public void canParseTitleElement() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head><title>Hello, World!</title></head><body></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
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
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyNode = testToBody(document, 1);
		NodeList bodyChildren = bodyNode.getChildNodes();
		Assertions.assertInstanceOf(Comment.class, bodyChildren.get(0));
		Comment comment = (Comment) bodyChildren.get(0);
		Assertions.assertEquals("Hello, World", comment.getData());
	}
	
	@Test
	@DisplayName("Can parse self-closing in-head tag")
	public void canParseSelfClosingInHeadTag() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head><link/><link/></head><body></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement headNode = testToHead(document, 2);
		NodeList headChildren = headNode.getChildNodes();
		testElement(headChildren.get(0), "link", 0);
		testElement(headChildren.get(1), "link", 0);
	}

	@Test
	@DisplayName("Can parse list with multiple li")
	public void canParseListWithMultipleLi() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body><ul><li>Text<li>Test</ul></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyNode = testToBody(document, 1);
		NodeList bodyChildren = bodyNode.getChildNodes();
		HTMLElement ulElement = testElement(bodyChildren.get(0), "ul", 2);
		NodeList ulChildren = ulElement.getChildNodes();
		testElement(ulChildren.get(0), "li", 1);
		NodeList liChildren1 = ulChildren.get(0).getChildNodes();
		assertText("Text", liChildren1.get(0));
		testElement(ulChildren.get(1), "li", 1);
		NodeList liChildren2 = ulChildren.get(1).getChildNodes();
		assertText("Test", liChildren2.get(0));
	}

	@Test
	@DisplayName("Can parse multiple p")
	public void canParseMultipleP() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body><p>Text<p>Test</body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyNode = testToBody(document, 2);
		NodeList bodyChildren = bodyNode.getChildNodes();
		testElement(bodyChildren.get(0), "p", 1);
		NodeList pChildren1 = bodyChildren.get(0).getChildNodes();
		assertText("Text", pChildren1.get(0));
		testElement(bodyChildren.get(1), "p", 1);
		NodeList pChildren2 = bodyChildren.get(1).getChildNodes();
		assertText("Test", pChildren2.get(0));
	}

	@Test
	@DisplayName("Can parse multiple dt and dd")
	public void canParseMultipleDtAndDd() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body><dl><dt>Text<dd>Test<dt>Test2<dd>Test3</dl></body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyNode = testToBody(document, 1);
		NodeList bodyChildren = bodyNode.getChildNodes();
		HTMLElement dlElement = testElement(bodyChildren.get(0), "dl", 4);
		NodeList dlChildren = dlElement.getChildNodes();
		testElement(dlChildren.get(0), "dt", 1);
		NodeList dtChildren1 = dlChildren.get(0).getChildNodes();
		assertText("Text", dtChildren1.get(0));
		testElement(dlChildren.get(1), "dd", 1);
		NodeList ddChildren1 = dlChildren.get(1).getChildNodes();
		assertText("Test", ddChildren1.get(0));
		testElement(dlChildren.get(2), "dt", 1);
		NodeList dtChildren2 = dlChildren.get(2).getChildNodes();
		assertText("Test2", dtChildren2.get(0));
		testElement(dlChildren.get(3), "dd", 1);
		NodeList ddChildren2 = dlChildren.get(3).getChildNodes();
		assertText("Test3", ddChildren2.get(0));
	}

	@Test
	@DisplayName("Can close ol and ul")
	public void canCloseOlAndUl() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body><ol><li>Test</ol><ul><li>Test2</ul>End</body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyNode = testToBody(document, 3);
		NodeList bodyChildren = bodyNode.getChildNodes();
		HTMLElement olElement = testElement(bodyChildren.get(0), "ol", 1);
		NodeList olChildren = olElement.getChildNodes();
		testElement(olChildren.get(0), "li", 1);
		NodeList liChildren1 = olChildren.get(0).getChildNodes();
		assertText("Test", liChildren1.get(0));
		testElement(bodyChildren.get(1), "ul", 1);
		NodeList ulChildren = bodyChildren.get(1).getChildNodes();
		testElement(ulChildren.get(0), "li", 1);
		NodeList liChildren2 = ulChildren.get(0).getChildNodes();
		assertText("Test2", liChildren2.get(0));
		assertText("End", bodyChildren.get(2));
	}

	@Test
	@DisplayName("hr is self-closing")
	public void hrIsSelfClosing() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body><hr>test</body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyNode = testToBody(document, 2);
		NodeList bodyChildren = bodyNode.getChildNodes();
		testElement(bodyChildren.get(0), "hr", 0);
		assertText("test", bodyChildren.get(1));
	}

	@Test
	@DisplayName("Can parse multiple tr and td")
	public void canParseMultipleTrAndTd() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader(
			"<!doctype html><html><head></head>" +
			"<body><table><tr><td>Test<td>Test2<tr>" +
			"<td>Test3<td>Test4</td></tr></table>End</body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyNode = testToBody(document, 2);
		NodeList bodyChildren = bodyNode.getChildNodes();
		HTMLElement tableElement = testElement(bodyChildren.get(0), "table", 1);
		NodeList tableChildren = tableElement.getChildNodes();
		HTMLElement tbodyElement = testElement(tableChildren.get(0), "tbody", 2);
		NodeList tbodyChildren = tbodyElement.getChildNodes();
		HTMLElement trElement1 = testElement(tbodyChildren.get(0), "tr", 2);
		NodeList trChildren1 = trElement1.getChildNodes();
		testElement(trChildren1.get(0), "td", 1);
		NodeList tdChildren1 = trChildren1.get(0).getChildNodes();
		assertText("Test", tdChildren1.get(0));
		testElement(trChildren1.get(1), "td", 1);
		NodeList tdChildren2 = trChildren1.get(1).getChildNodes();
		assertText("Test2", tdChildren2.get(0));
		HTMLElement trElement2 = testElement(tbodyChildren.get(1), "tr", 2);
		NodeList trChildren2 = trElement2.getChildNodes();
		testElement(trChildren2.get(0), "td", 1);
		NodeList tdChildren3 = trChildren2.get(0).getChildNodes();
		assertText("Test3", tdChildren3.get(0));
		testElement(trChildren2.get(1), "td", 1);
		NodeList tdChildren4 = trChildren2.get(1).getChildNodes();
		assertText("Test4", tdChildren4.get(0));
		assertText("End", bodyChildren.get(1));
	}

	@Test
	@DisplayName("Can parse table with thead, tbody, and tfoot")
	public void canParseTableWithTheadAndTbodyAndTfoot() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader(
			"<!doctype html><html><head></head><body><table>" +
			"<thead><tr><th>Test</th></tr></thead>" +
			"<tbody><tr><td>Test2" + // Test without end tags, too
			"<tfoot><tr><td>Test3" +
			"</table>End</body></html>");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		HTMLElement bodyNode = testToBody(document, 2);
		NodeList bodyChildren = bodyNode.getChildNodes();
		HTMLElement tableElement = testElement(bodyChildren.get(0), "table", 3);
		NodeList tableChildren = tableElement.getChildNodes();
		HTMLElement theadElement = testElement(tableChildren.get(0), "thead", 1);
		NodeList theadChildren = theadElement.getChildNodes();
		HTMLElement trElement = testElement(theadChildren.get(0), "tr", 1);
		NodeList trChildren = trElement.getChildNodes();
		testElement(trChildren.get(0), "th", 1);
		NodeList thChildren = trChildren.get(0).getChildNodes();
		assertText("Test", thChildren.get(0));
		HTMLElement tbodyElement = testElement(tableChildren.get(1), "tbody", 1);
		NodeList tbodyChildren = tbodyElement.getChildNodes();
		HTMLElement trElement2 = testElement(tbodyChildren.get(0), "tr", 1);
		NodeList trChildren2 = trElement2.getChildNodes();
		testElement(trChildren2.get(0), "td", 1);
		NodeList tdChildren = trChildren2.get(0).getChildNodes();
		assertText("Test2", tdChildren.get(0));
		HTMLElement tfootElement = testElement(tableChildren.get(2), "tfoot", 1);
		NodeList tfootChildren = tfootElement.getChildNodes();
		HTMLElement trElement3 = testElement(tfootChildren.get(0), "tr", 1);
		NodeList trChildren3 = trElement3.getChildNodes();
		testElement(trChildren3.get(0), "td", 1);
		assertText("End", bodyChildren.get(1));
	}

	@Test
	@DisplayName("Can handle comments in various places")
	public void canHandleCommentsInVariousPlaces() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader(
			"<!-- comment before doctype -->" +
			"<!doctype html>" +
			"<!-- comment after doctype -->" +
			"<html>" +
			"<!-- comment in html -->" +
			"<head>" +
			"<!-- comment in head -->" +
			"</head>" +
			"<!-- comment after head -->" +
			"<body>" +
			"<!-- comment in body -->" +
			"</body>" +
			"<!-- comment after body -->" +
			"</html>" +
			"<!-- comment after html -->");
		Document document = new DocumentImp();
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder, new TestParserSettings()));
		NodeList documentChildren = document.getChildNodes();
		Assertions.assertEquals(5, documentChildren.getLength());
		Assertions.assertInstanceOf(Comment.class, documentChildren.get(0));
		Assertions.assertInstanceOf(DocumentType.class, documentChildren.get(1));
		Assertions.assertInstanceOf(Comment.class, documentChildren.get(2));
		HTMLElement htmlNode = testElement(documentChildren.get(3), "html", 4);
		NodeList htmlChildren = htmlNode.getChildNodes();
		Assertions.assertInstanceOf(Comment.class, htmlChildren.get(0));
		HTMLElement headNode = testElement(htmlChildren.get(1), "head", 1);
		NodeList headChildren = headNode.getChildNodes();
		Assertions.assertInstanceOf(Comment.class, headChildren.get(0));
		Assertions.assertInstanceOf(Comment.class, htmlChildren.get(2));
		HTMLElement bodyNode = testElement(htmlChildren.get(3), "body", 2);
		NodeList bodyChildren = bodyNode.getChildNodes();
		Assertions.assertInstanceOf(Comment.class, bodyChildren.get(0));
		Assertions.assertInstanceOf(Comment.class, bodyChildren.get(1));
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
