package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.box.GenericBox;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.base.stage.context.GenericContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabComponent;

public record TabUnit(AbsoluteSize fitSize, GenericBox<TabComponent, GenericContext> box, Font2D font, TabButtonState buttonState) implements RenderedUnit {

	@Override
	public DirectivePool styleDirectives() {
		return box.styleDirectives();
	}

	@Override
	public UIDisplay<?, ?, ?> display() {
		return box.display();
	}

}
