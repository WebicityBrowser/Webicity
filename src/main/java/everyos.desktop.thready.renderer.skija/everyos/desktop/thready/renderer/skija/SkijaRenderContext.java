package everyos.desktop.thready.renderer.skija;

import everyos.desktop.thready.core.graphics.ResourceGenerator;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public class SkijaRenderContext implements RenderContext {

	private final AbsoluteSize viewportSize;
	private final ResourceGenerator resourceGenerator;

	public SkijaRenderContext(AbsoluteSize viewportSize, ResourceGenerator resourceGenerator) {
		this.viewportSize = viewportSize;
		this.resourceGenerator = resourceGenerator;
	}

	@Override
	public AbsoluteSize getViewportSize() {
		return this.viewportSize;
	}

	@Override
	public ResourceGenerator getResourceGenerator() {
		return this.resourceGenerator;
	}

}
