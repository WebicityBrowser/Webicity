package com.github.webicitybrowser.threadyweb.graphical.layout.adjusted;

import java.util.ArrayList;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.base.layout.StaticTreeTracker;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.layout.adjusted.position.PositionOffsetUtil;
import com.github.webicitybrowser.threadyweb.graphical.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.value.PositionType;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public class AdjustedLayoutManager implements SolidLayoutManager {

	private final SolidLayoutManager innerLayoutManager;

	public AdjustedLayoutManager(SolidLayoutManager innerLayoutManager) {
		this.innerLayoutManager = innerLayoutManager;
	}

	@Override
	public LayoutResult render(LayoutRenderContext layoutManagerContext) {
		List<Box> children = layoutManagerContext.treeTracker().children();
		List<Box> childrenForInnerLayout = new ArrayList<>(children.size());
		List<Box> outOfFlowChildren = new ArrayList<>(0);
		getChildrenForInnerLayout(children, childrenForInnerLayout, outOfFlowChildren);

		LayoutResult innerLayoutResult = renderInnerLayout(layoutManagerContext, childrenForInnerLayout);
		ChildLayoutResult[] adjustedNormalChildLayoutResults = adjustRelativeChildren(layoutManagerContext, innerLayoutResult.childLayoutResults());

		List<ChildLayoutResult> adjustedChildLayoutResults = new ArrayList<>();
		for (ChildLayoutResult adjustedNormalChildLayoutResult : adjustedNormalChildLayoutResults) {
			adjustedChildLayoutResults.add(adjustedNormalChildLayoutResult);
		}

		adjustedChildLayoutResults.addAll(AdjustedLayoutRenderer.render(layoutManagerContext, outOfFlowChildren));

		// TODO: Make sure paint order of positioned elements is preserved

		return LayoutResult.create(adjustedChildLayoutResults.toArray(ChildLayoutResult[]::new), innerLayoutResult.fitSize());
	}

	private ChildLayoutResult[] adjustRelativeChildren(
		LayoutRenderContext layoutManagerContext, ChildLayoutResult[] originalChildLayoutResults
	) {
		ChildLayoutResult[] adjustedChildLayoutResults = new ChildLayoutResult[originalChildLayoutResults.length];
		for (int i = 0; i < originalChildLayoutResults.length; i++) {
			ChildLayoutResult originalLayoutResult = originalChildLayoutResults[i];
			DirectivePool directives = originalLayoutResult.unit().styleDirectives();
			if (PositionOffsetUtil.getPositionType(directives) == PositionType.RELATIVE) {
				SizeCalculationContext sizeCalculationContext = LayoutSizeUtils.createSizeCalculationContext(layoutManagerContext, originalLayoutResult.unit().componentUI());
				adjustedChildLayoutResults[i] = adjustRelativeChild(originalLayoutResult, sizeCalculationContext);
			} else {
				adjustedChildLayoutResults[i] = originalLayoutResult;
			}
		}

		return adjustedChildLayoutResults;
	}

	private ChildLayoutResult adjustRelativeChild(ChildLayoutResult originalLayoutResult, SizeCalculationContext sizeCalculationContext) {
		DirectivePool directives = originalLayoutResult.unit().styleDirectives();
		AbsolutePosition positionOffset = PositionOffsetUtil.getRelativePositionOffset(sizeCalculationContext, directives);
		AbsolutePosition adjustedPosition = AbsoluteDimensionsMath.sum(
			originalLayoutResult.relativeRect().position(), positionOffset, AbsolutePosition::new);
		ChildLayoutResult adjustedChildLayoutResult = new ChildLayoutResult(
			originalLayoutResult.unit(),
			new Rectangle(adjustedPosition, originalLayoutResult.relativeRect().size())
		);

		return adjustedChildLayoutResult;
	}

	private LayoutResult renderInnerLayout(LayoutRenderContext layoutManagerContext, List<Box> childrenForInnerLayout) {
		LayoutRenderContext innerLayoutManagerContext = new LayoutRenderContext(
			layoutManagerContext.globalRenderContext(),
			layoutManagerContext.localRenderContext(),
			new StaticTreeTracker(
				layoutManagerContext.treeTracker().parentBox(),
				childrenForInnerLayout)
		);

		return innerLayoutManager.render(innerLayoutManagerContext);
	}

	private void getChildrenForInnerLayout(List<Box> children, List<Box> childrenForInnerLayout, List<Box> outOfFlowChildren) {
		for (Box child : children) {
			if (isInFlow(child)) {
				childrenForInnerLayout.add(child);
			} else {
				outOfFlowChildren.add(child);
			}
		}
	}

	public boolean isInFlow(Box box) {
		return
			PositionOffsetUtil.getPositionType(box.styleDirectives()) == PositionType.STATIC ||
			PositionOffsetUtil.getPositionType(box.styleDirectives()) == PositionType.RELATIVE;
	}

}
