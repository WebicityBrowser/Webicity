package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.directive.WhiteSpaceCollapseDirective.WhiteSpaceCollapse;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.InnerDisplayUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.UnitExitMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.HorizontalLineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedCollapsibleTextView;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.ConsolidatedTextCollapser;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.text.TextConsolidation;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextUnit;

public final class FlowFluidRenderer {
	
	private FlowFluidRenderer() {}

	public static InnerDisplayUnit render(
		ChildrenBox box, GlobalRenderContext globalRenderContext,
		LocalRenderContext localRenderContext, UIDisplay<?, ?, InnerDisplayUnit> innerDisplay
	) {
		LineDimensionConverter lineDimensionConverter = new HorizontalLineDimensionConverter();
		FlowFluidRendererState state = new FlowFluidRendererState(
			lineDimensionConverter, globalRenderContext,
			localRenderContext, innerDisplay);

		preadjustTextBoxes(state, box);

		for (Box childBox: box.getChildrenTracker().getChildren()) {
			addBoxToLine(state, childBox);
		}

		return createInnerDisplayUnit(box, state);
	}

	private static void preadjustTextBoxes(FlowFluidRendererState state, ChildrenBox box) {
		collectPreadjustTextBoxes(state, box);
		ConsolidatedCollapsibleTextView textView = state.getTextConsolidation().getTextView();
		ConsolidatedTextCollapser.collapse(textView, WhiteSpaceCollapse.COLLAPSE);
	}

	private static void collectPreadjustTextBoxes(FlowFluidRendererState state, ChildrenBox box) {
		for (Box childBox: box.getChildrenTracker().getChildren()) {
			if (childBox instanceof TextBox textBox) {
				state.getTextConsolidation().addText(textBox, textBox.text());
			} else if (childBox instanceof ChildrenBox && !childBox.managesSelf()) {
				collectPreadjustTextBoxes(state, (ChildrenBox) childBox);
			}
		}
	}

	private static void addBoxToLine(FlowFluidRendererState state, Box childBox) {
		if (childBox instanceof TextBox textBox) {
			addTextBoxToLine(state, textBox);
		} else if (childBox.managesSelf()) {
			addSelfManagedBoxToLine(state, childBox);
		} else {
			addInlineBoxToLine(state, childBox);
		}
	}

	private static void addTextBoxToLine(FlowFluidRendererState state, TextBox textBox) {
		TextConsolidation textConsolidation = state.getTextConsolidation();
		String adjustedText = textConsolidation.readNextText(textBox);
		Font2D font = textBox.getFont(state.getGlobalRenderContext());
		
		TextSplitter splitter = new TextSplitter(adjustedText, font);
		while (!splitter.completed()) {
			boolean forceFit = state.currentLine().isEmpty();
			float parentWidth = state.getLocalRenderContext().getPreferredSize().width();
			float remainingWidth = parentWidth == RelativeDimension.UNBOUNDED ?
				parentWidth :
				parentWidth - state.currentLine().getSize().width();
			String text = splitter.getFittingText(forceFit, remainingWidth);
			if (text == null) {
				startNewLine(state);
				continue;
			}

			AbsoluteSize preferredSize = new AbsoluteSize(
				splitter.getLastTextWidth(),
				font.getMetrics().getCapHeight()
			);
			
			state.currentLine().add(new TextUnit(preferredSize, textBox, text, font));
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
			startNewLine(state);
		}
	}

	private static void startNewLine(FlowFluidRendererState state) {
		state.startNewLine();
	}

	private static InnerDisplayUnit createInnerDisplayUnit(ChildrenBox box, FlowFluidRendererState state) {
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

		return InnerDisplayUnit.create(
			box.display(),
			totalSize,
			childLayoutResults.toArray(new ChildLayoutResult[0]));
	}

}
