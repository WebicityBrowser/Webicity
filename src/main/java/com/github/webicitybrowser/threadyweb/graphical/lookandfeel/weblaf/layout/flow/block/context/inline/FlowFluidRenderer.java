package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.render.unit.SingleRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.HorizontalLineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidation;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidationPrerenderMessage;

public final class FlowFluidRenderer {

	private FlowFluidRenderer() {}

	public static RenderedUnitGenerator<InnerDisplayUnit> render(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		List<Box> children = box.getChildrenTracker().getChildren();
		
		LineDimensionConverter dimensionConverter = new HorizontalLineDimensionConverter();
		ContextSwitch[] contextSwitches = createContextSwitches(children);
		AbsoluteSize precomputedInnerSize = localRenderContext.getPreferredSize();
		LineBoxContainer lines = new LineBoxContainer(globalRenderContext, precomputedInnerSize, dimensionConverter, contextSwitches);
		renderChildren(children, lines);
		
		LayoutResult result = lines.layout();
		InnerDisplayUnit unit = new InnerDisplayUnit(
			box.display(),
			result.fitSize(),
			result.childLayoutResults());
		
		return new SingleRenderedUnitGenerator<InnerDisplayUnit>(unit);
	}
	
	private static ContextSwitch[] createContextSwitches(List<Box> children) {
		TextConsolidation consolidation = collectText(children);
		TextAdjustContextSwitch adjustSwitch = createTextAdjustContextSwitch(consolidation);
		return new ContextSwitch[] { adjustSwitch };
	}

	private static void renderChildren(List<Box> children, LineBoxContainer lines) {
		for (Box child: children) {
			lines.addBox(child);
		}
	}

	private static TextConsolidation collectText(List<Box> children) {
		TextConsolidation consolidation = TextConsolidation.create();
		for (Box child: children) {
			child.message((TextConsolidationPrerenderMessage) () -> consolidation);
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
