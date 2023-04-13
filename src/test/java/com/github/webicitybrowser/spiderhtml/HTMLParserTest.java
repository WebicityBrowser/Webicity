package com.github.webicitybrowser.spiderhtml;

import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.github.webicitybrowser.spec.html.parse.HTMLParser;
import com.github.webicitybrowser.spiderhtml.test.TestHTMLDocumentLeaf;
import com.github.webicitybrowser.spiderhtml.test.TestHTMLDocumentTypeLeaf;
import com.github.webicitybrowser.spiderhtml.test.TestHTMLElementLeaf;
import com.github.webicitybrowser.spiderhtml.test.TestHTMLHtmlElementLeaf;
import com.github.webicitybrowser.spiderhtml.test.TestHTMLLeaf;
import com.github.webicitybrowser.spiderhtml.test.TestHTMLTreeBuilder;

public class HTMLParserTest {

	@Test
	@DisplayName("Empty input generates minimal tree")
	public void emptyInputGeneratesMinimalTree() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("");
		TestHTMLTreeBuilder treeBuilder = new TestHTMLTreeBuilder();
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		TestHTMLDocumentLeaf document = treeBuilder.getDocument();
		List<TestHTMLLeaf> documentChildren = document.getChildren();
		Assertions.assertEquals(1, documentChildren.size());
		TestHTMLElementLeaf htmlLeaf = (TestHTMLHtmlElementLeaf) documentChildren.get(0);
		Assertions.assertEquals("html", htmlLeaf.getName());
		List<TestHTMLLeaf> htmlChildren = htmlLeaf.getChildren();
		Assertions.assertEquals(2, htmlChildren.size());
		TestHTMLElementLeaf headLeaf = (TestHTMLElementLeaf) htmlChildren.get(0);
		Assertions.assertEquals("head", headLeaf.getName());
		TestHTMLElementLeaf bodyLeaf = (TestHTMLElementLeaf) htmlChildren.get(1);
		Assertions.assertEquals("body", bodyLeaf.getName());
		Assertions.assertEquals(0, headLeaf.getLength());
		Assertions.assertEquals(0, bodyLeaf.getLength());
	}
	
	@Test
	@DisplayName("Minimal input generates minimal tree")
	public void minimalInputGeneratesMinimalTree() {
		HTMLParser parser = new SpiderHTMLParserImp();
		StringReader reader = new StringReader("<!doctype html><html><head></head><body></body></html>");
		TestHTMLTreeBuilder treeBuilder = new TestHTMLTreeBuilder();
		Assertions.assertDoesNotThrow(() -> parser.parse(reader, treeBuilder));
		TestHTMLDocumentLeaf document = treeBuilder.getDocument();
		List<TestHTMLLeaf> documentChildren = document.getChildren();
		Assertions.assertEquals(2, documentChildren.size());
		TestHTMLLeaf doctypeLeaf = documentChildren.get(0);
		Assertions.assertInstanceOf(TestHTMLDocumentTypeLeaf.class, doctypeLeaf);
		Assertions.assertEquals("html", ((TestHTMLDocumentTypeLeaf) doctypeLeaf).getName());
		TestHTMLElementLeaf htmlLeaf = (TestHTMLHtmlElementLeaf) documentChildren.get(1);
		Assertions.assertEquals("html", htmlLeaf.getName());
		List<TestHTMLLeaf> htmlChildren = htmlLeaf.getChildren();
		Assertions.assertEquals(2, htmlChildren.size());
		TestHTMLElementLeaf headLeaf = (TestHTMLElementLeaf) htmlChildren.get(0);
		Assertions.assertEquals("head", headLeaf.getName());
		TestHTMLElementLeaf bodyLeaf = (TestHTMLElementLeaf) htmlChildren.get(1);
		Assertions.assertEquals("body", bodyLeaf.getName());
		Assertions.assertEquals(0, headLeaf.getLength());
		Assertions.assertEquals(0, bodyLeaf.getLength());
	}
	
}
