package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicFluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text.stage.render.TextComponentRenderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleBoxGenerator;
import com.github.webicitybrowser.thready.gui.tree.basics.TextComponent;
import com.github.webicitybrowser.thready.gui.tree.core.Component;

public class TextUI implements ComponentUI {
	
	private Component component;

	public TextUI(Component component, ComponentUI parent) {
		this.component = component;
	}

	@Override
	public Component getComponent() {
		return this.component;
	}

	@Override
	public Box[] generateBoxes(BoxContext context) {
		return SimpleBoxGenerator.generateBoxes(() -> new Box[] { createBox() });
	}

	private Box createBox() {
		return new BasicFluidBox(
			component.getStyleDirectives(),
			(box, children) -> new TextComponentRenderer((TextComponent) component, box));
	}

}
