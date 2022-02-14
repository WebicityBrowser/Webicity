package everyos.browser.webicitybrowser.gui.component;

import com.github.anythingide.lace.basics.pipeline.paint.canvas.shapes.ImageBuffer;
import com.github.anythingide.lace.imputils.ComponentBase;

public class CircularButton extends ComponentBase {
	
	private final ImageBuffer imageBuffer;

	public CircularButton(ImageBuffer imageBuffer) {
		this.imageBuffer = imageBuffer;
	}

	public ImageBuffer getImageBuffer() {
		return imageBuffer;
	}
	
}