package com.github.webicitybrowser.webicitybrowser.gui.ui.menu;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record MenuButtonUnit(AbsoluteSize fitSize, MenuButtonBox box, Font2D font) implements RenderedUnit {

	@Override
	public UIDisplay<?, ?, ?> display() {
		return box.display();
	}

	@Override
	public DirectivePool styleDirectives() {
		return box.styleDirectives();
	}

}
