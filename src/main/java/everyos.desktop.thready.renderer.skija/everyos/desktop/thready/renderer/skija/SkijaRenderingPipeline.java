package everyos.desktop.thready.renderer.skija;

import everyos.desktop.thready.core.graphics.ResourceGenerator;
import everyos.desktop.thready.core.gui.InvalidationLevel;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.style.StyleGenerator;
import everyos.desktop.thready.core.gui.directive.style.StyleGeneratorRoot;
import everyos.desktop.thready.core.gui.laf.ComponentUI;
import everyos.desktop.thready.core.gui.laf.LookAndFeel;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.box.BoxContext;
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
	
	private final ResourceGenerator resourceGenerator = new SkijaResourceGenerator();
	
	private final long windowId;
	private final ComponentUI rootUI;
	private final LookAndFeel lookAndFeel;
	private final StyleGenerator styleGenerator;

	private InvalidationLevel invalidationLevel = InvalidationLevel.BOX;
	private boolean doSecondFramePaint = false;

	private SolidBox rootBox;
	private Unit rootUnit;
	
	public SkijaRenderingPipeline(long windowId, Component rootComponent, LookAndFeel lookAndFeel, StyleGeneratorRoot styleGeneratorRoot) {
		this.windowId = windowId;
		this.rootUI = createRootUI(rootComponent, lookAndFeel);
		this.lookAndFeel = lookAndFeel;
		this.styleGenerator = styleGeneratorRoot.generateChildStyleGenerator(rootUI);
		styleGeneratorRoot.onMassChange(() -> invalidatePipeline(InvalidationLevel.BOX));
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
		BoxContext boxContext = () -> lookAndFeel;
		Box[] generatedBoxes = rootUI.generateBoxes(boxContext, null, styleGenerator);
		Box rootBox = generatedBoxes[0].getAdjustedBoxTree()[0];
		if (!(rootBox instanceof SolidBox)) {
			throw new RuntimeException("The root component must be solid!");
		}
		this.rootBox = (SolidBox) rootBox;
	}
	
	private void performRenderCycle(AbsoluteSize windowSize) {
		RenderContext renderContext = new SkijaRenderContext(windowSize, resourceGenerator);
		SolidRenderer rootRenderer = rootBox.createRenderer();
		this.rootUnit = rootRenderer.render(renderContext, windowSize);
		SkijaEventListeners.setupEventListeners(windowId, rootUnit);
	}

	private void performPaintCycle(SkijaRootCanvas2D currentCanvas, AbsoluteSize windowSize) {
		currentCanvas.drawRect(0, 0, windowSize.getWidth(), windowSize.getHeight());
		
		Rectangle viewportRect = createDocumentRect(windowSize);
		rootUnit.getPainter(createDocumentRect(windowSize))
			.paint(new SkijaPaintContext(resourceGenerator), currentCanvas, viewportRect);
		
		currentCanvas.flush();
	}

	private Rectangle createDocumentRect(AbsoluteSize windowSize) {
		return new RectangleImp(new AbsolutePositionImp(0, 0), windowSize);
	}
	
}
