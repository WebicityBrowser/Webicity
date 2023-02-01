package everyos.desktop.thready.renderer.skija;

import everyos.desktop.thready.core.graphics.ResourceGenerator;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public class SkijaRenderContext implements RenderContext {

	private final AbsoluteSize viewportSize;

	public SkijaRenderContext(AbsoluteSize viewportSize) {
		this.viewportSize = viewportSize;
	}

	@Override
	public AbsoluteSize getViewportSize() {
		return this.viewportSize;
	}

	@Override
	public ResourceGenerator getResourceGenerator() {
		// TODO Auto-generated method stub
		return null;
	}

}
