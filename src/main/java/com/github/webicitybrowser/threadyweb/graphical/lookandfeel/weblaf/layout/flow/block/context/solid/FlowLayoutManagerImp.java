package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.solid;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.directive.PositionDirective;
import com.github.webicitybrowser.thready.gui.graphical.directive.SizeDirective;
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
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.layout.flow.block.context.inline.FlowFluidRenderer;

public class FlowLayoutManagerImp implements SolidLayoutManager {

	@Override
	public LayoutResult render(ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		RenderCursorTracker renderCursor = new RenderCursorTracker();
		ChildLayoutResult[] childrenResults = renderChildren(box, globalRenderContext, localRenderContext, renderCursor);
		return LayoutResult.create(childrenResults, renderCursor.getCoveredSize());
	}

	private ChildLayoutResult[] renderChildren(
		ChildrenBox box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, RenderCursorTracker renderCursor
	) {
		List<Box> children = box.getChildrenTracker().getChildren();
		ChildLayoutResult[] results = new ChildLayoutResult[children.size()];
		for (int i = 0; i < children.size(); i++) {
			// TODO: Do we need to get the adjusted box tree?
			results[i] = renderChild(children.get(i), globalRenderContext, localRenderContext, renderCursor);
		}
		
		return results;
	}

	private ChildLayoutResult renderChild(
		Box childBox, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext, RenderCursorTracker renderCursor
	) {
		AbsoluteSize parentSize = localRenderContext.getPreferredSize();
		
		AbsoluteSize precomputedSize = precomputeChildSize(childBox, parentSize);
		LocalRenderContext childRenderContext = LocalRenderContext.create(precomputedSize, localRenderContext.getContextSwitches());
		
		RenderedUnit childUnit = childBox instanceof BasicAnonymousFluidBox inlineBox ?
			renderAnonBox(inlineBox, globalRenderContext, childRenderContext) :
			UIPipeline.render(childBox, globalRenderContext, childRenderContext);

		AbsoluteSize renderedSize = childUnit.preferredSize();
		AbsoluteSize finalSize = computeFinalChildSize(renderedSize, precomputedSize, parentSize);
		
		AbsolutePosition computedPosition = computeNormalChildPosition(childBox, parentSize, finalSize, renderCursor);
		AbsolutePosition finalPosition = computedPosition;
		
		Rectangle renderedRectangle = new Rectangle(finalPosition, finalSize);
		
		return new ChildLayoutResult(renderedRectangle, childUnit);
	}

	private RenderedUnit renderAnonBox(BasicAnonymousFluidBox inlineBox, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		return FlowFluidRenderer.render(inlineBox, globalRenderContext, localRenderContext);
	}

	private AbsoluteSize precomputeChildSize(Box childBox, AbsoluteSize parentSize) {
		return childBox
			.styleDirectives()
			.getDirectiveOrEmpty(SizeDirective.class)
			.map(directive -> directive.getSize())
			.map(relativeSize -> relativeSize.resolveAbsoluteSize(parentSize))
			.orElse(new AbsoluteSize(parentSize.width(), RelativeDimension.UNBOUNDED));
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
