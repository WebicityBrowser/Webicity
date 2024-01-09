package com.github.webicitybrowser.webicity.renderer.frontend.thready.html.component.image;

import com.github.webicitybrowser.threadyweb.context.image.ImageRequest;
import com.github.webicitybrowser.threadyweb.context.image.ImageState;
import com.github.webicitybrowser.threadyweb.context.image.ImageRequest.ImageRequestState;

public class ImageStateImp implements ImageState {

	private ImageRequest currentRequest = new ImageRequest(ImageRequestState.BROKEN, null);
	private ImageRequest pendingRequest;

	private Runnable onImageStateUpdateCallback;

	@Override
	public ImageRequest getCurrentRequest() {
		return currentRequest;
	}

	@Override
	public ImageRequest getPendingRequest() {
		return pendingRequest;
	}

	@Override
	public void setCurrentRequest(ImageRequest request) {
		this.currentRequest = request;
		request.addOnStateUpdateCallback(this::runOnImageStateUpdateCallback);
		runOnImageStateUpdateCallback();
	}

	@Override
	public void setPendingRequest(ImageRequest request) {
		this.pendingRequest = request;
	}

	@Override
	public void onImageStateUpdate(Runnable callback) {
		this.onImageStateUpdateCallback = callback;
	}

	private void runOnImageStateUpdateCallback() {
		if (onImageStateUpdateCallback != null) {
			onImageStateUpdateCallback.run();
		}
	}

}
