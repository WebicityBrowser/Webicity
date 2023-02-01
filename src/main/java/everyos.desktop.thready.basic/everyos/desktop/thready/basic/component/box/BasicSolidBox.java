package everyos.desktop.thready.basic.component.box;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import everyos.desktop.thready.core.gui.directive.DirectivePool;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.box.SolidBox;
import everyos.desktop.thready.core.gui.stage.render.SolidRenderer;

public class BasicSolidBox implements SolidBox {
	
	private final List<Box> children = new ArrayList<>();
	private final BiFunction<Box, Box[], SolidRenderer> rendererGenerator;
	private final DirectivePool directivePool;
	
	public BasicSolidBox(DirectivePool directivePool, BiFunction<Box, Box[], SolidRenderer> rendererGenerator) {
		this.directivePool = directivePool;
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
	public SolidRenderer createRenderer() {
		return rendererGenerator.apply(this, children.toArray(new Box[0]));
	}

	@Override
	public DirectivePool getDirectivePool() {
		return this.directivePool;
	}

}
