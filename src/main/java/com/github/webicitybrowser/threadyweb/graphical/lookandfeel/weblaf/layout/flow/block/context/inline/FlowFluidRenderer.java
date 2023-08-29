package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontMetrics;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.FlowBlockRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.contexts.LineContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.UnitEnterMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.marker.UnitExitMarker;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.HorizontalLineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.cursor.LineDimensionConverter;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebDirectiveUtil;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebFontUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public final class FlowFluidRenderer {
	
	private FlowFluidRenderer() {}

	public static LayoutResult render(FlowBlockRenderContext context) {
		LineDimensionConverter lineDimensionConverter = new HorizontalLineDimensionConverter();
		Font2D font = WebFontUtil.getFont(
			context.box().styleDirectives(),
			context.localRenderContext(),
			context.globalRenderContext());
		FlowFluidRendererState state = new FlowFluidRendererState(lineDimensionConverter, context, font);
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
		LocalRenderContext localRenderContext = createChildLocalRenderContext(state, childBox);
		GlobalRenderContext globalRenderContext = state.getGlobalRenderContext();
		// TODO: We need to use a custom localRenderContext.
		RenderedUnit boxUnit = UIPipeline.render(childBox, globalRenderContext, localRenderContext);
		AbsoluteSize adjustedSize = adjustSize(localRenderContext.getPreferredSize(), boxUnit.fitSize());
		startNewLineIfNotFits(state, adjustedSize);
		state.lineContext().currentLine().add(boxUnit, adjustedSize);
	}

	private static void addInlineBoxToLine(FlowFluidRendererState state, Box childBox) {
		assert childBox instanceof ChildrenBox;
		LineContext lineContext = state.lineContext();
		UnitEnterMarker unitEnterMarker = new UnitEnterMarker(true, childBox.styleDirectives());
		startNewLineIfNotFits(state, createSizeFromUnitEnterMarker(unitEnterMarker));
		lineContext.currentLine().addMarker(unitEnterMarker);
		for (Box inlineChildBox: ((ChildrenBox) childBox).getChildrenTracker().getChildren()) {
			addBoxToLine(state, inlineChildBox);
		}
		UnitExitMarker unitExitMarker = new UnitExitMarker(childBox.styleDirectives());
		startNewLineIfNotFits(state, createSizeFromUnitExitMarker(unitExitMarker));
		lineContext.currentLine().addMarker(unitExitMarker);
	}

	private static AbsoluteSize createSizeFromUnitEnterMarker(UnitEnterMarker marker) {
		return new AbsoluteSize(marker.leftEdgeSize() , marker.topEdgeSize());
	}

	private static AbsoluteSize createSizeFromUnitExitMarker(UnitExitMarker marker) {
		return new AbsoluteSize(marker.rightEdgeSize() , marker.bottomEdgeSize());
	}

	private static void startNewLineIfNotFits(FlowFluidRendererState state, AbsoluteSize preferredSize) {
		LineContext lineContext = state.lineContext();
		LineBox currentLine = lineContext.currentLine();
		LocalRenderContext localRenderContext = state.getLocalRenderContext();
		if (!currentLine.canFit(preferredSize, localRenderContext.getPreferredSize())) {
			lineContext.startNewLine();
		}
	}

	private static LocalRenderContext createChildLocalRenderContext(FlowFluidRendererState state, Box childBox) {
		SizeCalculation heightSizeCalculation = WebDirectiveUtil.getHeight(childBox.styleDirectives());
		float calculatedHeight = computeSize(childBox, heightSizeCalculation, state);
		SizeCalculation widthSizeCalculation = SizeCalculation.SIZE_AUTO;//WebDirectiveUtil.getWidth(childBox.styleDirectives());
		float calculatedWidth = computeSize(childBox, widthSizeCalculation, state);
		AbsoluteSize preferredSize = new AbsoluteSize(calculatedWidth, calculatedHeight);
		return LocalRenderContext.create(preferredSize, new ContextSwitch[0]);
	}

	private static float computeSize(Box childBox, SizeCalculation sizeCalculation, FlowFluidRendererState state) {
		FontMetrics fontMetrics = state.getFont().getMetrics();
		SizeCalculationContext sizeCalculationContext = new SizeCalculationContext(
			state.getLocalRenderContext().getPreferredSize(),
			state.getGlobalRenderContext().getViewportSize(),
			fontMetrics
		);

		return sizeCalculation.calculate(sizeCalculationContext);
	}

	private static AbsoluteSize adjustSize(AbsoluteSize preferredSize, AbsoluteSize fitSize) {
		float widthComponent = preferredSize.width() == RelativeDimension.UNBOUNDED ?
			fitSize.width() :
			preferredSize.width();
		float heightComponent = preferredSize.height() == RelativeDimension.UNBOUNDED ?
			fitSize.height() :
			preferredSize.height();

		return new AbsoluteSize(widthComponent, heightComponent);
	}

	private static LayoutResult createInnerDisplayUnit(ChildrenBox box, FlowFluidRendererState state) {
		List<ChildLayoutResult> childLayoutResults = new ArrayList<>();

		AbsolutePosition linePosition = new AbsolutePosition(0, 0);
		AbsoluteSize totalSize = new AbsoluteSize(0, 0);
		for (LineBox line: state.lineContext().lines()) {
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
