package com.github.webicitybrowser.threadyweb.context.image;

import com.github.webicitybrowser.codec.image.ImageData;
import com.github.webicitybrowser.spec.url.URL;

public class ImageRequest {

	private final URL url;

	private ImageRequestState state;
	private ImageData imageData;

	private Runnable onStateUpdateCallback;

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
		runOnStateUpdateCallback();
	}

	public ImageData getImageData() {
		return imageData;
	}

	public void setImageData(ImageData imageData) {
		this.imageData = imageData;
		runOnStateUpdateCallback();
	}
	
	public static enum ImageRequestState {
		UNAVAILABLE, PARTIALLY_AVAILABLE, COMPLETELY_AVAILABLE, BROKEN
	}

	public void addOnStateUpdateCallback(Runnable callback) {
		this.onStateUpdateCallback = callback;
	}

	private void runOnStateUpdateCallback() {
		if (onStateUpdateCallback != null) {
			onStateUpdateCallback.run();
		}
	}

}
