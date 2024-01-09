package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.NoopMessageHandler;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.alt.ImageAltPainter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.alt.ImageAltRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.alt.ImageAltUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.loaded.LoadedImagePainter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.loaded.LoadedImageRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image.loaded.LoadedImageUnit;
import com.github.webicitybrowser.threadyweb.tree.image.ImageComponent;
import com.github.webicitybrowser.threadyweb.tree.image.ImageStatus;

public class ImageDisplay implements UIDisplay<ImageContext, ImageBox, ImageUnit> {

	@Override
	public ImageContext createContext(ComponentUI componentUI) {
		ImageComponent component = (ImageComponent) componentUI.getComponent();
		component.onImageStatusUpdate(_1 -> componentUI.invalidate(InvalidationLevel.RENDER));
		return new ImageContext(this, componentUI);
	}

	@Override
	public List<ImageBox> generateBoxes(ImageContext displayContext, BoxContext boxContext,StyleGenerator styleGenerator) {
		ImageComponent component = (ImageComponent) displayContext.componentUI().getComponent();
		DirectivePool directives = styleGenerator.getStyleDirectives();
		return List.of(new ImageBox(this, component, directives));
	}

	@Override
	public ImageUnit renderBox(ImageBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		ImageStatus imageStatus = box.owningComponent().getImageStatus();
		if (imageStatus.canImageBeShown()) {
			return LoadedImageRenderer.render(box, globalRenderContext, localRenderContext, imageStatus.imageData());
		} else {
			String altText = imageStatus.imageAltText();
			return ImageAltRenderer.render(box, globalRenderContext, localRenderContext, altText);
		}
	}

	@Override
	public void paint(ImageUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		if (unit instanceof ImageAltUnit ImageAltUnit) {
			ImageAltPainter.paint(ImageAltUnit, globalPaintContext, localPaintContext);
		} else if (unit instanceof LoadedImageUnit loadedImageUnit) {
			LoadedImagePainter.paint(loadedImageUnit, globalPaintContext, localPaintContext);
		} else {
			throw new UnsupportedOperationException("ImagePainter.paint");
		}
	}

	@Override
	public MessageHandler createMessageHandler(ImageUnit unit, Rectangle documentRect) {
		return new NoopMessageHandler();
	}

}
