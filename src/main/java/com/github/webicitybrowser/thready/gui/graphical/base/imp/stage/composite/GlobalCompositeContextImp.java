package com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.composite;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer.CompositeReference;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class GlobalCompositeContextImp implements GlobalCompositeContext {

	private final List<CompositeLayer> layers = new ArrayList<>();
	private final List<LayerPaintEntry> paintEntries = new ArrayList<>();

	private Deque<CompositeLayerEntry> entryStack = new ArrayDeque<>();

	@Override
	public void enterChildContext(Rectangle bounds, CompositeReference reference) {
		finalizeCurrentEntry();
		entryStack.push(new CompositeLayerEntry(bounds, reference));
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
			entry.reference(), List.copyOf(paintEntries));
		layers.add(layer);
		
		paintEntries.clear();
	}

	private record CompositeLayerEntry(Rectangle bounds, CompositeReference reference) {
	}
	
}
