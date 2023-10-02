package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.contexts.LineContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline.marker.UnitExitMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimension;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimension.LineDirection;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;

public final class FlowInlineRenderer {
	
	private FlowInlineRenderer() {}

	public static LayoutResult render(FlowRenderContext context) {
		LineDirection lineDirection = LineDirection.LTR;
		FlowInlineRendererState state = new FlowInlineRendererState(lineDirection, context);
		ChildrenBox box = context.box();

		prepareTextRendering(state, box);

		for (Box childBox: box.getChildrenTracker().getChildren()) {
			addBoxToLine(state, childBox);
		}

		return createInnerDisplayUnit(box, state);
	}

	private static void prepareTextRendering(FlowInlineRendererState state, ChildrenBox box) {
		FlowRenderContext context = state.flowContext();
		state.getFontStack().push(FlowUtils.computeFont(
			context, context.box().styleDirectives(), context.localRenderContext().getParentFontMetrics()));
		FlowInlineTextRenderer.preadjustTextBoxes(state, box);
	}

	private static void addBoxToLine(FlowInlineRendererState state, Box childBox) {
		if (childBox instanceof TextBox textBox) {
			FlowInlineTextRenderer.addTextBoxToLine(state, textBox);
		} else if (childBox.managesSelf()) {
			FlowInlineSelfManagedRenderer.addSelfManagedBoxToLine(state, childBox);
		} else {
			assert childBox instanceof ChildrenBox;
			addInlineBoxToLine(state, (ChildrenBox) childBox);
		}
	}

	private static void addInlineBoxToLine(FlowInlineRendererState state, ChildrenBox childBox) {
		pushFormattingInfo(state, childBox);

		for (Box inlineChildBox: childBox.getChildrenTracker().getChildren()) {
			addBoxToLine(state, inlineChildBox);
		}
		
		popFormattingInfo(state, childBox);
	}

	private static void pushFormattingInfo(FlowInlineRendererState state, Box childBox) {
		LineContext lineContext = state.lineContext();
		
		UnitEnterMarker unitEnterMarker = new UnitEnterMarker(true, childBox.styleDirectives());
		FlowInlineRendererUtil.startNewLineIfNotFits(state, createSizeFromUnitEnterMarker(unitEnterMarker));
		lineContext.currentLine().addMarker(unitEnterMarker);

		FontMetrics parentFontMetrics = state.getFontStack().peek().getMetrics();
		state.getFontStack().push(FlowUtils.computeFont(state.flowContext(), childBox.styleDirectives(), parentFontMetrics));
	}

	private static void popFormattingInfo(FlowInlineRendererState state, Box childBox) {
		LineContext lineContext = state.lineContext();

		UnitExitMarker unitExitMarker = new UnitExitMarker(childBox.styleDirectives());
		FlowInlineRendererUtil.startNewLineIfNotFits(state, createSizeFromUnitExitMarker(unitExitMarker));
		lineContext.currentLine().addMarker(unitExitMarker);

		state.getFontStack().pop();
	}

	private static AbsoluteSize createSizeFromUnitEnterMarker(UnitEnterMarker marker) {
		return new AbsoluteSize(marker.leftEdgeSize(), marker.topEdgeSize());
	}

	private static AbsoluteSize createSizeFromUnitExitMarker(UnitExitMarker marker) {
		return new AbsoluteSize(marker.rightEdgeSize(), marker.bottomEdgeSize());
	}

	private static LayoutResult createInnerDisplayUnit(ChildrenBox box, FlowInlineRendererState state) {
		List<ChildLayoutResult> childLayoutResults = new ArrayList<>();
		LineDirection lineDirection = state.lineContext().lineDirection();

		LineDimension linePosition = new LineDimension(0, 0, lineDirection);
		LineDimension totalSize = new LineDimension(0, 0, lineDirection);

		for (LineBox line: state.lineContext().lines()) {
			childLayoutResults.addAll(layoutFinalLine(line, linePosition, state));

			LineDimension lineSize = LineDimensionConverter.convertToLineDimension(line.getSize(), lineDirection);
			linePosition = new LineDimension(0, linePosition.depth() + lineSize.depth(), lineDirection);

			totalSize = new LineDimension(Math.max(totalSize.run(), lineSize.run()), linePosition.depth(), lineDirection);
		}

		AbsoluteSize absoluteTotalSize = LineDimensionConverter.convertToAbsoluteSize(totalSize);
		return LayoutResult.create(childLayoutResults.toArray(ChildLayoutResult[]::new), absoluteTotalSize);
	}

	private static List<ChildLayoutResult> layoutFinalLine(
		LineBox line, LineDimension linePosition, FlowInlineRendererState state
	) {
		FlowRootContextSwitch flowRootContextSwitch = state.flowContext().flowRootContextSwitch();
		LineDimension actualLinePosition = LineOffsetCalculator.offsetLinePosition(linePosition, line, flowRootContextSwitch);
		
		return line.layoutAtPos(actualLinePosition);
	}

}
