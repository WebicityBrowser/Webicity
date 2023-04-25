package com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.imp;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.directive.PositionDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.SizeDirective;
import com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.FlowingLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;

public class FlowingLayoutManagerImp implements FlowingLayoutManager {

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize, Box[] children) {
		RenderCursorTracker renderCursor = new RenderCursorTracker();
		FlowingLayoutResult[] childrenResults = renderChildren(renderContext, precomputedSize, children, renderCursor);
		return new FlowingLayoutUnit(childrenResults, renderCursor.getCoveredSize());
	}

	private FlowingLayoutResult[] renderChildren(
		RenderContext renderContext, AbsoluteSize parentSize, Box[] children, RenderCursorTracker renderCursor
	) {
		FlowingLayoutResult[] results = new FlowingLayoutResult[children.length];
		for (int i = 0; i < children.length; i++) {
			results[i] = renderChild(renderContext, parentSize, children[i], renderCursor);
		}
		
		return results;
	}

	private FlowingLayoutResult renderChild(
		RenderContext renderContext, AbsoluteSize parentSize, Box childBox, RenderCursorTracker renderCursor
	) {
		AbsoluteSize precomputedSize = precomputeChildSize(childBox, parentSize);
		
		Renderer childRenderer = childBox.createRenderer();
		Unit childUnit = childRenderer.render(renderContext, precomputedSize);
		AbsoluteSize renderedSize = childUnit.getMinimumSize();
		
		AbsoluteSize finalSize = computeFinalChildSize(renderedSize, precomputedSize, parentSize);
		
		AbsolutePosition computedPosition = computeNormalChildPosition(childBox, parentSize, finalSize, renderCursor);
		AbsolutePosition finalPosition = computedPosition;
		
		Rectangle renderedRectangle = new Rectangle(finalPosition, finalSize);
		
		return new FlowingLayoutResult(renderedRectangle, childUnit);
	}

	private AbsoluteSize precomputeChildSize(Box childBox, AbsoluteSize parentSize) {
		return childBox
			.getStyleDirectives()
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
			.getStyleDirectives()
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
