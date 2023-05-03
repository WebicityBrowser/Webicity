package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;
import com.github.webicitybrowser.webicitybrowser.component.URLBarComponent;

public class URLBarComponentRenderer implements Renderer {

	private final Box box;
	private final URLBarComponent component;

	public URLBarComponentRenderer(Box box) {
		this.box = box;
		this.component = (URLBarComponent) box.getOwningComponent();
	}

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		Font2D font = renderContext
			.getResourceLoader()
			.loadFont(SimpleDirectiveUtil.getFontSettings(box.getStyleDirectives()));
		return new URLBarUnit(box, component, font);
	}

}
