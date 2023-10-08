package com.github.webicitybrowser.thready.gui.graphical.base.imp.stage.composite;

import java.util.List;

import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.dimensions.util.RectangleUtil;
import com.github.webicitybrowser.thready.gui.graphical.base.InvalidationLevel;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIPipeline;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeLayer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.CompositeParameters;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.GlobalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.composite.LocalCompositeContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.GlobalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.LocalPaintContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public class CompositeLayerImp implements CompositeLayer {

	private final GlobalCompositeContext context = new GlobalCompositeContextImp();

	private final Rectangle bounds;
	private final CompositeParameters parameters;
	private final List<LayerPaintEntry> paintEntries;
	private final int stackLevel;

	private InvalidationLevel invalidationLevel;

	public CompositeLayerImp(Rectangle bounds, CompositeParameters parameters, List<LayerPaintEntry> paintEntries, int stackLevel) {
		this.bounds = bounds;
		this.parameters = parameters;
		this.paintEntries = paintEntries;
		this.stackLevel = stackLevel;
		this.invalidationLevel = InvalidationLevel.PAINT;
	}

	@Override
	public InvalidationLevel getInvalidationLevel() {
		return invalidationLevel;
	}

	@Override
	public int getStackLevel() {
		return stackLevel;
	}

	@Override
	public Rectangle getBounds() {
		return bounds;
	}

	@Override
	public CompositeParameters getParameters() {
		return parameters;
	}

	@Override
	public void paint(GlobalPaintContext globalPaintContext, LocalPaintContext localPaintContext, Rectangle viewport) {
		for (LayerPaintEntry entry : paintEntries) {
			if (!entryIsInViewport(entry, viewport)) {
				continue;
			}
			RenderedUnit paintableUnit = entry.paintableUnit();
			LocalCompositeContext childCompositeContext = entry.compositeContext();
			LocalPaintContext childPaintContext = createChildLocalPaintContext(localPaintContext, childCompositeContext);
			UIPipeline.paint(paintableUnit, globalPaintContext, childPaintContext);
		}
	}

	private boolean entryIsInViewport(LayerPaintEntry entry, Rectangle viewport) {
		Rectangle entryBounds = entry.compositeContext().documentRect();
		return RectangleUtil.intersects(entryBounds, viewport);
	}

	private LocalPaintContext createChildLocalPaintContext(LocalPaintContext localPaintContext, LocalCompositeContext childCompositeContext) {
		// TODO: Inherit paint from parent canvas
		return new LocalPaintContext(localPaintContext.canvas(), childCompositeContext.documentRect());
	}

	public GlobalCompositeContext getContext() {
		return context;
	}
	
}
