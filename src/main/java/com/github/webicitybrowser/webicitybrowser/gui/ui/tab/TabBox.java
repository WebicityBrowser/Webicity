package com.github.webicitybrowser.webicitybrowser.gui.ui.tab;

import com.github.webicitybrowser.thready.gui.directive.core.pool.DirectivePool;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.UIDisplay;
import com.github.webicitybrowser.thready.gui.graphical.lookandfeel.core.stage.box.Box;
import com.github.webicitybrowser.webicitybrowser.gui.binding.component.tab.TabComponent;

public record TabBox(TabComponent owningComponent, DirectivePool styleDirectives, UIDisplay<?, ?, ?> display) implements Box {

}
