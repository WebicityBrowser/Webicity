package com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowConfig;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.FlowRenderContext;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.context.inline.LineDimension.LineDirection;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.FlowUtils;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.br.BreakBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.directive.WebTextDirectiveUtil;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;
import com.github.webicitybrowser.threadyweb.graphical.value.WhiteSpaceCollapse;

public class FlowInlineLayout implements SolidLayoutManager {

	private final FlowConfig flowConfig;

	public FlowInlineLayout(FlowConfig flowConfig) {
		this.flowConfig = flowConfig;
	}

	@Override
	public LayoutResult render(LayoutRenderContext layoutRenderContext) {
		FlowRenderContext flowRenderContext = FlowUtils.createFlowRenderContext(layoutRenderContext);
		LineContainer lineContainer = new LineContainer(LineDirection.LTR);

		List<Box> children = layoutRenderContext.treeTracker().children();
		for (Box child : children) {
			addBoxToLineContainer(child, layoutRenderContext, lineContainer);
		}

		WhiteSpaceCollapse whiteSpaceCollapse = WebTextDirectiveUtil.getWhiteSpaceCollapse(layoutRenderContext.layoutDirectives());
		LineContainer transformedContainer = lineContainer.map(lineBox -> WhitespaceHandler.collapse(lineBox, whiteSpaceCollapse));
		transformedContainer = LinePositioner.positionLines(transformedContainer, flowRenderContext);

		return convertContainerToLayoutResult(transformedContainer, layoutRenderContext);
	}

	private void addBoxToLineContainer(Box child, LayoutRenderContext layoutRenderContext, LineContainer lineContainer) {
		if (!child.managesSelf() && child instanceof ChildrenBox childrenBox) {
			lineContainer.currentLine().addEntry(new LineEntry.UnitEnter(child.styleDirectives(), true));
			for (Box innerChild : childrenBox.getChildrenTracker().getChildren()) {
				addBoxToLineContainer(innerChild, layoutRenderContext, lineContainer);
			}
			lineContainer.currentLine().addEntry(new LineEntry.UnitExit());
		} else if (child instanceof BreakBox) {
			lineContainer.currentLine().addEntry(new LineEntry.PreserveLine());
			lineContainer.newLine();
		} else if (child instanceof TextBox textBox) {
			SizeCalculationContext sizeCalculationContext = LayoutSizeUtils
				.createSizeCalculationContext(layoutRenderContext, textBox.styleDirectives());
			float letterSpacing = WebTextDirectiveUtil.getLetterSpacing(textBox.styleDirectives(), sizeCalculationContext);
			lineContainer.currentLine().addEntry(new LineEntry.Text(
				InlineTextRenderer.renderInitialText(textBox, layoutRenderContext, letterSpacing)));
		} else {
			RenderedUnit renderedUnit = SelfManagedRenderer.renderSelfManaged(flowConfig, layoutRenderContext, child);
			lineContainer.currentLine().addEntry(new LineEntry.Unit(renderedUnit));
		}
	}

	private LayoutResult convertContainerToLayoutResult(LineContainer lineContainer, LayoutRenderContext layoutRenderContext) {
		List<ChildLayoutResult> childLayoutResults = new ArrayList<>();

		float totalHeight = 0;
		float lowestLeft = Float.MAX_VALUE;
		float highestRight = Float.MIN_VALUE;
		
		for (LineBox lineBox : lineContainer.lines()) {
			LineRenderer lineRenderer = new LineRenderer(flowConfig, layoutRenderContext, lineBox);
			LayoutResult layoutResult = lineRenderer.getLayoutResult(lineBox.positioningInfo().position());
			childLayoutResults.addAll(List.of(layoutResult.childLayoutResults()));
			totalHeight += lineBox.positioningInfo().size().height();

			for (ChildLayoutResult childLayoutResult : layoutResult.childLayoutResults()) {
				Rectangle childRect = childLayoutResult.relativeRect();
				lowestLeft = Math.min(lowestLeft, childRect.position().x());
				highestRight = Math.max(highestRight, childRect.position().x() + childRect.size().width());
			}
		}

		if (childLayoutResults.isEmpty()) {
			return LayoutResult.create(new ChildLayoutResult[0], AbsoluteSize.ZERO_SIZE);
		}

		return LayoutResult.create(
			childLayoutResults.toArray(new ChildLayoutResult[0]),
			new AbsoluteSize(highestRight - lowestLeft, totalHeight)
		);
	}
	
}
