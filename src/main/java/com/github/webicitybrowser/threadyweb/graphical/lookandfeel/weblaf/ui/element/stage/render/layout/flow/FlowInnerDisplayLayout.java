package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.flow;

import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.render.Renderer;
import com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.element.stage.render.layout.InnerDisplayLayout;

public class FlowInnerDisplayLayout implements InnerDisplayLayout {

	@Override
	public Renderer createRenderer(Box box, Box[] children) {
		return new FlowBlockContextRenderer(box, children);
	}

}
