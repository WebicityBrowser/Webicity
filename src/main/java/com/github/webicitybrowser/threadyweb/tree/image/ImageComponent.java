package com.github.webicitybrowser.threadyweb.tree.image;

import java.util.function.Consumer;

import com.github.webicitybrowser.spec.dom.node.Element;
import com.github.webicitybrowser.threadyweb.context.WebComponentContext;
import com.github.webicitybrowser.threadyweb.tree.WebComponent;
import com.github.webicitybrowser.threadyweb.tree.imp.ImageWebComponent;

public interface ImageComponent extends WebComponent {

	ImageStatus getImageStatus();

	void onImageStatusUpdate(Consumer<ImageStatus> callback);

	static ImageComponent create(Element element, WebComponentContext componentContext) {
		return new ImageWebComponent(element, componentContext);
	}
	
}
