package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.component.image;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.webicitybrowser.codec.image.ImageCodec;
import com.github.webicitybrowser.codec.image.PossibleImage;
import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.fetch.FetchParameters;
import com.github.webicitybrowser.spec.fetch.FetchRequest;
import com.github.webicitybrowser.spec.fetch.FetchResponse;
import com.github.webicitybrowser.spec.fetch.builder.FetchParametersBuilder;
import com.github.webicitybrowser.spec.fetch.taskdestination.TaskDestination;
import com.github.webicitybrowser.spec.htmlbrowsers.tasks.EventLoop;
import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.threadyweb.context.image.ImageEngine;
import com.github.webicitybrowser.threadyweb.context.image.ImageRequest;
import com.github.webicitybrowser.threadyweb.context.image.ImageRequest.ImageRequestState;
import com.github.webicitybrowser.threadyweb.context.image.ImageState;
import com.github.webicitybrowser.webicity.core.image.ImageCodecRegistry;
import com.github.webicitybrowser.webicity.renderer.backend.html.HTMLRendererContext;
import com.github.webicitybrowser.webicity.renderer.backend.html.tasks.TaskQueueTaskDestination;

public class ImageEngineImp implements ImageEngine {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageEngineImp.class);

	private final HTMLRendererContext htmlRendererContext;

	public ImageEngineImp(HTMLRendererContext rendererContext) {
		this.htmlRendererContext = rendererContext;
	}

	@Override
	public ImageState createImageState() {
		return new ImageStateImp();
	}

	@Override
	public void updateImageData(ImageState imageState, Element element) {
		// TODO: Continue implementing
		String urlString = selectImageSource(element);
		URL url = tryParseURL(urlString);
		if (url == null) return;
		ImageRequest imageRequest = new ImageRequest(ImageRequestState.UNAVAILABLE, url);
		imageState.setCurrentRequest(imageRequest);
		FetchRequest request = new FetchRequest("GET", url);
		// TODO Instead use processResponse
		FetchParameters parameters = FetchParametersBuilder.create()
			.setRequest(request)
			.setConsumeBodyAction((response, success, body) -> onImageLoadComplete(imageState, imageRequest, response, success, body))
			.setTaskDestination(createTaskDestination())
			.build();
		htmlRendererContext.rendererContext().getRenderingEngine().getFetchEngine().fetch(parameters);
	}

	private void onImageLoadComplete(ImageState imageState, ImageRequest request, FetchResponse response, boolean success, byte[] body) {
		// TODO: We need to properly parse the mime type - sometimes it has a charset or something
		// There is a section in the mime sniff spec that tells us how to do so, but I don't know if we directly call it or what
		String imageType = response.headerList().getHeaderValue("Content-Type");
		ImageCodecRegistry imageLoaderRegistry = htmlRendererContext.rendererContext().getRenderingEngine().getImageLoaderRegistry();
		ImageCodec imageLoader = imageLoaderRegistry.getImageLoaderForType(imageType);
		PossibleImage possibleImageData = imageLoader.loadImage(body, _1 -> {});

		possibleImageData.imageData().ifPresent(imageData -> {
			request.setState(ImageRequestState.COMPLETELY_AVAILABLE);
			request.setImageData(imageData);
		});
		possibleImageData.exception().ifPresent(exception -> {
			LOGGER.error("Failed to load image", exception);
			request.setState(ImageRequestState.UNAVAILABLE);
		});
	}

	private String selectImageSource(Element element) {
		return element.hasAttribute("src") ?
			element.getAttribute("src") :
			null;
	}

	private URL tryParseURL(String urlString) {
		if (urlString == null) return null;

		URL baseURL = htmlRendererContext.rendererContext().getCurrentDocumentURL();
		try {
			return URL.of(baseURL, urlString);
		} catch (Exception e) {
			return null;
		}
	}

	private TaskDestination createTaskDestination() {
		return new TaskQueueTaskDestination(htmlRendererContext.eventLoop().getTaskQueue(EventLoop.NETWORK_TASK_QUEUE));
	}
	
}
