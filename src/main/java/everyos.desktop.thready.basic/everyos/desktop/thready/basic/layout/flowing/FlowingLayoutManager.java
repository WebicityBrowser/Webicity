package everyos.desktop.thready.basic.layout.flowing;

import everyos.desktop.thready.basic.layout.flowing.directive.PositionDirective;
import everyos.desktop.thready.basic.layout.flowing.directive.SizeDirective;
import everyos.desktop.thready.core.gui.SolidLayoutManager;
import everyos.desktop.thready.core.gui.stage.box.SolidBox;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.gui.stage.render.SolidRenderer;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsolutePosition;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.RelativeDimension;
import everyos.desktop.thready.core.positioning.imp.AbsolutePositionImp;
import everyos.desktop.thready.core.positioning.imp.AbsoluteSizeImp;
import everyos.desktop.thready.core.positioning.imp.RectangleImp;

public class FlowingLayoutManager implements SolidLayoutManager {

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize, SolidBox[] children) {
		FlowingLayoutResult[] childrenResults = new FlowingLayoutResult[children.length];
		RenderData renderData = new RenderData();
		for (int i = 0; i < children.length; i++) {
			childrenResults[i] = renderChild(renderContext, children[i], precomputedSize, renderData);
		}
		
		return new FlowingLayoutUnit(childrenResults);
	}

	private FlowingLayoutResult renderChild(
		RenderContext renderContext, SolidBox child, AbsoluteSize parentSize, RenderData renderData
	) {
		AbsoluteSize precomputedSize = precomputeChildSize(child, parentSize);
		
		SolidRenderer childRenderer = child.createRenderer();
		Unit childUnit = childRenderer.render(renderContext, precomputedSize);
		AbsoluteSize renderedSize = childUnit.getMinimumSize();
		
		AbsoluteSize finalSize = computeFinalChildSize(renderedSize, precomputedSize, parentSize);
		
		AbsolutePosition computedPosition = computeNormalChildPosition(child, parentSize, finalSize, renderData);
		AbsolutePosition finalPosition = computedPosition;
		
		Rectangle renderedRectangle = new RectangleImp(finalPosition, finalSize);
		
		return new FlowingLayoutResult(renderedRectangle, childUnit, 0);
	}

	private AbsolutePosition computeNormalChildPosition(
		SolidBox child, AbsoluteSize parentSize, AbsoluteSize finalSize, RenderData renderData
	) {
		return child
			.getDirectivePool()
			.getDirectiveOrEmpty(PositionDirective.class)
			.map(directive -> directive.getPosition())
			.map(relativePosition -> relativePosition.resolveAbsolutePosition(parentSize))
			.orElse(selectNextPosition(finalSize, renderData));
	}

	private AbsolutePosition selectNextPosition(AbsoluteSize finalSize, RenderData renderData) {
		float yPos = renderData.getNextYPos();
		renderData.increaseNextYPos(finalSize.getHeight());
		
		return new AbsolutePositionImp(0, yPos);
	}

	private AbsoluteSize precomputeChildSize(SolidBox child, AbsoluteSize parentSize) {
		return child
			.getDirectivePool()
			.getDirectiveOrEmpty(SizeDirective.class)
			.map(directive -> directive.getSize())
			.map(relativeSize -> relativeSize.resolveAbsoluteSize(parentSize))
			.orElse(new AbsoluteSizeImp(-1, -1));
	}
	
	private AbsoluteSize computeFinalChildSize(AbsoluteSize renderedSize, AbsoluteSize precomputedSize, AbsoluteSize parentSize) {
		float widthComponent = precomputedSize.getWidth() != RelativeDimension.UNBOUNDED ?
			precomputedSize.getWidth() :
			Math.max(renderedSize.getWidth(), parentSize.getWidth());
		
		float heightComponent = precomputedSize.getHeight() != RelativeDimension.UNBOUNDED ?
				precomputedSize.getHeight() :
				renderedSize.getHeight();
				
		return new AbsoluteSizeImp(widthComponent, heightComponent);
	}
	
	private static class RenderData {
		
		private float nextYPos = 0;

		public float getNextYPos() {
			return this.nextYPos;
		}
		
		public void increaseNextYPos(float amount) {
			this.nextYPos += amount;
		}
		
	}

}
