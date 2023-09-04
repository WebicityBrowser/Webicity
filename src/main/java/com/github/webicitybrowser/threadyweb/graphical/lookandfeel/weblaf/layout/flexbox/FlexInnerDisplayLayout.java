package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicAnonymousFluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.StyledUnitContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.unit.StyledUnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;

public class FlexInnerDisplayLayout implements SolidLayoutManager {

	private final StyledUnitGenerator styledUnitGenerator;

	public FlexInnerDisplayLayout(StyledUnitGenerator styledUnitGenerator) {
		this.styledUnitGenerator = styledUnitGenerator;
	}

	@Override
	public LayoutResult render(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		List<Box> children = box.getChildrenTracker().getChildren();
		int numChildren = children.size();
		if (numChildren == 0) {
			return LayoutResult.create(new ChildLayoutResult[0], new AbsoluteSize(0, 0));
		}

		FlexDirection flexDirection = getFlexDirection(box);

		List<FlexItem> flexItems = createFlexItems(children);
		FlexLine flexLine = new FlexLine(flexDirection);
		for (FlexItem flexItem : flexItems) {
			flexLine.addFlexItem(flexItem);
		}

		FlexMainSizeDetermination.determineMainSizes(flexLine, globalRenderContext, localRenderContext);
		Flexer.resolveFlexibleLengths(flexLine, localRenderContext);
		FlexCrossSizeDetermination.determineCrossSizes(flexLine, globalRenderContext, localRenderContext);

		ChildLayoutResult[] childLayoutResults = createChildLayoutResults(flexLine, globalRenderContext, localRenderContext);
		FlexDimension lineDimensions = new FlexDimension(flexLine.getMainSize(), flexLine.getCrossSize(), flexDirection);

		return LayoutResult.create(childLayoutResults, lineDimensions.toAbsoluteSize());
	}

	private List<FlexItem> createFlexItems(List<Box> children) {
		List<FlexItem> flexItems = new ArrayList<>(children.size());
		addFlexItems(flexItems, children);

		return flexItems;
	}

	private void addFlexItems(List<FlexItem> flexItems, List<Box> children) {
		for (Box child : children) {
			// TODO: Support anon boxes
			if (child instanceof TextBox textBox && textBox.text().isBlank()) continue;
			if (child instanceof BasicAnonymousFluidBox anonBox) {
				addFlexItems(flexItems, anonBox.getChildrenTracker().getChildren());
				continue;
			};
			flexItems.add(new FlexItem(child));
		}
	}

	private ChildLayoutResult[] createChildLayoutResults(
		FlexLine flexLine, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext
	) {
		List<FlexItem> flexItems = flexLine.getFlexItems();
		ChildLayoutResult[] childLayoutResults = new ChildLayoutResult[flexItems.size()];
		FlexDirection flexDirection = flexLine.getFlexDirection();
		float mainPosition = 0;
		for (int i = 0; i < flexItems.size(); i++) {
			FlexItem flexItem = flexItems.get(i);
			FlexDimension childPosition = new FlexDimension(mainPosition, 0, flexDirection);
			FlexDimension childSize = new FlexDimension(flexItem.getMainSize(), flexItem.getCrossSize(), flexDirection);
			Rectangle childBounds = new Rectangle(childPosition.toAbsolutePosition(), childSize.toAbsoluteSize());
			LocalRenderContext childLocalRenderContext = FlexUtils.createChildRenderContext(flexItem, childSize.toAbsoluteSize(), localRenderContext);
			RenderedUnit renderedUnit = UIPipeline.render(flexItem.getBox(), globalRenderContext, childLocalRenderContext);
			RenderedUnit styledUnit = styledUnitGenerator.generateStyledUnit(
				new StyledUnitContext(flexItem.getBox(), renderedUnit, childSize.toAbsoluteSize(), new float[4]));
			ChildLayoutResult childLayoutResult = new ChildLayoutResult(styledUnit, childBounds);
			childLayoutResults[i] = childLayoutResult;
			mainPosition += childSize.main();
		}

		return childLayoutResults;
	}

	private FlexDirection getFlexDirection(ChildrenBox box) {
		return box
			.styleDirectives()
			.getDirectiveOrEmpty(FlexDirectionDirective.class)
			.map(FlexDirectionDirective::getFlexDirection)
			.orElse(FlexDirection.ROW);
	}
	
}
