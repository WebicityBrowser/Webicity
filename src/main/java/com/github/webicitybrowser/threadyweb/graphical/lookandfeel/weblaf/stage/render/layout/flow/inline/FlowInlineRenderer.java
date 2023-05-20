package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.layout.flow.inline;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.FluidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderMessage;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.UnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidationRenderMessage;

public class FlowInlineRenderer implements FluidRenderer {

	private Box[] children;

	public FlowInlineRenderer(Box box, Box[] children) {
		this.children = children;
	}

	@Override
	public UnitGenerator renderFluid(RenderContext renderContext, ContextSwitch[] switches) {
		return new FlowInlineUnitGenerator(children, renderContext, switches);
	}
	
	@Override
	public void handleRenderMessage(RenderMessage renderMessage) {
		if (renderMessage instanceof TextConsolidationRenderMessage) {
			for (Box child: children) {
				// TODO: Don't create a new renderer when sending messages
				child.createRenderer().handleRenderMessage(renderMessage);
			}
		} else {
			renderMessage.handleDefault(this);
		}
	}

}
