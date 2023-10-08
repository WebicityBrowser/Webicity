package com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.composite;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeParameters;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class GlobalCompositeContextImp implements GlobalCompositeContext {

	private final List<CompositeLayer> layers = new ArrayList<>();
	private final List<LayerPaintEntry> paintEntries = new ArrayList<>();

	private Deque<CompositeLayerEntry> entryStack = new ArrayDeque<>();

	@Override
	public void enterChildContext(Rectangle bounds, CompositeParameters parameters) {
		finalizeCurrentEntry();
		entryStack.push(new CompositeLayerEntry(bounds, parameters));
	}

	@Override
	public void exitChildContext() {
		finalizeCurrentEntry();
		entryStack.pop();
	}

	@Override
	public void addPaintUnit(RenderedUnit paintableUnit, LocalCompositeContext localCompositeContext) {
		paintEntries.add(new LayerPaintEntry(paintableUnit, localCompositeContext));
	}

	public List<CompositeLayer> getLayers() {
		return layers;
	}

	private void finalizeCurrentEntry() {
		if (entryStack.isEmpty()) return;
		
		final CompositeLayerEntry entry = entryStack.peek();
		final CompositeLayerImp layer = new CompositeLayerImp(
			entry.bounds(),
			entry.parameters(), List.copyOf(paintEntries), entryStack.size() - 1);
		layers.add(layer);
		
		paintEntries.clear();
	}

	private record CompositeLayerEntry(Rectangle bounds, CompositeParameters parameters) {}
	
}
