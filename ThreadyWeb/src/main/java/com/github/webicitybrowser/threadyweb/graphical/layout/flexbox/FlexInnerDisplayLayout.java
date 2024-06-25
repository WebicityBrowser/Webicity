package com.github.webicitybrowser.threadyweb.graphical.layout.flexbox;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicAnonymousFluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.threadyweb.graphical.directive.layout.flexbox.FlexDirectionDirective.FlexDirection;
import com.github.webicitybrowser.threadyweb.graphical.layout.flexbox.item.FlexItem;
import com.github.webicitybrowser.threadyweb.graphical.layout.flexbox.item.FlexItemSizePreferences;
import com.github.webicitybrowser.threadyweb.graphical.layout.flow.util.BoxOffsetDimensions;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitContext;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.StyledUnitGenerator;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.TextBox;

public class FlexInnerDisplayLayout implements SolidLayoutManager {

	private final StyledUnitGenerator styledUnitGenerator;

	public FlexInnerDisplayLayout(StyledUnitGenerator styledUnitGenerator) {
		this.styledUnitGenerator = styledUnitGenerator;
	}

	@Override
	public LayoutResult render(LayoutManagerContext layoutManagerContext) {
		int numChildren = layoutManagerContext.children().size();
		if (numChildren == 0) {
			return LayoutResult.create(new ChildLayoutResult[0], AbsoluteSize.ZERO_SIZE);
		}

		DirectivePool layoutDirectives = layoutManagerContext.layoutDirectives();

		FlexDirection flexDirection = FlexUtils.getFlexDirection(layoutDirectives);
		List<FlexItem> flexItems = createFlexItems(layoutManagerContext);
		List<FlexLine> flexLines = FlexMainSizeDetermination.determineLinesWithMainSizes(layoutManagerContext, flexItems);

		for (FlexLine flexLine: flexLines) {
			Flexer.resolveFlexibleLengths(flexLine, layoutManagerContext.localRenderContext());
		}
		
		FlexCrossSizeDetermination.determineLineCrossSizes(layoutManagerContext, flexLines);
		
		FlexContentJustification.justifyContent(flexLines, FlexUtils.getFlexJustifyContent(layoutDirectives), flexDirection);

		List<ChildLayoutResult> childLayoutResults = new ArrayList<>();
		float crossPosition = 0;
		for (FlexLine flexLine: flexLines) {
			addLineChildLayoutResults(crossPosition, childLayoutResults, flexLine);
			crossPosition += flexLine.getCrossSize();
		}
		
		FlexDimension lineDimensions = determineLineDimensions(flexLines, flexDirection);

		return LayoutResult.create(
			childLayoutResults.toArray(ChildLayoutResult[]::new),
			lineDimensions.toAbsoluteSize());
	}

	private List<FlexItem> createFlexItems(LayoutManagerContext layoutManagerContext) {
		List<FlexItem> flexItems = new ArrayList<>(layoutManagerContext.children().size());
		addFlexItems(layoutManagerContext, flexItems, layoutManagerContext.children());

		return flexItems;
	}

	private void addFlexItems(LayoutManagerContext layoutManagerContext, List<FlexItem> flexItems, List<Box> children) {
		for (Box child: children) {
			if (child instanceof TextBox textBox && textBox.text().isBlank()) continue;
			if (child instanceof BasicAnonymousFluidBox anonBox) {
				addFlexItems(layoutManagerContext, flexItems, anonBox.getChildrenTracker().getChildren());
				continue;
			};

			FlexItemSizePreferences sizePreferences = new FlexItemSizePreferences(layoutManagerContext, child);
			FlexItem childFlexItem = new FlexItem(child, sizePreferences);
			flexItems.add(childFlexItem);
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
			Rectangle childBounds = computeFinalChildBounds(flexItem, crossPosition, flexDirection);
			RenderedUnit renderedUnit = flexItem.getRenderedUnit();
			FlexItemSizePreferences sizePreferences = flexItem.getSizePreferences();
			RenderedUnit styledUnit = styledUnitGenerator.generateStyledUnit(new StyledUnitContext(
				flexItem.getBox(), renderedUnit, childBounds.size(),
				sizePreferences.getBoxOffsetDimensions()));
			layoutResults.add(new ChildLayoutResult(styledUnit, childBounds));
		}
	}

	private Rectangle computeFinalChildBounds(FlexItem flexItem, float crossPosition, FlexDirection flexDirection) {
		BoxOffsetDimensions boxOffsetDimensions = flexItem.getSizePreferences().getBoxOffsetDimensions();
		float[] margins = boxOffsetDimensions.margins();
		FlexDimension marginsOffset = FlexDimension.createFrom(new AbsoluteSize(margins[0], margins[2]), flexDirection);
		FlexDimension itemOffset = flexItem.getItemOffset();
		FlexDimension childPosition = new FlexDimension(
			itemOffset.main() + marginsOffset.main(),
			crossPosition + itemOffset.cross() + marginsOffset.cross(),
			flexDirection);
		FlexDimension flexChildSize = new FlexDimension(flexItem.getMainSize(), flexItem.getCrossSize(), flexDirection);
		AbsoluteSize childSize = flexChildSize.toAbsoluteSize();
		AbsoluteSize childInnerSize = LayoutSizeUtils.subtractPadding(childSize, margins);
		return new Rectangle(childPosition.toAbsolutePosition(), childInnerSize);
	}
	
}
