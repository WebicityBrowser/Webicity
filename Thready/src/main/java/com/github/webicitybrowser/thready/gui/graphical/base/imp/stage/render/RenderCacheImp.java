package com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.render;

import java.util.HashMap;
import java.util.Map;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderCache;
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

	private record RenderEntry(Box box, AbsoluteSize size) {}

}
