package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.imp;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.SingleUnitGenerator;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.FluidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.UnitGenerator;

public class FluidRendererAdapter implements FluidRenderer {

	private final Renderer renderer;

	private FluidRendererAdapter(Renderer renderer) {
		this.renderer = renderer;
	}
	
	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		return renderer.render(renderContext, precomputedSize);
	}
	
	@Override
	public UnitGenerator renderFluid(RenderContext renderContext) {
		return new SingleUnitGenerator(renderer.render(renderContext, new AbsoluteSize(-1, -1)));
	}

	public static FluidRenderer createFor(Renderer renderer) {
		if (renderer instanceof FluidRenderer fluidRenderer) {
			return fluidRenderer;
		}
		
		return new FluidRendererAdapter(renderer);
	}

}
