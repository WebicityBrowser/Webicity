package everyos.desktop.thready.basic.component.box;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.box.FluidBox;
import everyos.desktop.thready.core.gui.stage.render.FluidRenderer;

public class BasicFluidBox implements FluidBox {
	
	private final List<Box> children = new ArrayList<>();
	private final BiFunction<Box, Box[], FluidRenderer> rendererGenerator;
	private final Component component;
	private final DirectivePool directives;
	
	public BasicFluidBox(Component component, DirectivePool directives, BiFunction<Box, Box[], FluidRenderer> rendererGenerator) {
		this.component = component;
		this.directives = directives;
		this.rendererGenerator = rendererGenerator;
	}

	@Override
	public void addChild(Box child) {
		children.add(child);
	}

	@Override
	public Box[] getAdjustedBoxTree() {
		return new Box[] { this };
	}

	@Override
	public FluidRenderer createRenderer() {
		return rendererGenerator.apply(this, children.toArray(new Box[0]));
	}
	
	@Override
	public Component getOwningComponent() {
		return this.component;
	}

	@Override
	public DirectivePool getDirectivePool() {
		return this.directives;
	}

}
