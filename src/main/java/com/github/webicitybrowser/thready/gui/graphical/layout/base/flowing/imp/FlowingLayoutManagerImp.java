package com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.imp;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.layout.base.flowing.FlowingLayoutManager;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.SolidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.SolidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;

public class FlowingLayoutManagerImp implements FlowingLayoutManager {

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedInnerSize, SolidBox[] solidChildren) {
		FlowingLayoutResult[] childrenResults = renderChildren(renderContext, precomputedInnerSize, solidChildren);
		return new FlowingLayoutUnit(childrenResults);
	}

	private FlowingLayoutResult[] renderChildren(RenderContext renderContext, AbsoluteSize precomputedInnerSize, SolidBox[] solidChildren) {
		FlowingLayoutResult[] results = new FlowingLayoutResult[solidChildren.length];
		for (int i = 0; i < solidChildren.length; i++) {
			results[i] = renderChild(renderContext, precomputedInnerSize, solidChildren[i]);
		}
		
		return results;
	}

	private FlowingLayoutResult renderChild(RenderContext renderContext, AbsoluteSize precomputedInnerSize, SolidBox solidBox) {
		SolidRenderer childRenderer = solidBox.createRenderer();
		Unit childUnit = childRenderer.render(renderContext, precomputedInnerSize);
		return new FlowingLayoutResult(childUnit);
	}

}
