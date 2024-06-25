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
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutManagerContext;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.LayoutResult;
import com.github.webicitybrowser.thready.gui.graphical.layout.core.SolidLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class FlowingLayoutManager implements SolidLayoutManager {

	@Override
	public LayoutResult render(LayoutManagerContext layoutManagerContext) {
		RenderCursorTracker renderCursor = new RenderCursorTracker();
		ChildLayoutResult[] childrenResults = renderChildren(layoutManagerContext, renderCursor);
		return LayoutResult.create(childrenResults, renderCursor.getCoveredSize());
	}

	private ChildLayoutResult[] renderChildren(LayoutManagerContext layoutManagerContext, RenderCursorTracker renderCursor) {
		List<Box> children = layoutManagerContext.children();
		ChildLayoutResult[] results = new ChildLayoutResult[children.size()];
		for (int i = 0; i < children.size(); i++) {
			results[i] = renderChild(layoutManagerContext, children.get(i), renderCursor);
		}
		
		return results;
	}

	private ChildLayoutResult renderChild(LayoutManagerContext layoutManagerContext, Box childBox, RenderCursorTracker renderCursor) {
		GlobalRenderContext globalRenderContext = layoutManagerContext.globalRenderContext();
		LocalRenderContext localRenderContext = layoutManagerContext.localRenderContext();

		AbsoluteSize parentSize = localRenderContext.preferredSize();
		AbsoluteSize precomputedSize = precomputeChildSize(childBox, parentSize);
		LocalRenderContext childRenderContext = LocalRenderContext.create(precomputedSize, localRenderContext.contextSwitches());
		
		RenderedUnit childUnit = UIPipeline.render(childBox, globalRenderContext, childRenderContext);

		AbsoluteSize renderedSize = childUnit.fitSize();
		AbsoluteSize finalSize = computeFinalChildSize(renderedSize, precomputedSize, parentSize);
		
		AbsolutePosition computedPosition = computeNormalChildPosition(childBox, parentSize, finalSize, renderCursor);
		AbsolutePosition finalPosition = computedPosition;
		
		Rectangle relativeRect = new Rectangle(finalPosition, finalSize);
		
		return new ChildLayoutResult(childUnit, relativeRect);
	}

	private AbsoluteSize precomputeChildSize(Box childBox, AbsoluteSize parentSize) {
		return childBox
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
		Box child, AbsoluteSize parentSize, AbsoluteSize finalSize, RenderCursorTracker renderCursor
	) {
		return child
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
