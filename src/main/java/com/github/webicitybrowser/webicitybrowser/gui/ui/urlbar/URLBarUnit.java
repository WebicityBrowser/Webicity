package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.dimensions.AbsolutePosition;
import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.RenderedUnit;
import com.github.webicitybrowser.webicitybrowser.gui.Styling;

public record URLBarUnit(AbsoluteSize fitSize, Font2D font, URLBarBox box) implements RenderedUnit {

	@Override
	public UIDisplay<?, ?, ?> display() {
		return box.display();
	}

	@Override
	public DirectivePool styleDirectives() {
		return box.styleDirectives();
	}

	public URLBarContext context() {
		return box.displayContext();
	}

	public Rectangle getContentRect(Rectangle documentRect) {
		float xOffset = Styling.BUTTON_WIDTH / 2;
		
		AbsolutePosition originalPosition = documentRect.position();
		AbsolutePosition adjustedPosition = new AbsolutePosition(originalPosition.x() + xOffset, originalPosition.y());
		
		AbsoluteSize originalSize = documentRect.size();
		AbsoluteSize adjustedSize = new AbsoluteSize(
			Math.max(0, originalSize.width() - xOffset * 2),
			originalSize.height());
		
		return new Rectangle(adjustedPosition, adjustedSize);
	}

}
