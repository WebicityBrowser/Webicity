package com.github.webicitybrowser.threadyweb.graphical.lookandfeel.weblaf.ui.image;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.threadyweb.tree.ImageComponent;

public record ImageBox(UIDisplay<?, ?, ?> display, ImageComponent owningComponent, DirectivePool styleDirectives) implements Box {

}
