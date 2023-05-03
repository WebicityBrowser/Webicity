package com.github.webicitybrowser.webicitybrowser.gui.ui.button;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.drawing.core.image.ImageSource;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.webicitybrowser.component.CircularButtonComponent;

public class CircularButtonComponentRenderer implements Renderer {

	private final Box box;
	private final CircularButtonComponent component;

	public CircularButtonComponentRenderer(Box box) {
		this.box = box;
		this.component = (CircularButtonComponent) box.getOwningComponent();
	}

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		ImageSource imageSource = component.getImageSource();
		Image image = renderContext.getResourceLoader().loadImage(imageSource);
		return new CircularButtonUnit(box, image);
	}

}
