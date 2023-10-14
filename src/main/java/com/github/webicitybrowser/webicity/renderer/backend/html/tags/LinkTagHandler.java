package com.github.webicitybrowser.webicity.renderer.backend.html.tags;

import com.github.webicitybrowser.spec.css.rule.CSSRuleList;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.FetchConsumeBodyAction;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.spec.fetch.builder.FetchParametersBuilder;
import com.github.webicitybrowser.spec.fetch.builder.imp.FetchParametersBuilderImp;
import com.github.webicitybrowser.spec.fetch.imp.FetchResponseImp;
import com.github.webicitybrowser.spec.fetch.taskdestination.ParallelQueue;
import com.github.webicitybrowser.spec.url.InvalidURLException;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.renderer.RendererContext;
import com.github.webicitybrowser.webicity.renderer.backend.html.CSSRulesUtils;
import com.github.webicitybrowser.webicity.renderer.backend.html.stylesheetactions.NoStylesheetAction;
import com.github.webicitybrowser.webicity.renderer.backend.html.stylesheetactions.StylesheetAction;
import com.github.webicitybrowser.webicity.renderer.backend.html.stylesheetactions.StylesheetLink;

import java.io.InputStreamReader;

public class LinkTagHandler implements TagAction{

	private final RendererContext context;

	public LinkTagHandler(RendererContext context) {
		this.context = context;
	}

	@Override
	public void onTagParsed(Element element) {
		TagAction.super.onTagParsed(element);
		handleTag(element);
	}

	public void handleTag(Element element) {
		if(!element.hasAttribute("href") && !element.hasAttribute("imagesrcset")) {
			return;
		}
		defaultFetchAndProcessLinkResource(element);
	}

	private void defaultFetchAndProcessLinkResource(Element element) {
		FetchRequest request = null;
		try {
			request = FetchRequest.createRequest("GET", URL.of(context.currentDocumentURL(), element.getAttribute("href")));
		} catch (InvalidURLException e) {
			throw new RuntimeException(e);
		}

		if(!linkedResourceFetchSetupSteps(element, request)) {
			return;
		}

		FetchParametersBuilder parametersBuilder = new FetchParametersBuilderImp();
		parametersBuilder.setRequest(request);
		parametersBuilder.setTaskDestination(new ParallelQueue());
		parametersBuilder.setConsumeBodyAction(new FetchConsumeBodyAction() {
			@Override
			public void execute(FetchResponse response, boolean success, byte[] body) {
				success = true;
				if(response.body().source() == null || !(response instanceof FetchResponseImp)) {
					success = false;
				}
				processStylesheetResource(element, success, response, body);
			}
		});
		FetchEngine fetchEngine = context.getFetchEngine();
		fetchEngine.fetch(parametersBuilder.build());

	}

	private boolean linkedResourceFetchSetupSteps(Element element, FetchRequest request) {
		return true;
	}

	private void processStylesheetResource(Element el, boolean success, FetchResponse response, byte[] bodyBytes) {
		StylesheetAction stylesheetAction = new NoStylesheetAction();
		if(el.getAttribute("rel").equals("stylesheet")) {
			stylesheetAction = new StylesheetLink();
		}
		stylesheetAction.processThisTypeOfLinkedResource(el, success, response, bodyBytes);
	}

}
