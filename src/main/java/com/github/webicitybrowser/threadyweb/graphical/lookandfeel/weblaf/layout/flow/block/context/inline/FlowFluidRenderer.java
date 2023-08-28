package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.FlowBlockRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.UnitExitMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.HorizontalLineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;

public final class FlowFluidRenderer {
	
	private FlowFluidRenderer() {}

	public static LayoutResult render(FlowBlockRenderContext context) {
		LineDimensionConverter lineDimensionConverter = new HorizontalLineDimensionConverter();
		FlowFluidRendererState state = new FlowFluidRendererState(lineDimensionConverter, context);
		ChildrenBox box = context.box();

		FlowFluidTextRenderer.preadjustTextBoxes(state, box);

		for (Box childBox: box.getChildrenTracker().getChildren()) {
			addBoxToLine(state, childBox);
		}

		return createInnerDisplayUnit(box, state);
	}

	private static void addBoxToLine(FlowFluidRendererState state, Box childBox) {
		if (childBox instanceof TextBox textBox) {
			FlowFluidTextRenderer.addTextBoxToLine(state, textBox);
		} else if (childBox.managesSelf()) {
			addSelfManagedBoxToLine(state, childBox);
		} else {
			addInlineBoxToLine(state, childBox);
		}
	}

	private static void addSelfManagedBoxToLine(FlowFluidRendererState state, Box childBox) {
		LocalRenderContext localRenderContext = state.getLocalRenderContext();
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		// TODO: We need to use a custom localRenderContext.
		RenderedUnit boxUnit = UIPipeline.render(childBox, globalRenderContext, localRenderContext);
		startNewLineIfNotFits(state, boxUnit.preferredSize());
		state.currentLine().add(boxUnit);
	}

	private static void addInlineBoxToLine(FlowFluidRendererState state, Box childBox) {
		assert childBox instanceof ChildrenBox;
		UnitEnterMarker unitEnterMarker = new UnitEnterMarker(true, childBox.styleDirectives());
		startNewLineIfNotFits(state, createSizeFromUnitEnterMarker(unitEnterMarker));
		state.currentLine().addMarker(unitEnterMarker);
		for (Box inlineChildBox: ((ChildrenBox) childBox).getChildrenTracker().getChildren()) {
			addBoxToLine(state, inlineChildBox);
		}
		UnitExitMarker unitExitMarker = new UnitExitMarker(childBox.styleDirectives());
		startNewLineIfNotFits(state, createSizeFromUnitExitMarker(unitExitMarker));
		state.currentLine().addMarker(unitExitMarker);
	}

	private static AbsoluteSize createSizeFromUnitEnterMarker(UnitEnterMarker marker) {
		return new AbsoluteSize(marker.leftEdgeSize() , marker.topEdgeSize());
	}

	private static AbsoluteSize createSizeFromUnitExitMarker(UnitExitMarker marker) {
		return new AbsoluteSize(marker.rightEdgeSize() , marker.bottomEdgeSize());
	}

	private static void startNewLineIfNotFits(FlowFluidRendererState state, AbsoluteSize preferredSize) {
		LineBox currentLine = state.currentLine();
		LocalRenderContext localRenderContext = state.getLocalRenderContext();
		if (!currentLine.canFit(preferredSize, localRenderContext.getPreferredSize())) {
			state.startNewLine();
		}
	}

	private static LayoutResult createInnerDisplayUnit(ChildrenBox box, FlowFluidRendererState state) {
		List<ChildLayoutResult> childLayoutResults = new ArrayList<>();

		AbsolutePosition linePosition = new AbsolutePosition(0, 0);
		AbsoluteSize totalSize = new AbsoluteSize(0, 0);
		for (LineBox line: state.lines()) {
			List<ChildLayoutResult> lineChildLayoutResults = line.layoutAtPos(linePosition);
			childLayoutResults.addAll(lineChildLayoutResults);
			// TODO: Use the LineDimensionConverter so we can handle vertical lines.
			linePosition = new AbsolutePosition(0, linePosition.y() + line.getSize().height());
			totalSize = new AbsoluteSize(
				Math.max(totalSize.width(), line.getSize().width()),
				Math.max(totalSize.height(), linePosition.y())
			);
		}

		return LayoutResult.create(childLayoutResults.toArray(ChildLayoutResult[]::new), totalSize);
	}

}
