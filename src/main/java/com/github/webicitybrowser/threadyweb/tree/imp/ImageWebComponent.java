package com.github.webicitybrowser.threadyweb.tree.imp;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.spec.dom.node.Node;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.context.WebComponentContext;
import com.github.webicitybrowser.threadyweb.context.image.ImageEngine;
import com.github.webicitybrowser.threadyweb.context.image.ImageRequest;
import com.github.webicitybrowser.threadyweb.context.image.ImageState;
import com.github.webicitybrowser.threadyweb.tree.image.ImageComponent;
import com.github.webicitybrowser.threadyweb.tree.image.ImageStatus;

public class ImageWebComponent extends BaseWebComponent implements ImageComponent {

	private final Element element;
	private final ImageEngine imageEngine;
	private final ImageState imageState;

	private Consumer<ImageStatus> onImageStatusUpdateCallback;

	public ImageWebComponent(Element element, WebComponentContext componentContext) {
		this.element = element;
		this.imageEngine = componentContext.getContext(ImageEngine.class);
		this.imageState = imageEngine.createImageState();
		imageState.onImageStateUpdate(this::onImageStateUpdate);
		imageEngine.updateImageData(imageState, element);
	}

	@Override
	public Node getNode() {
		return element;
	}

	@Override
	public Class<? extends Component> getPrimaryType() {
		return ImageComponent.class;
	}

	@Override
	public ImageStatus getImageStatus() {
		ImageRequest imageRequest = imageState.getCurrentRequest();
		boolean canImageBeShown =
			imageRequest.getState() == ImageRequest.ImageRequestState.COMPLETELY_AVAILABLE ||
			imageRequest.getState() == ImageRequest.ImageRequestState.PARTIALLY_AVAILABLE;
		canImageBeShown = canImageBeShown && imageRequest.getImageData() != null;

		String altText = element.hasAttribute("alt") ? element.getAttribute("alt") : "Image";
		return new ImageStatus(canImageBeShown, imageRequest.getImageData(), altText);
	}

	@Override
	public void onImageStatusUpdate(Consumer<ImageStatus> callback) {
		this.onImageStatusUpdateCallback = callback;
	}

	private void onImageStateUpdate() {
		if (onImageStatusUpdateCallback != null) {
			onImageStatusUpdateCallback.accept(getImageStatus());
		}
	}
	
}
