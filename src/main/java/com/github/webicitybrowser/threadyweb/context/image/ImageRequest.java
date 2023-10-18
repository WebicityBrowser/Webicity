package com.github.webicitybrowser.threadyweb.context.image;

import com.github.webicitybrowser.spec.url.URL;
import com.github.webicitybrowser.webicity.core.image.ImageData;

public class ImageRequest {

	private final URL url;

	private ImageRequestState state;
	private ImageData imageData;

	public ImageRequest(ImageRequestState imageRequestState, URL url) {
		this.url = url;

		this.state = imageRequestState;
		this.imageData = null;
	}

	public URL getUrl() {
		return url;
	}

	public ImageRequestState getState() {
		return state;
	}

	public void setState(ImageRequestState state) {
		this.state = state;
	}

	public ImageData getImageData() {
		return imageData;
	}

	public void setImageData(ImageData imageData) {
		this.imageData = imageData;
	}
	
	public static enum ImageRequestState {
		UNAVAILABLE, PARTIALLY_AVAILABLE, COMPLETELY_AVAILABLE, BROKEN
	}

}
