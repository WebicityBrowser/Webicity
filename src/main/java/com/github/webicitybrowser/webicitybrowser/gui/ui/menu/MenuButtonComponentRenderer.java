package com.github.webicitybrowser.webicitybrowser.gui.ui.menu;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.drawing.core.text.FontSettings;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;

public class MenuButtonComponentRenderer implements Renderer {

	private final Box box;

	public MenuButtonComponentRenderer(Box box) {
		this.box = box;
	}

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		Font2D font = loadFont(renderContext);
		return new MenuButtonUnit(box, font);
	}
	
	private Font2D loadFont(RenderContext renderContext) {
		FontSettings fontInfo = SimpleDirectiveUtil.getFontSettings(box.getStyleDirectives());
		return renderContext
			.getResourceLoader()
			.loadFont(fontInfo);
	}

}
