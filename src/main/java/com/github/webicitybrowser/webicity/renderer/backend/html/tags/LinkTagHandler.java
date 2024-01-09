package com.github.webicitybrowser.webicity.renderer.backend.html.tags;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.fetch.FetchEngine;
import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.builder.FetchParametersBuilder;
import com.github.webicitybrowser.spec.fetch.builder.imp.FetchParametersBuilderImp;
import com.github.webicitybrowser.spec.fetch.imp.FetchNetworkError;
import com.github.webicitybrowser.spec.htmlbrowsers.tasks.EventLoop;
import com.github.webicitybrowser.spec.htmlbrowsers.tasks.TaskQueue;
import com.github.webicitybrowser.spec.url.InvalidURLException;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererContext;
import com.github.webicitybrowser.webicity.renderer.backend.html.stylesheetactions.LinkAction;
import com.github.webicitybrowser.webicity.renderer.backend.html.stylesheetactions.NoLinkAction;
import com.github.webicitybrowser.webicity.renderer.backend.html.stylesheetactions.StylesheetAction;
import com.github.webicitybrowser.webicity.renderer.backend.html.tasks.TaskQueueTaskDestination;

public class LinkTagHandler implements TagAction {

	private final HTMLRendererContext context;

	public LinkTagHandler(HTMLRendererContext context) {
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
			URL url = URL.of(
				context.rendererContext().getCurrentDocumentURL(),
				element.getAttribute("href"));
			request = FetchRequest.createRequest("GET", url);
		} catch (InvalidURLException e) {
			throw new RuntimeException(e);
		}

		if(!linkedResourceFetchSetupSteps(element, request)) {
			return;
		}

		FetchParametersBuilder parametersBuilder = new FetchParametersBuilderImp();
		TaskQueue networkQueue = context.eventLoop().getTaskQueue(EventLoop.NETWORK_TASK_QUEUE);

		parametersBuilder.setRequest(request);
		parametersBuilder.setTaskDestination(new TaskQueueTaskDestination(networkQueue));
		parametersBuilder.setConsumeBodyAction((response, success, body) -> {
			success = body != null && !(response instanceof FetchNetworkError);
			processTheLinkedResource(element, success, response, body);
		});

		FetchEngine fetchEngine = context.rendererContext().getRenderingEngine().getFetchEngine();
		fetchEngine.fetch(parametersBuilder.build());
	}

	private boolean linkedResourceFetchSetupSteps(Element element, FetchRequest request) {
		return true;
	}

	private void processTheLinkedResource(Element el, boolean success, FetchResponse response, byte[] bodyBytes) {
		if(!success || !el.hasAttribute("rel")) return;
		String rel = el.getAttribute("rel");

		for (String relValue : rel.split(" ")) {
			LinkAction stylesheetAction = switch (relValue) {
				case "stylesheet" -> new StylesheetAction();
				default -> new NoLinkAction();
			};

			stylesheetAction.processThisTypeOfLinkedResource(el, success, response, bodyBytes);	
		}
	}

}
