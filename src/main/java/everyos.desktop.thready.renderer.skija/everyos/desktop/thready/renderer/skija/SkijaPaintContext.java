package everyos.desktop.thready.renderer.skija;

import everyos.desktop.thready.core.graphics.ResourceGenerator;
import everyos.desktop.thready.core.gui.laf.animation.InvalidationScheduler;
import everyos.desktop.thready.core.gui.stage.paint.PaintContext;

public class SkijaPaintContext implements PaintContext {

	private final ResourceGenerator resourceGenerator;

	public SkijaPaintContext(ResourceGenerator resourceGenerator) {
		this.resourceGenerator = resourceGenerator;
	}
	
	@Override
	public InvalidationScheduler getInvalidationScheduler() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ResourceGenerator getResourceGenerator() {
		return this.resourceGenerator;
	}

	@Override
	public int getMillisSinceLastFrame() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void stepComposite() {
		// TODO Auto-generated method stub

	}

}
