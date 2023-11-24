package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
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
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.br.BreakBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;

/**
 * This class renders elements in the flow layout manager when they
 * form an inline context. For more info on inline contexts, see
 * the specification at https://www.w3.org/TR/CSS22/visuren.html#inline-formatting
 */
public final class FlowInlineRenderer {
	
	private FlowInlineRenderer() {}

	public static LayoutResult render(FlowRenderContext context) {
		LineDirection lineDirection = LineDirection.LTR;
		FlowInlineRendererState state = new FlowInlineRendererState(lineDirection, context);

		prepareTextRendering(state);

		for (Box childBox: context.layoutManagerContext().children()) {
			addBoxToLine(state, childBox);
		}

		LayoutManagerContext layoutManagerContext = context.layoutManagerContext();
		float lineDepth = FlowUtils.getLineHeight(layoutManagerContext, layoutManagerContext.layoutDirectives());
		return createInnerDisplayUnit(state, lineDepth);
	}

	private static void prepareTextRendering(FlowInlineRendererState state) {
		FlowRenderContext context = state.flowContext();
		LayoutManagerContext layoutManagerContext = context.layoutManagerContext();
		state.getFontStack().push(FlowUtils.computeFont(
			context.layoutManagerContext(), layoutManagerContext.layoutDirectives(), context.localRenderContext().getParentFontMetrics()));
		FlowInlineTextRenderer.preadjustTextBoxes(state, layoutManagerContext.children());
	}

	/**
	 * Take a box to add to the line, and pass it to the appropriate
	 * delegate method based on its type and properties.
	 * @param state The inline context state
	 * @param childBox The box to add to the line
	 */
	private static void addBoxToLine(FlowInlineRendererState state, Box childBox) {
		if (childBox instanceof TextBox textBox) {
			FlowInlineTextRenderer.addTextBoxToLine(state, textBox);
		} else if (childBox instanceof BreakBox) {
			FlowInlineBreakRenderer.addBreakBoxToLine(state);
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
		state.getFontStack().push(FlowUtils.computeFont(
			state.flowContext().layoutManagerContext(), childBox.styleDirectives(), parentFontMetrics));
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

	private static LayoutResult createInnerDisplayUnit(FlowInlineRendererState state, float lineDepth) {
		List<ChildLayoutResult> childLayoutResults = new ArrayList<>();
		LineDirection lineDirection = state.lineContext().lineDirection();

		LineDimension linePosition = new LineDimension(0, 0, lineDirection);
		LineDimension totalSize = new LineDimension(0, 0, lineDirection);

		for (LineBox line: state.lineContext().lines()) {
			childLayoutResults.addAll(layoutFinalLine(line, linePosition, state));

			LineDimension lineSize = LineDimensionConverter.convertToLineDimension(line.getSize(), lineDirection);
			float finalLineDepth = lineDepth != -1 ? lineDepth : lineSize.depth();
			linePosition = new LineDimension(0, linePosition.depth() + finalLineDepth, lineDirection);

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
