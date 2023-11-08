package com.github.webicitybrowser.threadyweb.context.image;

public interface ImageState {
	
	ImageRequest getCurrentRequest();

	ImageRequest getPendingRequest();

	void setCurrentRequest(ImageRequest request);

	void setPendingRequest(ImageRequest request);

	void onImageStateUpdate(Runnable callback);

}
