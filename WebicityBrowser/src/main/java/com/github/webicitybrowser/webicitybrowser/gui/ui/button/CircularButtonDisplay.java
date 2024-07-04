package com.github.webicitybrowser.webicitybrowser.gui.ui.button;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.image.Image;
import com.github.webicitybrowser.thready.drawing.core.image.ImageSource;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.GenericComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.GenericBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.context.GenericContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.DefaultGraphicalMessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.component.CircularButtonComponent;

public class CircularButtonDisplay implements UIDisplay<GenericContext, GenericBox<CircularButtonComponent, GenericContext>, CircularButtonUnit> {

	private static final UIDisplay<?, ?, ?> INSTANCE = new CircularButtonDisplay();

	@Override
	public GenericContext createContext(ComponentUI componentUI) {
		return new GenericContext(this, componentUI);
	}

	@Override
	public List<GenericBox<CircularButtonComponent, GenericContext>> generateBoxes(GenericContext displayContext, BoxContext boxContext) {
		return List.of(new GenericBox<>(displayContext));
	}

	@Override
	public CircularButtonUnit renderBox(GenericBox<CircularButtonComponent, GenericContext> box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		ImageSource imageSource = box.owningComponent().getImageSource();
		Image image = renderContext.resourceLoader().loadImage(imageSource);
		
		return new CircularButtonUnit(box, image);
	}

	@Override
	public void paint(CircularButtonUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		CircularButtonPainter.paint(unit, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(CircularButtonUnit unit, Rectangle documentRect) {
		return new DefaultGraphicalMessageHandler(documentRect, unit.box());
	}

	public static ComponentUI componentUI(Component component, ComponentUI parent) {
		return new GenericComponentUI(component, parent, INSTANCE);
	}

}
