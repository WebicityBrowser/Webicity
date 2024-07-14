package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.GenericComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.display.wrapper.SimpleWrapperDisplay;
import com.github.webicitybrowser.thready.gui.graphical.message.basics.DefaultGraphicalMessageHandler;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class TextDisplay implements UIDisplay<TextContext, TextBox, TextUnit> {

	private static final UIDisplay<?, ?, ?> INSTANCE = new SimpleWrapperDisplay<>(new TextDisplay());

	@Override
	public TextContext createContext(ComponentUI componentUI) {
		return new TextContext(this, componentUI);
	}

	@Override
	public List<TextBox> generateBoxes(TextContext displayContext, BoxContext boxContext) {
		DirectivePool directives = displayContext.styleDirectives();
		return List.of(new TextBox(displayContext, directives));
	}

	@Override
	public TextUnit renderBox(TextBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		return TextRenderer.createTextUnit(box, globalRenderContext, localRenderContext);
	}

	@Override
	public void paint(TextUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		TextPainter.paint(unit, localPaintContext);
	}

	@Override
	public MessageHandler createMessageHandler(TextUnit unit, Rectangle documentRect) {
		return new DefaultGraphicalMessageHandler(documentRect, unit.box());
	}

	public static ComponentUI componentUI(Component component, ComponentUI parent) {
		return new GenericComponentUI(component, parent, INSTANCE);
	}

}
