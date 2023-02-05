package everyos.browser.webicitybrowser.gui.ui.button;

import everyos.browser.webicitybrowser.component.CircularButtonComponent;
import everyos.desktop.thready.core.graphics.image.Image;
import everyos.desktop.thready.core.graphics.image.LoadedImage;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.gui.stage.render.SolidRenderer;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public class CircularButtonComponentRenderer implements SolidRenderer {

	private final Box box;

	public CircularButtonComponentRenderer(Box box) {
		this.box = box;
	}

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		LoadedImage image = loadImage(renderContext);
		return new CircularButtonUnit(box, image);
	}

	private LoadedImage loadImage(RenderContext renderContext) {
		Image image = ((CircularButtonComponent) box.getOwningComponent()).getImage();
		return renderContext
			.getResourceGenerator()
			.loadImage(image);
	}

}
