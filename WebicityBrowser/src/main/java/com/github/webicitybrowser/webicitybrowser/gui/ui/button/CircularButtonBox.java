package com.github.webicitybrowser.webicitybrowser.gui.ui.button;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.webicitybrowser.component.CircularButtonComponent;

public record CircularButtonBox(CircularButtonComponent owningComponent, DirectivePool styleDirectives, UIDisplay<?, ?, ?> display) implements Box {

}
