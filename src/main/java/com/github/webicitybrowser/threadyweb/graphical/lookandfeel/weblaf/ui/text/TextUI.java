package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.BasicFluidBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.BoxContext;
import com.github.webicitybrowser.thready.gui.tree.core.Component;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.text.stage.render.TextComponentRenderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.util.WebBoxGenerator;
import com.github.webicitybrowser.threadyweb.tree.TextComponent;

public class TextUI implements ComponentUI {
	
	private final TextComponent component;

	public TextUI(Component component, ComponentUI parent) {
		this.component = (TextComponent) component;
	}

	@Override
	public TextComponent getComponent() {
		return this.component;
	}

	@Override
	public Box[] generateBoxes(BoxContext context) {
		return WebBoxGenerator.generateBoxes(() -> new Box[] { createBox() });
	}

	private Box createBox() {
		return new BasicFluidBox(
			component.getStyleDirectives(),
			(box, children) -> new TextComponentRenderer(component, box));
	}

}
