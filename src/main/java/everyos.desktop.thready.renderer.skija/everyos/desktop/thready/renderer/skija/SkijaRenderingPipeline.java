package everyos.desktop.thready.renderer.skija;

import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.LookAndFeel;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public class SkijaRenderingPipeline {

	private InvalidationLevel invalidationLevel = InvalidationLevel.BOX;
	private boolean doSecondFramePaint = false;

	public void invalidate(InvalidationLevel invalidationLevel) {
		this.invalidationLevel = invalidationLevel;
	}

	public void tick(Component rootComponent, LookAndFeel lookAndFeel, AbsoluteSize windowSize) {
		if (doSecondFramePaint && invalidationLevel == InvalidationLevel.NONE) {
			performPaintCycle();
		}
		
		switch (invalidationLevel) {
		case BOX:
		case RENDER:
		case COMPOSITE:
		case EVENT_SETUP:
		case PAINT:
			performPaintCycle();
			invalidationLevel = InvalidationLevel.NONE;
			doSecondFramePaint = true;
		default:
		}
	}

	private void performPaintCycle() {
		// TODO Auto-generated method stub
		
	}
	
}
