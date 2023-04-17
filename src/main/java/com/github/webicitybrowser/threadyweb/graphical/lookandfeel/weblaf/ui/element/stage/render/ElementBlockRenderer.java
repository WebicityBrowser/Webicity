package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.SolidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.SolidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.stage.render.unit.BlockWrappingUnit;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.fluid.ElementFluidRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.solid.ElementSolidRenderer;

public class ElementBlockRenderer implements SolidRenderer {

	private final Box box;
	private final Box[] children;

	public ElementBlockRenderer(Box box, Box[] children) {
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
		if (box instanceof SolidBox && children.length > 0 && children[0] instanceof SolidBox) {
			return ElementSolidRenderer.render(renderContext, precomputedInnerSize, children);
		} else {
			return ElementFluidRenderer.render(renderContext, precomputedInnerSize, children);
		}
	}
}
