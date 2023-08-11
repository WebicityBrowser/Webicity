package com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.directive.PositionDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.SizeDirective;
import com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.imp.RenderCursorTracker;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.ChildLayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.pipeline.BoundRenderedUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.ChildrenBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;

public class FlowingLayoutManager implements SolidLayoutManager {

	@Override
	public LayoutResult render(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		RenderCursorTracker renderCursor = new RenderCursorTracker();
		ChildLayoutResult[] childrenResults = renderChildren(box, globalRenderContext, localRenderContext, renderCursor);
		return LayoutResult.create(childrenResults, renderCursor.getCoveredSize());
	}

	private ChildLayoutResult[] renderChildren(
		ChildrenBox box, GlobalRenderContext renderContext, LocalRenderContext localRenderContext, RenderCursorTracker renderCursor
	) {
		List<BoundBox<?, ?>> children = box.getChildrenTracker().getChildren();
		ChildLayoutResult[] results = new ChildLayoutResult[children.size()];
		for (int i = 0; i < children.size(); i++) {
			results[i] = renderChild(renderContext, localRenderContext, children.get(i), renderCursor);
		}
		
		return results;
	}

	private ChildLayoutResult renderChild(
		GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, BoundBox<?, ?> childBox, RenderCursorTracker renderCursor
	) {
		AbsoluteSize parentSize = localRenderContext.getPreferredSize();
		AbsoluteSize precomputedSize = precomputeChildSize(childBox, parentSize);
		LocalRenderContext childRenderContext = LocalRenderContext.create(precomputedSize, localRenderContext.getContextSwitches());
		
		BoundRenderedUnitGenerator<?> childUnitGenerator = childBox.render(globalRenderContext, childRenderContext);
		// TODO: What if it is a fluid?
		BoundRenderedUnit<?> childUnit = childUnitGenerator.getUnit(g -> g.generateNextUnit(precomputedSize, true));
		AbsoluteSize renderedSize = childUnit.getRaw().preferredSize();
		
		AbsoluteSize finalSize = computeFinalChildSize(renderedSize, precomputedSize, parentSize);
		
		AbsolutePosition computedPosition = computeNormalChildPosition(childBox, parentSize, finalSize, renderCursor);
		AbsolutePosition finalPosition = computedPosition;
		
		Rectangle relativeRect = new Rectangle(finalPosition, finalSize);
		
		return new ChildLayoutResult(relativeRect, childUnit);
	}

	private AbsoluteSize precomputeChildSize(BoundBox<?, ?> childBox, AbsoluteSize parentSize) {
		return childBox
			.getRaw()
			.styleDirectives()
			.getDirectiveOrEmpty(SizeDirective.class)
			.map(directive -> directive.getSize())
			.map(relativeSize -> relativeSize.resolveAbsoluteSize(parentSize))
			.orElse(new AbsoluteSize(RelativeDimension.UNBOUNDED, RelativeDimension.UNBOUNDED));
	}
	
	private AbsoluteSize computeFinalChildSize(AbsoluteSize renderedSize, AbsoluteSize precomputedSize, AbsoluteSize parentSize) {
		float widthComponent = precomputedSize.width() != RelativeDimension.UNBOUNDED ?
			precomputedSize.width() :
			Math.max(renderedSize.width(), parentSize.width());
		
		float heightComponent = precomputedSize.height() != RelativeDimension.UNBOUNDED ?
			precomputedSize.height() :
			renderedSize.height();
				
		return new AbsoluteSize(widthComponent, heightComponent);
	}
	
	private AbsolutePosition computeNormalChildPosition(
		BoundBox<?, ?> child, AbsoluteSize parentSize, AbsoluteSize finalSize, RenderCursorTracker renderCursor
	) {
		return child
			.getRaw()
			.styleDirectives()
			.getDirectiveOrEmpty(PositionDirective.class)
			.map(directive -> directive.getPosition())
			.map(relativePosition -> relativePosition.resolveAbsolutePosition(parentSize))
			.orElse(selectNextPosition(finalSize, renderCursor));
	}

	private AbsolutePosition selectNextPosition(AbsoluteSize finalSize, RenderCursorTracker renderCursor) {
		float yPos = renderCursor.getNextYPos();
		renderCursor.recordWidth(finalSize.width());
		renderCursor.increaseNextYPos(finalSize.height());
		
		return new AbsolutePosition(0, yPos);
	}

}
