package everyos.desktop.thready.laf.simple.component.ui.text;

import everyos.desktop.thready.basic.component.TextComponent;
import everyos.desktop.thready.basic.component.box.BasicFluidBox;
import everyos.desktop.thready.core.gui.component.Component;
import everyos.desktop.thready.core.gui.laf.component.ComponentUI;
import everyos.desktop.thready.core.gui.stage.box.Box;
import everyos.desktop.thready.core.gui.stage.box.BoxContext;
import everyos.desktop.thready.laf.simple.component.SimpleComponentUIBase;
import everyos.desktop.thready.laf.simple.util.SimpleBoxGenerator;

public class SimpleTextComponentUI extends SimpleComponentUIBase<TextComponent>{

	public SimpleTextComponentUI(Component component, ComponentUI parent) {
		super((TextComponent) component, parent);
	}

	@Override
	public Box[] generateBoxes(BoxContext context) {
		return SimpleBoxGenerator.generateBoxes(getComputedDirectives(), () -> new Box[] {
			new BasicFluidBox(getComponent(), (box, children) -> new TextComponentRenderer(box))
		});
	}

}
