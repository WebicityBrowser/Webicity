package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.br;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.GenericComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.context.GenericContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.message.MessageHandler;
import com.github.webicitybrowser.thready.gui.message.NoopMessageHandler;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class BreakDisplay implements UIDisplay<GenericContext, BreakBox, BreakUnit> {

	private static final BreakDisplay INSTANCE = new BreakDisplay();

	@Override
	public GenericContext createContext(ComponentUI componentUI) {
		return new GenericContext(this, componentUI);
	}

	@Override
	public List<BreakBox> generateBoxes(GenericContext displayContext, BoxContext boxContext) {
		return List.of(new BreakBox(
			this, displayContext.componentUI(), displayContext.styleDirectives()
		));
	}

	@Override
	public BreakUnit renderBox(BreakBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		return new BreakUnit(INSTANCE, new AbsoluteSize(0, 0), box.styleDirectives());
	}

	@Override
	public void paint(BreakUnit unit, GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		// Do nothing
	}

	@Override
	public MessageHandler createMessageHandler(BreakUnit unit, Rectangle documentRect) {
		return new NoopMessageHandler();
	}

	public static ComponentUI componentUI(Component component, ComponentUI parent) {
		return new GenericComponentUI(component, parent, INSTANCE);
	}

}
