package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flexbox;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicAnonymousFluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
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

		FlexDirection flexDirection = FlexUtils.getFlexDirection(box);
		List<FlexItem> flexItems = createFlexItems(children);
		List<FlexLine> flexLines = FlexMainSizeDetermination.determineLinesWithMainSizes(
			box, flexItems, globalRenderContext, localRenderContext);

		for (FlexLine flexLine : flexLines) {
			Flexer.resolveFlexibleLengths(flexLine, localRenderContext);
		}
		
		FlexCrossSizeDetermination.determineLineCrossSizes(box, flexLines, globalRenderContext, localRenderContext);
		
		FlexContentJustification.justifyContent(flexLines, FlexUtils.getFlexJustifyContent(box), flexDirection);

		List<ChildLayoutResult> childLayoutResults = new ArrayList<>();
		float crossPosition = 0;
		for (FlexLine flexLine : flexLines) {
			addLineChildLayoutResults(crossPosition, childLayoutResults, flexLine);
			crossPosition += flexLine.getCrossSize();
		}
		
		FlexDimension lineDimensions = determineLineDimensions(flexLines, flexDirection);

		return LayoutResult.create(
			childLayoutResults.toArray(ChildLayoutResult[]::new),
			lineDimensions.toAbsoluteSize());
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

	private FlexDimension determineLineDimensions(List<FlexLine> flexLines, FlexDirection flexDirection) {
		float mainSize = 0;
		float crossSize = 0;
		for (FlexLine flexLine : flexLines) {
			mainSize = Math.max(mainSize, flexLine.getMainSize());
			crossSize += flexLine.getCrossSize();
		}

		return new FlexDimension(mainSize, crossSize, flexDirection);
	}

	private void addLineChildLayoutResults(float crossPosition, List<ChildLayoutResult> layoutResults, FlexLine flexLine) {
		List<FlexItem> flexItems = flexLine.getFlexItems();
		FlexDirection flexDirection = flexLine.getFlexDirection();
		for (FlexItem flexItem : flexItems) {
			FlexDimension itemOffset = flexItem.getItemOffset();
			FlexDimension childPosition = new FlexDimension(itemOffset.main(), crossPosition + itemOffset.cross(), flexDirection);
			FlexDimension childSize = new FlexDimension(flexItem.getMainSize(), flexItem.getCrossSize(), flexDirection);
			Rectangle childBounds = new Rectangle(childPosition.toAbsolutePosition(), childSize.toAbsoluteSize());
			RenderedUnit renderedUnit = flexItem.getRenderedUnit();
			RenderedUnit styledUnit = styledUnitGenerator.generateStyledUnit(
				new StyledUnitContext(flexItem.getBox(), renderedUnit, childSize.toAbsoluteSize(), new float[4]));
			layoutResults.add(new ChildLayoutResult(styledUnit, childBounds));
		}
	}
	
}
