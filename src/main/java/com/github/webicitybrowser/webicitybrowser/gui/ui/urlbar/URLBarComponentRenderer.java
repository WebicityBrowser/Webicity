package com.github.webicitybrowser.webicitybrowser.gui.ui.urlbar;

import com.github.webicitybrowser.thready.dimensions.AbsoluteSize;
import com.github.webicitybrowser.thready.drawing.core.text.Font2D;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.ComponentUI;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.RenderContext;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.unit.Unit;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.simplelaf.util.SimpleDirectiveUtil;
import com.github.webicitybrowser.thready.gui.graphical.viewmodel.textfield.TextFieldViewModel;

public class URLBarComponentRenderer implements Renderer {

	private final Box box;
	private final ComponentUI componentUI;
	private final TextFieldViewModel textFieldViewModel;

	public URLBarComponentRenderer(Box box, ComponentUI componentUI, TextFieldViewModel textFieldViewModel) {
		this.box = box;
		this.componentUI = componentUI;
		this.textFieldViewModel = textFieldViewModel;
	}

	@Override
	public Unit render(RenderContext renderContext, AbsoluteSize precomputedSize) {
		Font2D font = renderContext
			.getResourceLoader()
			.loadFont(SimpleDirectiveUtil.getFontSettings(box.getStyleDirectives()));
		return new URLBarUnit(box, componentUI, font, textFieldViewModel);
	}

}
