package everyos.browser.webicitybrowser.component;

import everyos.desktop.thready.basic.component.ComponentBase;
import everyos.desktop.thready.core.graphics.image.Image;

public class CircularButtonComponent extends ComponentBase {

	private final Image image;
	
	public CircularButtonComponent(Image image) {
		this.image = image;
	}
	
	public Image getImage() {
		return this.image;
	}

}
