package com.github.webicitybrowser.thready.gui.graphical.base.imp;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer.CompositeReference;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class CompositeContextImp implements CompositeContext {

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
	public void addPaintUnit(RenderedUnit paintableUnit, LocalPaintContext localPaintContext) {
		paintEntries.add(new LayerPaintEntry(paintableUnit, localPaintContext));
	}

	private void finalizeCurrentEntry() {
		final CompositeLayerEntry entry = entryStack.peek();;
		final CompositeLayerImp layer = new CompositeLayerImp(
			entry.bounds(),
			entry.reference(), List.copyOf(paintEntries));
		layers.add(layer);
		
		paintEntries.clear();
	}

	private record CompositeLayerEntry(Rectangle bounds, CompositeReference reference) {
	}
	
}
