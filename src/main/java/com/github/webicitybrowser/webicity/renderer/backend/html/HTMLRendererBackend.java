package com.github.webicitybrowser.webicity.renderer.backend.html;

import java.io.IOException;
import java.io.Reader;
import java.util.function.Function;

import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.spec.fetch.connection.imp.HTTPFetchConnectionPool;
import com.github.webicitybrowser.spec.fetch.imp.FetchEngineImp;
import com.github.webicitybrowser.webicity.core.renderer.imp.RendererContextImp;
import com.github.webicitybrowser.webicity.renderer.backend.html.tags.LinkTagHandler;
import com.github.webicitybrowser.webicity.renderer.backend.html.tags.StyleTagHandler;
import com.github.webicitybrowser.webicitybrowser.loader.ResourceAssetLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.spec.html.binding.BindingHTMLTreeBuilder;
import com.github.webicitybrowser.spec.html.node.HTMLDocument;
import com.github.webicitybrowser.spec.html.parse.HTMLTreeBuilder;
import com.github.webicitybrowser.spec.html.parse.CharacterReferenceLookup;
import com.github.webicitybrowser.spiderhtml.SpiderHTMLParserImp;
import com.github.webicitybrowser.webicity.core.net.Connection;
import com.github.webicitybrowser.webicity.core.renderer.RendererBackend;
import com.github.webicitybrowser.webicity.core.renderer.RendererContext;
import com.github.webicitybrowser.webicity.core.renderer.RendererFrontend;


public class HTMLRendererBackend implements RendererBackend {
	
	private static final Logger logger = LoggerFactory.getLogger(HTMLRendererBackend.class);
	
	private final RendererContext rendererContext;
	private final CharacterReferenceLookup characterReferenceLookup;
	private final HTMLDocument document;

	public HTMLRendererBackend(
		RendererContext rendererContext, Connection connection, CharacterReferenceLookup characterReferenceLookup
	) throws IOException {
		this.rendererContext = rendererContext;
		this.characterReferenceLookup = characterReferenceLookup;
		this.document = HTMLDocument.create();
		parseAndTimeDocument(connection);
	}
	
	@Override
	public String getTitle() {
		return document.getTitle();
	}

	public HTMLDocument getDocument() {
		return this.document;
	}
	
	private void parseAndTimeDocument(Connection connection) throws IOException {
		long time = System.currentTimeMillis();
		parseDocument(connection.getInputReader());
		long millisToParse = System.currentTimeMillis()-time;
		int secondsToParse = (int) (millisToParse/1000 + .5);
		logger.info("Page (" + connection.getURL() + ") parsed in " + (millisToParse) + " millis (" + secondsToParse +" seconds).");
	}
	
	private void parseDocument(Reader inputReader) throws IOException {
		HTMLTreeBuilder treeBuilder = new BindingHTMLTreeBuilder(document);
		new SpiderHTMLParserImp().parse(inputReader, treeBuilder, new HTMLRendererBackendParserSettings(characterReferenceLookup, createTagActions()));
	}

	private TagActions createTagActions() {
		TagActions tagActions = new ActionsRegistry();
		tagActions.registerTagAction("link", new LinkTagHandler(rendererContext));
		tagActions.registerTagAction("style", new StyleTagHandler());
		return tagActions;
	}

	public <T extends RendererFrontend> T createFrontend(Function<RendererContext, T> factory) {
		return factory.apply(rendererContext);
	}

}
