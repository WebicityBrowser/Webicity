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
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.HorizontalLineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.floatbox.FloatTracker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.util.FlowUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;

public final class FlowInlineRenderer {
	
	private FlowInlineRenderer() {}

	public static LayoutResult render(FlowRenderContext context) {
		LineDimensionConverter lineDimensionConverter = new HorizontalLineDimensionConverter();
		FlowInlineRendererState state = new FlowInlineRendererState(lineDimensionConverter, context);
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

		AbsolutePosition linePosition = new AbsolutePosition(0, 0);
		AbsoluteSize totalSize = new AbsoluteSize(0, 0);
		for (LineBox line: state.lineContext().lines()) {
			
			childLayoutResults.addAll(layoutFinalLine(line, linePosition, flowRootContextSwitch));
			// TODO: Use the LineDimensionConverter so we can handle vertical lines.
			linePosition = new AbsolutePosition(0, linePosition.y() + line.getSize().height());
			totalSize = new AbsoluteSize(
				Math.max(totalSize.width(), line.getSize().width()),
				Math.max(totalSize.height(), linePosition.y())
			);
		}

		return LayoutResult.create(childLayoutResults.toArray(ChildLayoutResult[]::new), totalSize);
	}

	private static List<ChildLayoutResult> layoutFinalLine(
		LineBox line, AbsolutePosition linePosition, FlowRootContextSwitch flowRootContextSwitch
	) {
		float lineX = 0;
		if (flowRootContextSwitch != null) {
			FloatTracker floatTracker = flowRootContextSwitch.floatContext().getFloatTracker();
			lineX += floatTracker.getLeftInlineOffset(linePosition.y());
		}
		AbsolutePosition actualLinePosition = new AbsolutePosition(lineX, linePosition.y());
		
		return line.layoutAtPos(actualLinePosition);
	}

}
