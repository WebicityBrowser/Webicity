package com.github.webicitybrowser.webicity.renderer.backend.html;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.spec.html.binding.BindingHTMLTreeBuilder;
import com.github.webicitybrowser.spec.html.node.HTMLDocument;
import com.github.webicitybrowser.spec.html.parse.CharacterReferenceLookup;
import com.github.webicitybrowser.spec.html.parse.HTMLTreeBuilder;
import com.github.webicitybrowser.spiderhtml.SpiderHTMLParserImp;
import com.github.webicitybrowser.webicity.core.net.Connection;
import com.github.webicitybrowser.webicity.core.renderer.RendererBackend;
import com.github.webicitybrowser.webicity.core.renderer.RendererContext;
import com.github.webicitybrowser.webicity.core.renderer.RendererFrontend;
import com.github.webicitybrowser.webicity.renderer.backend.html.tags.LinkTagHandler;
import com.github.webicitybrowser.webicity.renderer.backend.html.tags.StyleTagHandler;
import com.github.webicitybrowser.webicity.renderer.backend.html.tasks.EventLoopImp;
import com.github.webicitybrowser.webicity.renderer.backend.html.tasks.EventScheduler;
import com.github.webicitybrowser.webicity.renderer.backend.html.tasks.EventSchedulerImp;


public class HTMLRendererBackend implements RendererBackend {
	
	private static final Logger logger = LoggerFactory.getLogger(HTMLRendererBackend.class);
	
	private final HTMLRendererContext htmlRendererContext;
	private final CharacterReferenceLookup characterReferenceLookup;
	private final HTMLDocument document;
	private final EventScheduler eventScheduler;

	public HTMLRendererBackend(
		RendererContext rendererContext, Connection connection, CharacterReferenceLookup characterReferenceLookup
	) throws IOException {
		this.htmlRendererContext = createHTMLRendererContext(rendererContext);
		this.characterReferenceLookup = characterReferenceLookup;
		this.document = HTMLDocument.create();
		this.eventScheduler = new EventSchedulerImp(htmlRendererContext.eventLoop());
		parseAndTimeDocument(connection);
	}

	@Override
	public String getTitle() {
		return document.getTitle();
	}

	@Override
	public void tick() {
		eventScheduler.tick();
	}

	public HTMLDocument getDocument() {
		return this.document;
	}
	
	private void parseAndTimeDocument(Connection connection) throws IOException {
		long time = System.currentTimeMillis();
		parseDocument(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
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
		tagActions.registerTagAction("link", new LinkTagHandler(htmlRendererContext));
		tagActions.registerTagAction("style", new StyleTagHandler());
		return tagActions;
	}

	public <T extends RendererFrontend> T createFrontend(Function<HTMLRendererContext, T> factory) {
		return factory.apply(htmlRendererContext);
	}

	private HTMLRendererContext createHTMLRendererContext(RendererContext rendererContext) {
		return new HTMLRendererContext(rendererContext, new EventLoopImp());
	}

}
