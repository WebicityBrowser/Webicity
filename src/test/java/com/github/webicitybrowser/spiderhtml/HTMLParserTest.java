package com.github.webicitybrowser.spiderhtml;

import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.html.parse.HTMLParser;
import com.github.webicitybrowser.spec.html.parse.tree.HTMLLeaf;
import com.github.webicitybrowser.spiderhtml.test.TestHTMLDocumentLeaf;
import com.github.webicitybrowser.spiderhtml.test.TestHTMLDocumentTypeLeaf;
import com.github.webicitybrowser.spiderhtml.test.TestHTMLElementLeaf;
import com.github.webicitybrowser.spiderhtml.test.TestHTMLLeaf;
import com.github.webicitybrowser.spiderhtml.test.TestHTMLTextLeaf;
import com.github.webicitybrowser.spiderhtml.test.TestHTMLTreeBuilder;

public class HTMLParserTest {

	@Test
	@DisplayName("Empty input generates minimal tree")
	public void emptyInputGeneratesMinimalTree() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("");
		TestHTMLTreeBuilder treeBuilder = new TestHTMLTreeBuilder();
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		TestHTMLDocumentLeaf document = treeBuilder.getDocumentLeaf();
		List<TestHTMLLeaf> documentChildren = document.getChildren();
		Assertions.assertEquals(1, documentChildren.size());
		TestHTMLElementLeaf htmlLeaf = testElement(documentChildren.get(0), "html", 2);
		List<TestHTMLLeaf> htmlChildren = htmlLeaf.getChildren();
		testElement(htmlChildren.get(0), "head", 0);
		testElement(htmlChildren.get(1), "body", 0);
	}
	
	@Test
	@DisplayName("Minimal input generates minimal tree")
	public void minimalInputGeneratesMinimalTree() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body></body></html>");
		TestHTMLTreeBuilder treeBuilder = new TestHTMLTreeBuilder();
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		testToBody(treeBuilder, 0);
	}
	
	@Test
	@DisplayName("Can parse basic text")
	public void canParseBasicText() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body>text</body></html>");
		TestHTMLTreeBuilder treeBuilder = new TestHTMLTreeBuilder();
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		TestHTMLElementLeaf bodyLeaf = testToBody(treeBuilder, 1);
		TestHTMLTextLeaf textLeaf = (TestHTMLTextLeaf) bodyLeaf.getChildren().get(0);
		Assertions.assertEquals("text", textLeaf.getData());
	}
	
	private TestHTMLElementLeaf testToBody(TestHTMLTreeBuilder treeBuilder, int numBodyChildren) {
		TestHTMLDocumentLeaf document = treeBuilder.getDocumentLeaf();
		List<TestHTMLLeaf> documentChildren = document.getChildren();
		Assertions.assertEquals(2, documentChildren.size());
		TestHTMLLeaf doctypeLeaf = documentChildren.get(0);
		Assertions.assertInstanceOf(TestHTMLDocumentTypeLeaf.class, doctypeLeaf);
		Assertions.assertEquals("html", ((TestHTMLDocumentTypeLeaf) doctypeLeaf).getName());
		TestHTMLElementLeaf htmlLeaf = testElement(documentChildren.get(1), "html", 2);
		List<TestHTMLLeaf> htmlChildren = htmlLeaf.getChildren();
		testElement(htmlChildren.get(0), "head", 0);
		return testElement(htmlChildren.get(1), "body", numBodyChildren);
	}
	
	private TestHTMLElementLeaf testElement(HTMLLeaf leaf, String name, int children) {
		Assertions.assertInstanceOf(TestHTMLElementLeaf.class, leaf);
		TestHTMLElementLeaf element = (TestHTMLElementLeaf) leaf;
		Assertions.assertEquals(name, element.getName());
		Assertions.assertEquals(children, element.getLength());
		
		return element;
	}
	
}
