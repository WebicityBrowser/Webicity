package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowConfig;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRootContextSwitch;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.marker.UnitExitMarker;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimension.LineDirection;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.FlowUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.br.BreakBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;

/**
 * This class renders elements in the flow layout manager when they
 * form an inline context. For more info on inline contexts, see
 * the specification at https://www.w3.org/TR/CSS22/visuren.html#inline-formatting
 */
public class FlowInlineLayout implements SolidLayoutManager {
	
	private final FlowConfig flowConfig;

	public FlowInlineLayout(FlowConfig flowConfig) {
		this.flowConfig = flowConfig;
	}

	public LayoutResult render(LayoutRenderContext layoutRenderContext) {
		LineDirection lineDirection = LineDirection.LTR;
		FlowRenderContext flowRenderContext = FlowUtils.createFlowRenderContext(layoutRenderContext);
		FlowInlineRenderContext state = new FlowInlineRenderContext(flowConfig, flowRenderContext, lineDirection);

		prepareTextRendering(state);

		for (Box childBox: layoutRenderContext.treeTracker().children()) {
			addBoxToLine(state, childBox);
		}

		float lineDepth = FlowUtils.getLineHeight(layoutRenderContext, layoutRenderContext.layoutDirectives());
		return createInnerDisplayUnit(state, lineDepth);
	}

	private static void prepareTextRendering(FlowInlineRenderContext state) {
		FlowRenderContext context = state.flowContext();
		LayoutRenderContext layoutManagerContext = context.layoutRenderContext();
		FlowInlineTextRenderer.preadjustTextBoxes(state, layoutManagerContext.treeTracker().children());
	}

	/**
	 * Take a box to add to the line, and pass it to the appropriate
	 * delegate method based on its type and properties.
	 * @param state The inline context state
	 * @param childBox The box to add to the line
	 */
	private static void addBoxToLine(FlowInlineRenderContext state, Box childBox) {
		if (childBox instanceof TextBox textBox) {
			FlowInlineTextRenderer.addTextBoxToLine(state, textBox);
		} else if (childBox instanceof BreakBox) {
			FlowInlineBreakRenderer.addBreakBoxToLine(state, childBox.styleDirectives());
		} else if (childBox.managesSelf()) {
			FlowInlineSelfManagedRenderer.addSelfManagedBoxToLine(state, childBox);
		} else {
			assert childBox instanceof ChildrenBox;
			addInlineBoxToLine(state, (ChildrenBox) childBox);
		}
	}

	private static void addInlineBoxToLine(FlowInlineRenderContext state, ChildrenBox childBox) {
		pushFormattingInfo(state, childBox);

		for (Box inlineChildBox: childBox.getChildrenTracker().getChildren()) {
			addBoxToLine(state, inlineChildBox);
		}
		
		popFormattingInfo(state, childBox);
	}

	private static void pushFormattingInfo(FlowInlineRenderContext state, Box childBox) {
		LineBoxContainer lineContext = state.lineContext();
		
		UnitEnterMarker unitEnterMarker = new UnitEnterMarker(true, childBox.styleDirectives());
		FlowInlineRendererUtil.startNewLineIfNotFits(state, createSizeFromUnitEnterMarker(unitEnterMarker));
		lineContext.currentLine().addMarker(unitEnterMarker);
	}

	private static void popFormattingInfo(FlowInlineRenderContext state, Box childBox) {
		LineBoxContainer lineContext = state.lineContext();

		UnitExitMarker unitExitMarker = new UnitExitMarker(childBox.styleDirectives());
		FlowInlineRendererUtil.startNewLineIfNotFits(state, createSizeFromUnitExitMarker(unitExitMarker));
		lineContext.currentLine().addMarker(unitExitMarker);
	}

	private static AbsoluteSize createSizeFromUnitEnterMarker(UnitEnterMarker marker) {
		return new AbsoluteSize(marker.leftEdgeSize(), marker.topEdgeSize());
	}

	private static AbsoluteSize createSizeFromUnitExitMarker(UnitExitMarker marker) {
		return new AbsoluteSize(marker.rightEdgeSize(), marker.bottomEdgeSize());
	}

	private static LayoutResult createInnerDisplayUnit(FlowInlineRenderContext state, float lineDepth) {
		List<ChildLayoutResult> childLayoutResults = new ArrayList<>();
		LineDirection lineDirection = state.lineContext().lineDirection();

		LineDimension linePosition = new LineDimension(0, 0, lineDirection);
		LineDimension totalSize = new LineDimension(0, 0, lineDirection);

		for (LineBox line: state.lineContext().lines()) {
			childLayoutResults.addAll(layoutFinalLine(line, linePosition, state));

			LineDimension lineSize = LineDimensionConverter.convertToLineDimension(line.getSize(), lineDirection);
			float finalLineDepth = lineDepth != RelativeDimension.UNBOUNDED ? lineDepth : lineSize.depth();
			linePosition = new LineDimension(0, linePosition.depth() + finalLineDepth, lineDirection);

			totalSize = new LineDimension(Math.max(totalSize.run(), lineSize.run()), linePosition.depth(), lineDirection);
		}

		AbsoluteSize absoluteTotalSize = LineDimensionConverter.convertToAbsoluteSize(totalSize);
		return LayoutResult.create(childLayoutResults.toArray(ChildLayoutResult[]::new), absoluteTotalSize);
	}

	private static List<ChildLayoutResult> layoutFinalLine(
		LineBox line, LineDimension linePosition, FlowInlineRenderContext state
	) {
		FlowRootContextSwitch flowRootContextSwitch = state.flowContext().flowRootContextSwitch();
		LineDimension actualLinePosition = LineOffsetCalculator.offsetLinePosition(linePosition, line, flowRootContextSwitch);
		
		return line.layoutAtPos(actualLinePosition);
	}

}
