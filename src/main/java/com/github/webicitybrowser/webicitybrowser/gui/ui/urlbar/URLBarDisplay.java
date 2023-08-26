package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.directive.core.style.StyleGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.webicitybrowser.component.URLBarComponent;

public class URLBarDisplay implements UIDisplay<URLBarContext, URLBarBox, URLBarUnit> {

	@Override
	public URLBarContext createContext(ComponentUI componentUI) {
		return URLBarContextGenerator.generateContext(this, componentUI);
	}

	@Override
	public List<URLBarBox> generateBoxes(URLBarContext displayContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		URLBarComponent owningComponent = (URLBarComponent) displayContext.componentUI().getComponent();
		DirectivePool styleDirectives = styleGenerator.getStyleDirectives();
		return List.of(new URLBarBox(owningComponent, styleDirectives, this, displayContext));
	}

	@Override
	public URLBarUnit renderBox(URLBarBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext) {
		return URLBarRenderer.render(box, renderContext, localRenderContext, this);
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
	
}
