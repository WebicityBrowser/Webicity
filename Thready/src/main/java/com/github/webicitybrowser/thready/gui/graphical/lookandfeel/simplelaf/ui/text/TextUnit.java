package com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.ui.text;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;

public record TextUnit(
	AbsoluteSize fitSize, TextBox box, String text, Font2D font
) implements RenderedUnit {

	public UIDisplay<?, ?, ?> display() {
		return box.display();
	}

	public DirectivePool styleDirectives() {
		return box.styleDirectives();
	}

}
