package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container.stage.render.fluid;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.FluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;

public interface ContainerFluidRenderer {
	
	Unit render();

	public static Unit render(RenderContext renderContext, AbsoluteSize precomputedInnerSize, Box[] children) {
		FluidBox[] fluidChildren = toFluidBoxArray(children);
		FluidLines lines = new HorizontalFluidLines(renderContext, precomputedInnerSize);
		return render(fluidChildren, lines);
	}
	
	public static Unit render(FluidBox[] children, FluidLines lines) {
		renderChildren(children, lines);
		
		AbsoluteSize computedSize = lines.computeTotalSize();
		return new FluidChildrenUnit(computedSize, lines.getRenderResults());
	}
	
	private static void renderChildren(FluidBox[] children, FluidLines lines) {
		for (FluidBox child: children) {
			lines.addBox(child);
		}
	}
	
	private static FluidBox[] toFluidBoxArray(Box[] array) {
		FluidBox[] children = new FluidBox[array.length];
		System.arraycopy(array, 0, children, 0, array.length);
		
		return children;
	}

}
