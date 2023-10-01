package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
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
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.FloatTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;

public final class FlowInlineRenderer {
	
	private FlowInlineRenderer() {}

	public static LayoutResult render(FlowRenderContext context) {
		LineDirection lineDirection = LineDirection.LTR;
		FlowInlineRendererState state = new FlowInlineRendererState(lineDirection, context);
		ChildrenBox box = context.box();

		state.getFontStack().push(FlowUtils.computeFont(
			context, context.box().styleDirectives(), context.localRenderContext().getParentFontMetrics()));
		FlowInlineTextRenderer.preadjustTextBoxes(state, box);

		for (Box childBox: box.getChildrenTracker().getChildren()) {
			addBoxToLine(state, childBox);
		}

		return createInnerDisplayUnit(box, state);
	}

	private static void addBoxToLine(FlowInlineRendererState state, Box childBox) {
		if (childBox instanceof TextBox textBox) {
			FlowInlineTextRenderer.addTextBoxToLine(state, textBox);
		} else if (childBox.managesSelf()) {
			FlowInlineSelfManagedRenderer.addSelfManagedBoxToLine(state, childBox);
		} else {
			addInlineBoxToLine(state, childBox);
		}
	}

	private static void addInlineBoxToLine(FlowInlineRendererState state, Box childBox) {
		assert childBox instanceof ChildrenBox;
		LineContext lineContext = state.lineContext();
		UnitEnterMarker unitEnterMarker = new UnitEnterMarker(true, childBox.styleDirectives());
		FontMetrics parentFontMetrics = state.getFontStack().peek().getMetrics();
		state.getFontStack().push(FlowUtils.computeFont(state.flowContext(), childBox.styleDirectives(), parentFontMetrics));
		FlowInlineRendererUtil.startNewLineIfNotFits(state, createSizeFromUnitEnterMarker(unitEnterMarker));
		lineContext.currentLine().addMarker(unitEnterMarker);
		for (Box inlineChildBox: ((ChildrenBox) childBox).getChildrenTracker().getChildren()) {
			addBoxToLine(state, inlineChildBox);
		}
		UnitExitMarker unitExitMarker = new UnitExitMarker(childBox.styleDirectives());
		FlowInlineRendererUtil.startNewLineIfNotFits(state, createSizeFromUnitExitMarker(unitExitMarker));
		lineContext.currentLine().addMarker(unitExitMarker);
		state.getFontStack().pop();
	}

	private static AbsoluteSize createSizeFromUnitEnterMarker(UnitEnterMarker marker) {
		return new AbsoluteSize(marker.leftEdgeSize() , marker.topEdgeSize());
	}

	private static AbsoluteSize createSizeFromUnitExitMarker(UnitExitMarker marker) {
		return new AbsoluteSize(marker.rightEdgeSize() , marker.bottomEdgeSize());
	}

	private static LayoutResult createInnerDisplayUnit(ChildrenBox box, FlowInlineRendererState state) {
		List<ChildLayoutResult> childLayoutResults = new ArrayList<>();
		FlowRootContextSwitch flowRootContextSwitch = state.flowContext().flowRootContextSwitch();
		LineDirection lineDirection = state.lineContext().lineDirection();


		LineDimension linePosition = new LineDimension(0, 0, lineDirection);
		AbsoluteSize maxSize = state.getLocalRenderContext().getPreferredSize();
		AbsoluteSize totalSize = new AbsoluteSize(0, 0);
		for (LineBox line: state.lineContext().lines()) {
			childLayoutResults.addAll(layoutFinalLine(line, linePosition, flowRootContextSwitch, maxSize));
			float lineDepth = LineDimensionConverter.convertToLineDimension(line.getSize(), lineDirection).depth();

			AbsoluteSize lineSize = line.getSize();
			AbsolutePosition absoluteLinePosition = LineDimensionConverter.convertToAbsolutePosition(linePosition, maxSize, lineSize);
			totalSize = new AbsoluteSize(
				Math.max(totalSize.width(), lineSize.width()),
				Math.max(totalSize.height(), absoluteLinePosition.y() + lineSize.height())
			);

			linePosition = new LineDimension(0, linePosition.depth() + lineDepth, lineDirection);
		}

		return LayoutResult.create(childLayoutResults.toArray(ChildLayoutResult[]::new), totalSize);
	}

	private static List<ChildLayoutResult> layoutFinalLine(
		LineBox line, LineDimension linePosition, FlowRootContextSwitch flowRootContextSwitch, AbsoluteSize maxSize
	) {
		LineDimension actualLinePosition = linePosition;
		if (flowRootContextSwitch != null && line.getLineDirection() == LineDirection.LTR) {
			float offsetY = flowRootContextSwitch.predictedPosition().y() + actualLinePosition.depth();
			FloatTracker floatTracker = flowRootContextSwitch.floatContext().getFloatTracker();
			float lineXOffset = floatTracker.getLeftInlineOffset(offsetY);
			actualLinePosition = new LineDimension(
				actualLinePosition.run() + lineXOffset, actualLinePosition.depth(),
				line.getLineDirection());
		}
		// TODO: Handle non-LTR line direction
		
		return line.layoutAtPos(actualLinePosition);
	}

}
