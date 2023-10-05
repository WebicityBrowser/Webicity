package com.github.webicitybrowser.thready.gui.graphical.base.imp;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class CompositeLayerImp implements CompositeLayer {

	private final GlobalCompositeContext context = new GlobalCompositeContextImp();

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
			LocalCompositeContext childCompositeContext = entry.compositeContext();
			LocalPaintContext childPaintContext = createChildLocalPaintContext(localPaintContext, childCompositeContext);
			UIPipeline.paint(paintableUnit, globalPaintContext, childPaintContext);
		}
	}

	private LocalPaintContext createChildLocalPaintContext(LocalPaintContext localPaintContext, LocalCompositeContext childCompositeContext) {
		// TODO: Inherit paint from parent canvas
		return new LocalPaintContext(localPaintContext.canvas(), childCompositeContext.documentRect());
	}

	public GlobalCompositeContext getContext() {
		return context;
	}
	
}
