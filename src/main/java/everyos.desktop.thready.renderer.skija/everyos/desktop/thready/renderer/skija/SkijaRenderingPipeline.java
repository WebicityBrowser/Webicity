package everyos.desktop.thready.renderer.skija;

import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.LookAndFeel;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.renderer.skija.canvas.SkijaRootCanvas2D;
import everyos.desktop.thready.renderer.skija.rootui.RootUI;

public class SkijaRenderingPipeline {
	
	@SuppressWarnings("unused")
	private final ComponentUI rootUI;

	private InvalidationLevel invalidationLevel = InvalidationLevel.BOX;
	private boolean doSecondFramePaint = false;
	
	public SkijaRenderingPipeline(Component rootComponent, LookAndFeel lookAndFeel) {
		this.rootUI = createRootUI(rootComponent, lookAndFeel);
	}

	public void invalidatePipeline(InvalidationLevel invalidationLevel) {
		if (invalidationLevel.ordinal() > this.invalidationLevel.ordinal()) {
			this.invalidationLevel = invalidationLevel;
		}
	}

	public void tick(SkijaRootCanvas2D currentCanvas, AbsoluteSize windowSize) {
		checkInvalidationScheduler();
		checkCompositeInvalidationLevels();
		
		if (doSecondFramePaint && invalidationLevel == InvalidationLevel.NONE) {
			performPaintCycle(currentCanvas, windowSize);
		}
		
		switch (invalidationLevel) {
		case BOX:
		case RENDER:
		case COMPOSITE:
		case EVENT_SETUP:
		case PAINT:
			performPaintCycle(currentCanvas, windowSize);
			invalidationLevel = InvalidationLevel.NONE;
			doSecondFramePaint = true;
		default:
		}
	}

	private ComponentUI createRootUI(Component rootComponent, LookAndFeel lookAndFeel) {
		ComponentUI dummyUI = new RootUI() {
			@Override
			public void invalidate(InvalidationLevel level) {
				invalidatePipeline(level);
			}
		};
		
		return lookAndFeel.createUIFor(rootComponent, dummyUI);
	}
	
	private void checkInvalidationScheduler() {
		// TODO Auto-generated method stub
		
	}
	
	private void checkCompositeInvalidationLevels() {
		// TODO Auto-generated method stub
		
	}

	private void performPaintCycle(SkijaRootCanvas2D currentCanvas, AbsoluteSize windowSize) {
		currentCanvas.drawRect(0, 0, windowSize.getWidth(), windowSize.getHeight());
		currentCanvas.flush();
	}
	
}
