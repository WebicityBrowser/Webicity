package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text;

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
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.DefaultGraphicalMessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.tree.basics.TextComponent;

public class TextDisplay implements UIDisplay<TextContext, TextBox, TextRenderedUnit> {

	@Override
	public TextContext createContext(ComponentUI componentUI) {
		return new TextContext(this, componentUI);
	}

	@Override
	public List<TextBox> generateBoxes(TextContext displayContext, BoxContext boxContext, StyleGenerator styleGenerator) {
		TextComponent component = (TextComponent) displayContext.componentUI().getComponent();
		DirectivePool directives = styleGenerator.getStyleDirectives();
		return List.of(new TextBox(this, component, directives));
	}

	@Override
	public RenderedUnitGenerator<TextRenderedUnit> renderBox(TextBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		return TextRenderer.createTextUnitGenerator(box, globalRenderContext, localRenderContext);
	}

	@Override
	public void paint(TextRenderedUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		TextPainter.paint(unit, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(TextRenderedUnit unit, Rectangle documentRect) {
		return new DefaultGraphicalMessageHandler(documentRect, unit.box());
	}

}
