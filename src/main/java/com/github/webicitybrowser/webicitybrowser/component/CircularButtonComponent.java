package com.github.webicitybrowser.webicitybrowser.component;

import com.github.webicitybrowser.thready.drawing.core.image.ImageSource;
import com.github.webicitybrowser.thready.gui.tree.basics.imp.BaseComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class CircularButtonComponent extends BaseComponent {

	private final ImageSource imageSource;
	
	public CircularButtonComponent(ImageSource imageSource) {
		this.imageSource = imageSource;
	}
	
	public ImageSource getImageSource() {
		return this.imageSource;
	}

	@Override
	public Class<? extends Component> getPrimaryType() {
		return CircularButtonComponent.class;
	}

}
