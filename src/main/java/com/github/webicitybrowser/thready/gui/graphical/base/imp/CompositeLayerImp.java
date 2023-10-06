package com.github.webicitybrowser.thready.gui.graphical.base.imp;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class CompositeLayerImp implements CompositeLayer {

	private final CompositeContext context = new CompositeContextImp();

	private final Rectangle bounds;
	private final CompositeReference reference;
	private final List<LayerPaintEntry> paintEntries;

	private InvalidationLevel invalidationLevel;

	public CompositeLayerImp(Rectangle bounds, CompositeReference reference, List<LayerPaintEntry> paintEntries) {
		this.bounds = bounds;
		this.reference = reference;
		this.paintEntries = paintEntries;
		this.invalidationLevel = InvalidationLevel.PAINT;
	}

	@Override
	public InvalidationLevel getInvalidationLevel() {
		return invalidationLevel;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public CompositeReference getReference() {
		return reference;
	}

	@Override
	public void paint(GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext) {
		for (LayerPaintEntry entry : paintEntries) {
			RenderedUnit paintableUnit = entry.paintableUnit();
			LocalPaintContext childPaintContext = entry.paintContext();
			UIPipeline.paint(paintableUnit, globalPaintContext, childPaintContext);
		}
	}

	public CompositeContext getContext() {
		return context;
	}
	
}
