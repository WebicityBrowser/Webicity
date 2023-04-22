package com.github.webicitybrowser.webicity.renderer.backend.html;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.spec.dom.node.Document;
import com.github.webicitybrowser.spec.html.binding.BindingHTMLTreeBuilder;
import com.github.webicitybrowser.spec.html.parse.HTMLTreeBuilder;
import com.github.webicitybrowser.spiderhtml.SpiderHTMLParserImp;
import com.github.webicitybrowser.webicity.core.net.Connection;
import com.github.webicitybrowser.webicity.core.renderer.RendererBackend;
import com.github.webicitybrowser.webicity.core.renderer.RendererContext;
import com.github.webicitybrowser.webicity.core.renderer.RendererFrontend;

public class HTMLRendererBackend implements RendererBackend {
	
	private static final Logger logger = LoggerFactory.getLogger(HTMLRendererBackend.class);
	
	private final RendererContext rendererContext;
	private final Document document;

	public HTMLRendererBackend(RendererContext rendererContext, Connection connection) throws IOException {
		this.rendererContext = rendererContext;
		this.document = Document.create();
		parseAndTimeDocument(connection);
	}

	public Document getDocument() {
		return this.document;
	}
	
	private void parseAndTimeDocument(Connection connection) throws IOException {
		long time = System.currentTimeMillis();
		parseDocument(connection.getInputReader());
		long millisToParse = System.currentTimeMillis()-time;
		int secondsToParse = (int) (millisToParse/1000 + .5);
		logger.info("Page (" + connection.getURL() +") parsed in " + (millisToParse) + " millis (" + secondsToParse +" seconds).");
	}
	
	private void parseDocument(Reader inputReader) throws IOException {
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		new SpiderHTMLParserImp().parse(inputReader, treeBuilder);
		System.out.println(document);
	}

	public <T extends RendererFrontend> T createFrontend(Function<RendererContext, T> factory) {
		return factory.apply(rendererContext);
	}

}
