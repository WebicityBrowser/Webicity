package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.container;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicSolidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.SolidRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleBoxGenerator;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class ContainerUI implements ComponentUI {

	private final Component component;

	public ContainerUI(Component component, ComponentUI parent) {
		this.component = component;
	}
	
	@Override
	public Box[] generateBoxes(BoxContext context) {
		return SimpleBoxGenerator.generateBoxes(() -> performBoxing(context));
	}

	private Box[] performBoxing(BoxContext context) {
		Box rootBox = new BasicSolidBox(component.getStyleDirectives(), box -> createRenderer(box));
		return new Box[] { rootBox };
	}
	
	private SolidRenderer createRenderer(Box box) {
		return new ContainerRenderer(box);
	}

}
