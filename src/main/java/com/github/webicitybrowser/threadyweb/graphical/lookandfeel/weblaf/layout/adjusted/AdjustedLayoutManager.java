package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.adjusted;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.AbsoluteDimensionsMath;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.adjusted.position.PositionOffsetUtil;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.util.LayoutSizeUtils;
import com.github.webicitybrowser.threadyweb.graphical.value.PositionType;
import com.github.webicitybrowser.threadyweb.graphical.value.SizeCalculation.SizeCalculationContext;

public class AdjustedLayoutManager implements SolidLayoutManager {

	private final SolidLayoutManager innerLayoutManager;

	public AdjustedLayoutManager(SolidLayoutManager innerLayoutManager) {
		this.innerLayoutManager = innerLayoutManager;
	}

	@Override
	public LayoutResult render(LayoutManagerContext layoutManagerContext) {
		Function<Boolean, SizeCalculationContext>	 sizeCalculationContextGenerator =
			isHorizontal -> LayoutSizeUtils.createSizeCalculationContext(layoutManagerContext, false);

		List<Box> children = layoutManagerContext.children();
		List<Box> childrenForInnerLayout = new ArrayList<>();
		List<Box> outOfFlowChildren = new ArrayList<>();
		getChildrenForInnerLayout(children, childrenForInnerLayout, outOfFlowChildren);

		LayoutResult innerLayoutResult = renderInnerLayout(layoutManagerContext, childrenForInnerLayout);
		ChildLayoutResult[] adjustedNormalChildLayoutResults =
			adjustRelativeChildren(innerLayoutResult.childLayoutResults(), sizeCalculationContextGenerator);

		List<ChildLayoutResult> adjustedChildLayoutResults = new ArrayList<>();
		for (ChildLayoutResult adjustedNormalChildLayoutResult : adjustedNormalChildLayoutResults) {
			adjustedChildLayoutResults.add(adjustedNormalChildLayoutResult);
		}

		adjustedChildLayoutResults.addAll(
			AdjustedLayoutRenderer.render(layoutManagerContext, outOfFlowChildren, sizeCalculationContextGenerator));

		// TODO: Make sure paint order of positioned elements is preserved

		return LayoutResult.create(adjustedChildLayoutResults.toArray(ChildLayoutResult[]::new), innerLayoutResult.fitSize());
	}

	private ChildLayoutResult[] adjustRelativeChildren(
		ChildLayoutResult[] originalChildLayoutResults, Function<Boolean, SizeCalculationContext> sizeCalculationContextGenerator
	) {
		ChildLayoutResult[] adjustedChildLayoutResults = new ChildLayoutResult[originalChildLayoutResults.length];
		for (int i = 0; i < originalChildLayoutResults.length; i++) {
			ChildLayoutResult originalLayoutResult = originalChildLayoutResults[i];
			if (PositionOffsetUtil.getPositionType(originalLayoutResult.unit().styleDirectives()) == PositionType.RELATIVE) {
				adjustedChildLayoutResults[i] = adjustRelativeChild(originalLayoutResult, sizeCalculationContextGenerator);
			} else {
				adjustedChildLayoutResults[i] = originalLayoutResult;
			}
		}

		return adjustedChildLayoutResults;
	}

	private ChildLayoutResult adjustRelativeChild(
		ChildLayoutResult originalLayoutResult, Function<Boolean, SizeCalculationContext> sizeCalculationContextGenerator
	) {
		DirectivePool directives = originalLayoutResult.unit().styleDirectives();
		AbsolutePosition positionOffset = PositionOffsetUtil.getRelativePositionOffset(sizeCalculationContextGenerator, directives);
		AbsolutePosition adjustedPosition = AbsoluteDimensionsMath.sum(
			originalLayoutResult.relativeRect().position(), positionOffset, AbsolutePosition::new);
		ChildLayoutResult adjustedChildLayoutResult = new ChildLayoutResult(
			originalLayoutResult.unit(),
			new Rectangle(adjustedPosition, originalLayoutResult.relativeRect().size())
		);

		return adjustedChildLayoutResult;
	}

	private LayoutResult renderInnerLayout(LayoutManagerContext layoutManagerContext, List<Box> childrenForInnerLayout) {
		LayoutManagerContext innerLayoutManagerContext = new LayoutManagerContext(
			layoutManagerContext.parentBox(),
			childrenForInnerLayout,
			layoutManagerContext.globalRenderContext(),
			layoutManagerContext.localRenderContext()
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
