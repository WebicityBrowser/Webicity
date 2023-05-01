package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.dimensions.Rectangle;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.paint.Painter;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.webicitybrowser.component.URLBarComponent;

public class URLBarUnit implements Unit {

	private final Box box;
	private final URLBarComponent component;
	private final Font2D font;

	public URLBarUnit(Box box, URLBarComponent component, Font2D font) {
		this.box = box;
		this.component = component;
		this.font = font;
	}

	@Override
	public Painter getPainter(Rectangle documentRect) {
		return new URLBarPainter(box, documentRect, component, font);
	}

	@Override
	public AbsoluteSize getMinimumSize() {
		return AbsoluteSize.ZERO_SIZE;
	}

}
