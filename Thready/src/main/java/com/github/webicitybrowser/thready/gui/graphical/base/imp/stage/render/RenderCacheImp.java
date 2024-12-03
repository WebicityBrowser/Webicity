package com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.render;

import java.util.HashMap;
import java.util.Map;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.RelativeDimension;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.GlobalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.LocalRenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderCache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.ContextSwitch;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class RenderCacheImp implements RenderCache {

	private static final int NUM_EXPECTED_ENTRIES = 4;
	
	private Map<RenderEntry, RenderedUnit> cache = new HashMap<>(NUM_EXPECTED_ENTRIES);
	private Map<RenderEntry, RenderedUnit> swapCache = new HashMap<>(NUM_EXPECTED_ENTRIES);
	private Map<Box, RenderCache> subCaches = new HashMap<>(NUM_EXPECTED_ENTRIES);
	// TODO: What if the set of boxes changes?

	@Override
	@SuppressWarnings("unchecked")
	public <U extends Box, V extends RenderedUnit> V cachedRender(U box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext) {
		UIDisplay<?, U, V> display = (UIDisplay<?, U, V>) box.display();
		boolean hasNoContextSwitches = true;//localRenderContext.contextSwitches().length == 0;

		// Currently, we must bypass cache if there are special contexts, as they contain extrinsic state that
		// we are not yet prepared to handle
		// TODO: Abovementioned issue seems to have disappeared? Look if it is still relevant
		RenderEntry renderEntry = new RenderEntry(box, localRenderContext.preferredSize());
		RenderedUnit renderedUnit = cache.get(renderEntry);
		if (renderedUnit == null) {
			renderedUnit = swapCache.get(renderEntry);
			if (renderedUnit != null) {
				cache.put(renderEntry, renderedUnit);
			}
		}

		if (renderedUnit != null && hasNoContextSwitches) {
			return (V) renderedUnit;
		}
	
		return cacheFallbackRender(display, box, globalRenderContext, localRenderContext);
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

	@Override
	public void prepare() {
		// Remove all invalid entires
		cache.entrySet().removeIf(entry ->
			entry.getKey().box.componentUI().invalidationLevel().compareTo(InvalidationLevel.RENDER) >= 0);
		for (RenderCache subCache : subCaches.values()) {
			subCache.prepare();
		}
	}

	@Override
	public void swap() {
		Map<RenderEntry, RenderedUnit> temp = cache;
		cache = swapCache;
		swapCache = temp;
		if (swapCache.size() > cache.size() * 1.5 && swapCache.size() > 16) {
			swapCache = new HashMap<>(swapCache);
		} else {
			swapCache.clear();
		}

		for (RenderCache subCache : subCaches.values()) {
			subCache.swap();
		}
	}

	private <U extends Box, V extends RenderedUnit> V cacheFallbackRender(
		UIDisplay<?, U, V> display, U box, GlobalRenderContext globalRenderContext, LocalRenderContext localRenderContext
	) {
		RenderEntry renderEntry = new RenderEntry(box, localRenderContext.preferredSize());

		RenderCache subCache = subCaches.get(box);
		if (subCache == null) {
			subCache = new RenderCacheImp();
			subCaches.put(box, subCache);
		}
		
		V result = display.renderBox(box, new GlobalRenderContext(
			globalRenderContext.viewportSize(), globalRenderContext.resourceLoader(),
			globalRenderContext.rootFontMetrics(), subCache), localRenderContext);
		//if (hasNoContextSwitches) {
			RenderEntry fitRenderEntry = new RenderEntry(box, result.fitSize());
			cache.put(renderEntry, result);
			cache.put(fitRenderEntry, result);
			subCache.swap();

			box.componentUI().validateUpTo(InvalidationLevel.COMPOSITE);
		//}

		return result;
	}

	private record RenderEntry(Box box, AbsoluteSize size) {}

}
