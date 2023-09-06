package com.github.webicitybrowser.thready.gui.graphical.base.imp;

import java.util.HashMap;
import java.util.Map;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderCache;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class RenderCacheImp implements RenderCache {
	
private Map<RenderEntry, RenderedUnit> cache = new HashMap<>();

	@Override
	public RenderedUnit get(Box box, AbsoluteSize size) {
		return cache.get(new RenderEntry(box, size));
	}

	@Override
	public void put(Box box, AbsoluteSize size, RenderedUnit renderedUnit) {
		cache.put(new RenderEntry(box, size), renderedUnit);
	}

	private record RenderEntry(Box box, AbsoluteSize size) {}

}
