package everyos.desktop.thready.renderer.skija;

import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.LookAndFeel;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.box.SolidBox;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.gui.stage.render.SolidRenderer;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.core.positioning.Rectangle;
import everyos.desktop.thready.core.positioning.imp.AbsolutePositionImp;
import everyos.desktop.thready.core.positioning.imp.RectangleImp;
import everyos.desktop.thready.renderer.skija.canvas.SkijaRootCanvas2D;
import everyos.desktop.thready.renderer.skija.rootui.RootUI;

public class SkijaRenderingPipeline {
	
	private final ComponentUI rootUI;
	private final LookAndFeel lookAndFeel;

	private InvalidationLevel invalidationLevel = InvalidationLevel.BOX;
	private boolean doSecondFramePaint = false;

	private SolidBox rootBox;
	private Unit rootUnit;
	
	public SkijaRenderingPipeline(Component rootComponent, LookAndFeel lookAndFeel) {
		this.rootUI = createRootUI(rootComponent, lookAndFeel);
		this.lookAndFeel = lookAndFeel;
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
			doSecondFramePaint = false;
			performPaintCycle(currentCanvas, windowSize);
		}
		
		switch (invalidationLevel) {
		case BOX:
			performBoxCycle();
		case RENDER:
			performRenderCycle(windowSize);
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
	
	private void performBoxCycle() {
		Box rootBox = rootUI.generateBoxes(() -> lookAndFeel)[0]
			.getAdjustedBoxTree()[0];
		if (!(rootBox instanceof SolidBox)) {
			throw new RuntimeException("The root component must be solid!");
		}
		this.rootBox = (SolidBox) rootBox;
	}
	
	private void performRenderCycle(AbsoluteSize windowSize) {
		RenderContext renderContext = new SkijaRenderContext(windowSize);
		SolidRenderer rootRenderer = rootBox.createRenderer();
		this.rootUnit = rootRenderer.render(renderContext, windowSize);
	}

	private void performPaintCycle(SkijaRootCanvas2D currentCanvas, AbsoluteSize windowSize) {
		currentCanvas.drawRect(0, 0, windowSize.getWidth(), windowSize.getHeight());
		
		Rectangle viewportRect = createDocumentRect(windowSize);
		rootUnit.getPainter(createDocumentRect(windowSize))
			.paint(new SkijaPaintContext(), currentCanvas, viewportRect);
		
		currentCanvas.flush();
	}

	private Rectangle createDocumentRect(AbsoluteSize windowSize) {
		return new RectangleImp(new AbsolutePositionImp(0, 0), windowSize);
	}
	
}
