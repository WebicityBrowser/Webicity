package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.GenericComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.GenericBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.webicitybrowser.component.URLBarComponent;

public class URLBarDisplay implements UIDisplay<URLBarContext, GenericBox<URLBarComponent, URLBarContext>, URLBarUnit> {

	private static final URLBarDisplay INSTANCE = new URLBarDisplay();

	@Override
	public URLBarContext createContext(ComponentUI componentUI) {
		URLBarContext context = URLBarContextGenerator.generateContext(this, componentUI);
		URLBarComponent owningComponent = (URLBarComponent) componentUI.getComponent();
		owningComponent.addURLChangeListener(() -> {
			componentUI.invalidate(InvalidationLevel.PAINT);
			context
				.textFieldContext()
				.getViewModel()
				.setWholeText(owningComponent.getValue());
		});

		return context;
	}

	@Override
	public List<GenericBox<URLBarComponent, URLBarContext>> generateBoxes(URLBarContext displayContext, BoxContext boxContext) {
		return List.of(new GenericBox<>(displayContext));
	}

	@Override
	public URLBarUnit renderBox(GenericBox<URLBarComponent, URLBarContext> box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		Font2D font = renderContext
			.resourceLoader()
			.loadFont(SimpleDirectiveUtil.getFontSettings(box.styleDirectives()));
		return new URLBarUnit(localRenderContext.preferredSize(), font, box);
	}

	@Override
	public void paint(URLBarUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		URLBarPainter.paint(unit, globalPaintContext, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(URLBarUnit unit, Rectangle documentRect) {
		Rectangle contentRect = unit.getContentRect(documentRect);
		return new URLBarMessageHandler(documentRect, contentRect, unit);
	}

	public static ComponentUI componentUI(Component component, ComponentUI parent) {
		return new GenericComponentUI(component, parent, INSTANCE);
	}
	
}
