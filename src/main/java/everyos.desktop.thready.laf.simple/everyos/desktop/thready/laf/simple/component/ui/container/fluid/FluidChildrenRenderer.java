package everyos.desktop.thready.laf.simple.component.ui.container.fluid;

import everyos.desktop.thready.core.gui.stage.box.FluidBox;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;

public class FluidChildrenRenderer {

	public Unit render(RenderContext renderContext, AbsoluteSize precomputedInnerSize, FluidBox[] children) {
		return new HorizontalFluidChildrenRendererSession(renderContext, precomputedInnerSize, children).render();
	}

}
