package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.FluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BlockWrappingUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.context.inline.ElementFluidRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow.context.solid.ElementSolidRenderer;

public class FlowBlockContextRenderer implements Renderer {

	private final Box box;
	private final Box[] children;

	public FlowBlockContextRenderer(Box box, Box[] children) {
		this.box = box;
		this.children = children;
	}

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		Function<AbsoluteSize, Unit> innerUnitGenerator =
				precomputedInnerSize -> renderChildren(renderContext, precomputedInnerSize);
		return BlockWrappingUnit.render(box, precomputedSize, innerUnitGenerator);
	}
	
	private Unit renderChildren(RenderContext renderContext, AbsoluteSize precomputedInnerSize) {
		if (box instanceof FluidBox && children.length > 0 && !(children[0] instanceof FluidBox)) {
			return ElementSolidRenderer.render(renderContext, precomputedInnerSize, children);
		} else {
			return ElementFluidRenderer.render(renderContext, precomputedInnerSize, children);
		}
	}
}
