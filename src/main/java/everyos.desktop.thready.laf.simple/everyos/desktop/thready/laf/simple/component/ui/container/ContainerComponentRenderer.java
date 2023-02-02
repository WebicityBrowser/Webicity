package everyos.desktop.thready.laf.simple.component.ui.container;

import java.util.function.Function;

import everyos.desktop.thready.basic.directive.LayoutManagerDirective;
import everyos.desktop.thready.basic.layout.flowing.FlowingLayoutManager;
import everyos.desktop.thready.core.gui.SolidLayoutManager;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.box.FluidBox;
import everyos.desktop.thready.core.gui.stage.box.SolidBox;
import everyos.desktop.thready.core.gui.stage.render.RenderContext;
import everyos.desktop.thready.core.gui.stage.render.SolidRenderer;
import everyos.desktop.thready.core.gui.stage.render.unit.Unit;
import everyos.desktop.thready.core.positioning.AbsoluteSize;
import everyos.desktop.thready.laf.simple.component.render.unit.BlockWrappingUnit;
import everyos.desktop.thready.laf.simple.component.ui.container.fluid.FluidChildrenRenderer;

public class ContainerComponentRenderer implements SolidRenderer {
	
	private final Box box;
	private final Box[] children;
	
	public ContainerComponentRenderer(Box box, Box[] children) {
		this.box = box;
		this.children = children;
	}

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		Function<AbsoluteSize, Unit> innerUnitGenerator =
			precomputedInnerSize -> renderChildren(renderContext, precomputedInnerSize);
		Unit outerUnit = BlockWrappingUnit.render(precomputedSize, box, innerUnitGenerator);
		
		return outerUnit;
	}
	
	private Unit renderChildren(RenderContext renderContext, AbsoluteSize precomputedInnerSize) {
		if (children.length == 0 || children[0] instanceof SolidBox) {
			return renderSolidChildren(renderContext, precomputedInnerSize, toSolidBoxArray(children));
		} else {
			return renderFluidChildren(renderContext, precomputedInnerSize, toFluidBoxArray(children));
		}
	}
	
	private Unit renderSolidChildren(RenderContext renderContext, AbsoluteSize precomputedInnerSize, SolidBox[] solidChildren) {
		SolidLayoutManager layoutManager = getLayoutManager();
		
		return layoutManager.render(renderContext, precomputedInnerSize, solidChildren);
	}

	private SolidBox[] toSolidBoxArray(Box[] array) {
		SolidBox[] children = new SolidBox[array.length];
		System.arraycopy(array, 0, children, 0, array.length);
		
		return children;
	}

	private SolidLayoutManager getLayoutManager() {
		return box
			.getDirectivePool()
			.getDirectiveOrEmpty(LayoutManagerDirective.class)
			.map(directive -> directive.getLayoutManager())
			.orElseGet(() -> new FlowingLayoutManager());
	}
	
	private Unit renderFluidChildren(RenderContext renderContext, AbsoluteSize precomputedInnerSize, FluidBox[] fluidChildren) {
		return new FluidChildrenRenderer()
				.render(renderContext, precomputedInnerSize, fluidChildren);
	}

	private FluidBox[] toFluidBoxArray(Box[] array) {
		FluidBox[] children = new FluidBox[array.length];
		System.arraycopy(array, 0, children, 0, array.length);
		
		return children;
	}

}
