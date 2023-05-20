package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.layout.flow.block.context.inline;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.layout.flow.cursor.HorizontalLineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidation;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidationRenderMessage;

public final class ElementFluidRenderer {
	
	private ElementFluidRenderer() {}

	public static Unit render(RenderContext renderContext, AbsoluteSize precomputedInnerSize, Box[] children) {
		LineDimensionConverter dimensionConverter = new HorizontalLineDimensionConverter();
		ContextSwitch[] contextSwitches = createContextSwitches(children);
		LineBoxContainer lines = new LineBoxContainer(renderContext, precomputedInnerSize, dimensionConverter, contextSwitches);
		renderChildren(children, lines);
		
		LineBoxContainerResult result = lines.collectRenderResults();
		return new FluidChildrenUnit(result.size(), result.childResults());
	}
	
	private static ContextSwitch[] createContextSwitches(Box[] children) {
		TextConsolidation consolidation = collectText(children);
		TextAdjustContextSwitch adjustSwitch = createTextAdjustContextSwitch(consolidation);
		
		return new ContextSwitch[] { adjustSwitch };
	}

	private static void renderChildren(Box[] children, LineBoxContainer lines) {
		for (Box child: children) {
			lines.addBox(child);
		}
	}

	private static TextConsolidation collectText(Box[] children) {
		TextConsolidation consolidation = TextConsolidation.create();
		for (Box child: children) {
			// TODO: Don't create a new renderer when sending messages
			child.createRenderer().handleRenderMessage((TextConsolidationRenderMessage) () -> consolidation);
		}
		return consolidation;
	}

	private static TextAdjustContextSwitch createTextAdjustContextSwitch(TextConsolidation consolidation) {
		return new TextAdjustContextSwitch() {
			@Override
			public String getNextText(Object textOwner) {
				return consolidation.readNextText(textOwner);
			}
		};
	}
	
}
