package com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.render;

import java.util.HashMap;
import java.util.Map;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderCache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class RenderCacheImp implements RenderCache {
	
private Map<RenderEntry, RenderedUnit> cache = new HashMap<>();

	@Override
	@SuppressWarnings("unchecked")
	public <U extends Box, V extends RenderedUnit> V cachedRender(U box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		UIDisplay<?, U, V> display = (UIDisplay<?, U, V>) box.display();
		boolean hasNoContextSwitches = localRenderContext.contextSwitches().length == 0;

		// Currently, we must bypass cache if there are special contexts, as they contain extrinsic state that
		// we are not yet prepared to handle
		RenderedUnit renderedUnit = cache.get(new RenderEntry(box, localRenderContext.preferredSize()));
		if (renderedUnit != null && hasNoContextSwitches) {
			return (V) renderedUnit;
		}
	
		V result = display.renderBox(box, globalRenderContext, localRenderContext);
		if (hasNoContextSwitches) {
			cache.put(new RenderEntry(box, localRenderContext.preferredSize()), result);
			cache.put(new RenderEntry(box, result.fitSize()), result);
		}

		return result;
	}


	// TODO: Can we get away with ignoring context switches or not?
	@Override
	public <U extends Box> AbsoluteSize minContentSize(U box, GlobalRenderContext globalRenderContext, boolean isHorizontal) {
		AbsoluteSize size = new AbsoluteSize(
			isHorizontal ? 0 : RelativeDimension.UNBOUNDED,
			isHorizontal ? RelativeDimension.UNBOUNDED : 0
		);
		return cachedRender(box, globalRenderContext, new LocalRenderContext(size, new ContextSwitch[0])).fitSize();
	}

	@Override
	public <U extends Box> AbsoluteSize maxContentSize(U box, GlobalRenderContext globalRenderContext, boolean isHorizontal) {
		AbsoluteSize size = new AbsoluteSize(
			isHorizontal ? RelativeDimension.UNBOUNDED : 0,
			isHorizontal ? 0 : RelativeDimension.UNBOUNDED
		);

		return cachedRender(box, globalRenderContext, new LocalRenderContext(size, new ContextSwitch[0])).fitSize();
	}

	private record RenderEntry(Box box, AbsoluteSize size) {}

}
