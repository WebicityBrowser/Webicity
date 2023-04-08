package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.stage.render;

import java.util.function.Function;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.SolidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.stage.render.unit.BlockWrappingUnit;

public class ContainerRenderer implements SolidRenderer {

	private final Box box;
	private final Box[] children;

	public ContainerRenderer(Box box, Box[] children) {
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
		return ContainerSolidRenderer.render(renderContext, precomputedInnerSize, children);
	}
}
