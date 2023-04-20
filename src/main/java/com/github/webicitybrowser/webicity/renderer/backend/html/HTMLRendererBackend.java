package com.github.webicitybrowser.webicity.renderer.backend.html;

import java.io.IOException;
import java.io.Reader;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.html.binding.BindingHTMLTreeBuilder;
import com.github.webicitybrowser.spec.html.parse.HTMLTreeBuilder;
import com.github.webicitybrowser.spiderhtml.SpiderHTMLParserImp;
import com.github.webicitybrowser.webicity.renderer.backend.core.RendererBackend;

public class HTMLRendererBackend implements RendererBackend {
	
	private final Document document;

	public HTMLRendererBackend(Reader inputReader) throws IOException {
		this.document = Document.create();
		parseDocument(inputReader);
	}

	public Document getDocument() {
		return this.document;
	}
	
	private void parseDocument(Reader inputReader) throws IOException {
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		new SpiderHTMLParserImp().parse(inputReader, treeBuilder);
		System.out.println(document);
	}

}
